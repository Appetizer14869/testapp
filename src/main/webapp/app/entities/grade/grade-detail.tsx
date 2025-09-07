import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './grade.reducer';

export const GradeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gradeEntity = useAppSelector(state => state.grade.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gradeDetailsHeading">
          <Translate contentKey="testappApp.grade.detail.title">Grade</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{gradeEntity.id}</dd>
          <dt>
            <span id="score">
              <Translate contentKey="testappApp.grade.score">Score</Translate>
            </span>
          </dt>
          <dd>{gradeEntity.score}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="testappApp.grade.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{gradeEntity.remarks}</dd>
          <dt>
            <span id="dateAwarded">
              <Translate contentKey="testappApp.grade.dateAwarded">Date Awarded</Translate>
            </span>
          </dt>
          <dd>{gradeEntity.dateAwarded ? <TextFormat value={gradeEntity.dateAwarded} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="testappApp.grade.student">Student</Translate>
          </dt>
          <dd>{gradeEntity.student ? gradeEntity.student.id : ''}</dd>
          <dt>
            <Translate contentKey="testappApp.grade.subject">Subject</Translate>
          </dt>
          <dd>{gradeEntity.subject ? gradeEntity.subject.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/grade" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grade/${gradeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GradeDetail;
