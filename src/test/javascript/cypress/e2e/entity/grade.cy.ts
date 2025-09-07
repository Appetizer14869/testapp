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

describe('Grade e2e test', () => {
  const gradePageUrl = '/grade';
  const gradePageUrlPattern = new RegExp('/grade(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const gradeSample = { score: 11571.87 };

  let grade;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/grades+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/grades').as('postEntityRequest');
    cy.intercept('DELETE', '/api/grades/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (grade) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/grades/${grade.id}`,
      }).then(() => {
        grade = undefined;
      });
    }
  });

  it('Grades menu should load Grades page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('grade');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Grade').should('exist');
    cy.url().should('match', gradePageUrlPattern);
  });

  describe('Grade page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(gradePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Grade page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/grade/new$'));
        cy.getEntityCreateUpdateHeading('Grade');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', gradePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/grades',
          body: gradeSample,
        }).then(({ body }) => {
          grade = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/grades+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/grades?page=0&size=20>; rel="last",<http://localhost/api/grades?page=0&size=20>; rel="first"',
              },
              body: [grade],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(gradePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Grade page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('grade');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', gradePageUrlPattern);
      });

      it('edit button click should load edit Grade page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Grade');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', gradePageUrlPattern);
      });

      it('edit button click should load edit Grade page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Grade');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', gradePageUrlPattern);
      });

      it('last delete button click should delete instance of Grade', () => {
        cy.intercept('GET', '/api/grades/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('grade').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', gradePageUrlPattern);

        grade = undefined;
      });
    });
  });

  describe('new Grade page', () => {
    beforeEach(() => {
      cy.visit(`${gradePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Grade');
    });

    it('should create an instance of Grade', () => {
      cy.get(`[data-cy="score"]`).type('26291.5');
      cy.get(`[data-cy="score"]`).should('have.value', '26291.5');

      cy.get(`[data-cy="remarks"]`).type('challenge afford till');
      cy.get(`[data-cy="remarks"]`).should('have.value', 'challenge afford till');

      cy.get(`[data-cy="dateAwarded"]`).type('2025-09-07T05:59');
      cy.get(`[data-cy="dateAwarded"]`).blur();
      cy.get(`[data-cy="dateAwarded"]`).should('have.value', '2025-09-07T05:59');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        grade = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', gradePageUrlPattern);
    });
  });
});
