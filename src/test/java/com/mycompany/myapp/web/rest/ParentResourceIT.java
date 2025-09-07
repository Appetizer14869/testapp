package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ParentAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Parent;
import com.mycompany.myapp.repository.ParentRepository;
import com.mycompany.myapp.service.dto.ParentDTO;
import com.mycompany.myapp.service.mapper.ParentMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParentResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ParentMapper parentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParentMockMvc;

    private Parent parent;

    private Parent insertedParent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parent createEntity() {
        return new Parent()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parent createUpdatedEntity() {
        return new Parent()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);
    }

    @BeforeEach
    void initTest() {
        parent = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedParent != null) {
            parentRepository.delete(insertedParent);
            insertedParent = null;
        }
    }

    @Test
    @Transactional
    void createParent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);
        var returnedParentDTO = om.readValue(
            restParentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ParentDTO.class
        );

        // Validate the Parent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedParent = parentMapper.toEntity(returnedParentDTO);
        assertParentUpdatableFieldsEquals(returnedParent, getPersistedParent(returnedParent));

        insertedParent = returnedParent;
    }

    @Test
    @Transactional
    void createParentWithExistingId() throws Exception {
        // Create the Parent with an existing ID
        parent.setId(1L);
        ParentDTO parentDTO = parentMapper.toDto(parent);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        parent.setFirstName(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.toDto(parent);

        restParentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        parent.setLastName(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.toDto(parent);

        restParentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParents() throws Exception {
        // Initialize the database
        insertedParent = parentRepository.saveAndFlush(parent);

        // Get all the parentList
        restParentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parent.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    @Transactional
    void getParent() throws Exception {
        // Initialize the database
        insertedParent = parentRepository.saveAndFlush(parent);

        // Get the parent
        restParentMockMvc
            .perform(get(ENTITY_API_URL_ID, parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parent.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingParent() throws Exception {
        // Get the parent
        restParentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParent() throws Exception {
        // Initialize the database
        insertedParent = parentRepository.saveAndFlush(parent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parent
        Parent updatedParent = parentRepository.findById(parent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedParent are not directly saved in db
        em.detach(updatedParent);
        updatedParent.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);
        ParentDTO parentDTO = parentMapper.toDto(updatedParent);

        restParentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parentDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Parent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParentToMatchAllProperties(updatedParent);
    }

    @Test
    @Transactional
    void putNonExistingParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parent.setId(longCount.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parentDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parent.setId(longCount.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parent.setId(longCount.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParentWithPatch() throws Exception {
        // Initialize the database
        insertedParent = parentRepository.saveAndFlush(parent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parent using partial update
        Parent partialUpdatedParent = new Parent();
        partialUpdatedParent.setId(parent.getId());

        partialUpdatedParent.firstName(UPDATED_FIRST_NAME).email(UPDATED_EMAIL);

        restParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParent))
            )
            .andExpect(status().isOk());

        // Validate the Parent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParentUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedParent, parent), getPersistedParent(parent));
    }

    @Test
    @Transactional
    void fullUpdateParentWithPatch() throws Exception {
        // Initialize the database
        insertedParent = parentRepository.saveAndFlush(parent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parent using partial update
        Parent partialUpdatedParent = new Parent();
        partialUpdatedParent.setId(parent.getId());

        partialUpdatedParent
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParent))
            )
            .andExpect(status().isOk());

        // Validate the Parent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParentUpdatableFieldsEquals(partialUpdatedParent, getPersistedParent(partialUpdatedParent));
    }

    @Test
    @Transactional
    void patchNonExistingParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parent.setId(longCount.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parent.setId(longCount.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parent.setId(longCount.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(parentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParent() throws Exception {
        // Initialize the database
        insertedParent = parentRepository.saveAndFlush(parent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parent
        restParentMockMvc
            .perform(delete(ENTITY_API_URL_ID, parent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parentRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Parent getPersistedParent(Parent parent) {
        return parentRepository.findById(parent.getId()).orElseThrow();
    }

    protected void assertPersistedParentToMatchAllProperties(Parent expectedParent) {
        assertParentAllPropertiesEquals(expectedParent, getPersistedParent(expectedParent));
    }

    protected void assertPersistedParentToMatchUpdatableProperties(Parent expectedParent) {
        assertParentAllUpdatablePropertiesEquals(expectedParent, getPersistedParent(expectedParent));
    }
}
