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

describe('ClassRoom e2e test', () => {
  const classRoomPageUrl = '/class-room';
  const classRoomPageUrlPattern = new RegExp('/class-room(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const classRoomSample = { roomNumber: 'mid dusk' };

  let classRoom;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/class-rooms+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/class-rooms').as('postEntityRequest');
    cy.intercept('DELETE', '/api/class-rooms/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (classRoom) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/class-rooms/${classRoom.id}`,
      }).then(() => {
        classRoom = undefined;
      });
    }
  });

  it('ClassRooms menu should load ClassRooms page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('class-room');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ClassRoom').should('exist');
    cy.url().should('match', classRoomPageUrlPattern);
  });

  describe('ClassRoom page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(classRoomPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ClassRoom page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/class-room/new$'));
        cy.getEntityCreateUpdateHeading('ClassRoom');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', classRoomPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/class-rooms',
          body: classRoomSample,
        }).then(({ body }) => {
          classRoom = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/class-rooms+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [classRoom],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(classRoomPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ClassRoom page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('classRoom');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', classRoomPageUrlPattern);
      });

      it('edit button click should load edit ClassRoom page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ClassRoom');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', classRoomPageUrlPattern);
      });

      it('edit button click should load edit ClassRoom page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ClassRoom');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', classRoomPageUrlPattern);
      });

      it('last delete button click should delete instance of ClassRoom', () => {
        cy.intercept('GET', '/api/class-rooms/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('classRoom').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', classRoomPageUrlPattern);

        classRoom = undefined;
      });
    });
  });

  describe('new ClassRoom page', () => {
    beforeEach(() => {
      cy.visit(`${classRoomPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ClassRoom');
    });

    it('should create an instance of ClassRoom', () => {
      cy.get(`[data-cy="roomNumber"]`).type('than goose');
      cy.get(`[data-cy="roomNumber"]`).should('have.value', 'than goose');

      cy.get(`[data-cy="capacity"]`).type('6221');
      cy.get(`[data-cy="capacity"]`).should('have.value', '6221');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        classRoom = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', classRoomPageUrlPattern);
    });
  });
});
