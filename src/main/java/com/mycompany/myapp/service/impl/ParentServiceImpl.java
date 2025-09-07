package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Parent;
import com.mycompany.myapp.repository.ParentRepository;
import com.mycompany.myapp.service.ParentService;
import com.mycompany.myapp.service.dto.ParentDTO;
import com.mycompany.myapp.service.mapper.ParentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Parent}.
 */
@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private static final Logger LOG = LoggerFactory.getLogger(ParentServiceImpl.class);

    private final ParentRepository parentRepository;

    private final ParentMapper parentMapper;

    public ParentServiceImpl(ParentRepository parentRepository, ParentMapper parentMapper) {
        this.parentRepository = parentRepository;
        this.parentMapper = parentMapper;
    }

    @Override
    public ParentDTO save(ParentDTO parentDTO) {
        LOG.debug("Request to save Parent : {}", parentDTO);
        Parent parent = parentMapper.toEntity(parentDTO);
        parent = parentRepository.save(parent);
        return parentMapper.toDto(parent);
    }

    @Override
    public ParentDTO update(ParentDTO parentDTO) {
        LOG.debug("Request to update Parent : {}", parentDTO);
        Parent parent = parentMapper.toEntity(parentDTO);
        parent = parentRepository.save(parent);
        return parentMapper.toDto(parent);
    }

    @Override
    public Optional<ParentDTO> partialUpdate(ParentDTO parentDTO) {
        LOG.debug("Request to partially update Parent : {}", parentDTO);

        return parentRepository
            .findById(parentDTO.getId())
            .map(existingParent -> {
                parentMapper.partialUpdate(existingParent, parentDTO);

                return existingParent;
            })
            .map(parentRepository::save)
            .map(parentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentDTO> findAll() {
        LOG.debug("Request to get all Parents");
        return parentRepository.findAll().stream().map(parentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParentDTO> findOne(Long id) {
        LOG.debug("Request to get Parent : {}", id);
        return parentRepository.findById(id).map(parentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Parent : {}", id);
        parentRepository.deleteById(id);
    }
}
