import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './parent.reducer';

export const ParentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const parentEntity = useAppSelector(state => state.parent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="parentDetailsHeading">
          <Translate contentKey="testappApp.parent.detail.title">Parent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{parentEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="testappApp.parent.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{parentEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="testappApp.parent.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{parentEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="testappApp.parent.email">Email</Translate>
            </span>
          </dt>
          <dd>{parentEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="testappApp.parent.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{parentEntity.phoneNumber}</dd>
        </dl>
        <Button tag={Link} to="/parent" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parent/${parentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParentDetail;
