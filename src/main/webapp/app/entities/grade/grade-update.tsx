import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { getEntities as getSubjects } from 'app/entities/subject/subject.reducer';
import { createEntity, getEntity, updateEntity } from './grade.reducer';

export const GradeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const students = useAppSelector(state => state.student.entities);
  const subjects = useAppSelector(state => state.subject.entities);
  const gradeEntity = useAppSelector(state => state.grade.entity);
  const loading = useAppSelector(state => state.grade.loading);
  const updating = useAppSelector(state => state.grade.updating);
  const updateSuccess = useAppSelector(state => state.grade.updateSuccess);

  const handleClose = () => {
    navigate('/grade');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getStudents({}));
    dispatch(getSubjects({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.score !== undefined && typeof values.score !== 'number') {
      values.score = Number(values.score);
    }
    values.dateAwarded = convertDateTimeToServer(values.dateAwarded);

    const entity = {
      ...gradeEntity,
      ...values,
      student: students.find(it => it.id.toString() === values.student?.toString()),
      subject: subjects.find(it => it.id.toString() === values.subject?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dateAwarded: displayDefaultDateTime(),
        }
      : {
          ...gradeEntity,
          dateAwarded: convertDateTimeFromServer(gradeEntity.dateAwarded),
          student: gradeEntity?.student?.id,
          subject: gradeEntity?.subject?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testappApp.grade.home.createOrEditLabel" data-cy="GradeCreateUpdateHeading">
            <Translate contentKey="testappApp.grade.home.createOrEditLabel">Create or edit a Grade</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="grade-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('testappApp.grade.score')}
                id="grade-score"
                name="score"
                data-cy="score"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('testappApp.grade.remarks')}
                id="grade-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                label={translate('testappApp.grade.dateAwarded')}
                id="grade-dateAwarded"
                name="dateAwarded"
                data-cy="dateAwarded"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="grade-student"
                name="student"
                data-cy="student"
                label={translate('testappApp.grade.student')}
                type="select"
              >
                <option value="" key="0" />
                {students
                  ? students.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="grade-subject"
                name="subject"
                data-cy="subject"
                label={translate('testappApp.grade.subject')}
                type="select"
              >
                <option value="" key="0" />
                {subjects
                  ? subjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/grade" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default GradeUpdate;
