import React from 'react';
import { Route } from 'react-router'; // eslint-disable-line

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Student from './student';
import Teacher from './teacher';
import Department from './department';
import Course from './course';
import ClassRoom from './class-room';
import Subject from './subject';
import Grade from './grade';
import Parent from './parent';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="student/*" element={<Student />} />
        <Route path="teacher/*" element={<Teacher />} />
        <Route path="department/*" element={<Department />} />
        <Route path="course/*" element={<Course />} />
        <Route path="class-room/*" element={<ClassRoom />} />
        <Route path="subject/*" element={<Subject />} />
        <Route path="grade/*" element={<Grade />} />
        <Route path="parent/*" element={<Parent />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
