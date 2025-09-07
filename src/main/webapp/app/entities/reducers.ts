import student from 'app/entities/student/student.reducer';
import teacher from 'app/entities/teacher/teacher.reducer';
import department from 'app/entities/department/department.reducer';
import course from 'app/entities/course/course.reducer';
import classRoom from 'app/entities/class-room/class-room.reducer';
import subject from 'app/entities/subject/subject.reducer';
import grade from 'app/entities/grade/grade.reducer';
import parent from 'app/entities/parent/parent.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  student,
  teacher,
  department,
  course,
  classRoom,
  subject,
  grade,
  parent,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
