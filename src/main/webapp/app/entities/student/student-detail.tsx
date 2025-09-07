import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './student.reducer';

export const StudentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const studentEntity = useAppSelector(state => state.student.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studentDetailsHeading">
          <Translate contentKey="testappApp.student.detail.title">Student</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{studentEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="testappApp.student.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{studentEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="testappApp.student.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{studentEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="testappApp.student.email">Email</Translate>
            </span>
          </dt>
          <dd>{studentEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="testappApp.student.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{studentEntity.phoneNumber}</dd>
          <dt>
            <span id="enrollmentDate">
              <Translate contentKey="testappApp.student.enrollmentDate">Enrollment Date</Translate>
            </span>
          </dt>
          <dd>
            {studentEntity.enrollmentDate ? <TextFormat value={studentEntity.enrollmentDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="testappApp.student.classroom">Classroom</Translate>
          </dt>
          <dd>{studentEntity.classroom ? studentEntity.classroom.id : ''}</dd>
          <dt>
            <Translate contentKey="testappApp.student.course">Course</Translate>
          </dt>
          <dd>
            {studentEntity.courses
              ? studentEntity.courses.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {studentEntity.courses && i === studentEntity.courses.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="testappApp.student.parent">Parent</Translate>
          </dt>
          <dd>{studentEntity.parent ? studentEntity.parent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/student" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/student/${studentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudentDetail;
