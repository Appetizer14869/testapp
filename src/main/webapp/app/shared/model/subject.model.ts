import { ITeacher } from 'app/shared/model/teacher.model';
import { ICourse } from 'app/shared/model/course.model';

export interface ISubject {
  id?: number;
  subjectName?: string;
  description?: string | null;
  teacher?: ITeacher | null;
  courses?: ICourse[] | null;
}

export const defaultValue: Readonly<ISubject> = {};
