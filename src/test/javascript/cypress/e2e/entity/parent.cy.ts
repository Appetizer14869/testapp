import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Parent e2e test', () => {
  const parentPageUrl = '/parent';
  const parentPageUrlPattern = new RegExp('/parent(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const parentSample = { firstName: 'Korbin', lastName: 'Hilpert' };

  let parent;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/parents+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/parents').as('postEntityRequest');
    cy.intercept('DELETE', '/api/parents/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (parent) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/parents/${parent.id}`,
      }).then(() => {
        parent = undefined;
      });
    }
  });

  it('Parents menu should load Parents page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('parent');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Parent').should('exist');
    cy.url().should('match', parentPageUrlPattern);
  });

  describe('Parent page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(parentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Parent page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/parent/new$'));
        cy.getEntityCreateUpdateHeading('Parent');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/parents',
          body: parentSample,
        }).then(({ body }) => {
          parent = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/parents+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [parent],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(parentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Parent page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('parent');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parentPageUrlPattern);
      });

      it('edit button click should load edit Parent page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Parent');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parentPageUrlPattern);
      });

      it('edit button click should load edit Parent page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Parent');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parentPageUrlPattern);
      });

      it('last delete button click should delete instance of Parent', () => {
        cy.intercept('GET', '/api/parents/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('parent').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parentPageUrlPattern);

        parent = undefined;
      });
    });
  });

  describe('new Parent page', () => {
    beforeEach(() => {
      cy.visit(`${parentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Parent');
    });

    it('should create an instance of Parent', () => {
      cy.get(`[data-cy="firstName"]`).type('Anita');
      cy.get(`[data-cy="firstName"]`).should('have.value', 'Anita');

      cy.get(`[data-cy="lastName"]`).type('Senger');
      cy.get(`[data-cy="lastName"]`).should('have.value', 'Senger');

      cy.get(`[data-cy="email"]`).type('Marietta52@gmail.com');
      cy.get(`[data-cy="email"]`).should('have.value', 'Marietta52@gmail.com');

      cy.get(`[data-cy="phoneNumber"]`).type('plait');
      cy.get(`[data-cy="phoneNumber"]`).should('have.value', 'plait');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        parent = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', parentPageUrlPattern);
    });
  });
});
