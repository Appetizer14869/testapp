import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './class-room.reducer';

export const ClassRoomDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const classRoomEntity = useAppSelector(state => state.classRoom.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="classRoomDetailsHeading">
          <Translate contentKey="testappApp.classRoom.detail.title">ClassRoom</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{classRoomEntity.id}</dd>
          <dt>
            <span id="roomNumber">
              <Translate contentKey="testappApp.classRoom.roomNumber">Room Number</Translate>
            </span>
          </dt>
          <dd>{classRoomEntity.roomNumber}</dd>
          <dt>
            <span id="capacity">
              <Translate contentKey="testappApp.classRoom.capacity">Capacity</Translate>
            </span>
          </dt>
          <dd>{classRoomEntity.capacity}</dd>
        </dl>
        <Button tag={Link} to="/class-room" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/class-room/${classRoomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClassRoomDetail;
