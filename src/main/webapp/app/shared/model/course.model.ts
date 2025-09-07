import { ISubject } from 'app/shared/model/subject.model';
import { IStudent } from 'app/shared/model/student.model';

export interface ICourse {
  id?: number;
  title?: string;
  description?: string | null;
  credits?: number;
  subjects?: ISubject[] | null;
  students?: IStudent[] | null;
}

export const defaultValue: Readonly<ICourse> = {};
