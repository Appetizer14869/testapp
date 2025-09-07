import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './teacher.reducer';

export const TeacherDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const teacherEntity = useAppSelector(state => state.teacher.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="teacherDetailsHeading">
          <Translate contentKey="testappApp.teacher.detail.title">Teacher</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="testappApp.teacher.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="testappApp.teacher.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="testappApp.teacher.email">Email</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="testappApp.teacher.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.phoneNumber}</dd>
          <dt>
            <span id="hireDate">
              <Translate contentKey="testappApp.teacher.hireDate">Hire Date</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.hireDate ? <TextFormat value={teacherEntity.hireDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="salary">
              <Translate contentKey="testappApp.teacher.salary">Salary</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.salary}</dd>
          <dt>
            <Translate contentKey="testappApp.teacher.department">Department</Translate>
          </dt>
          <dd>{teacherEntity.department ? teacherEntity.department.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/teacher" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/teacher/${teacherEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TeacherDetail;
