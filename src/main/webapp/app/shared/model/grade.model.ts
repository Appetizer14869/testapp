import dayjs from 'dayjs';
import { IStudent } from 'app/shared/model/student.model';
import { ISubject } from 'app/shared/model/subject.model';

export interface IGrade {
  id?: number;
  score?: number;
  remarks?: string | null;
  dateAwarded?: dayjs.Dayjs | null;
  student?: IStudent | null;
  subject?: ISubject | null;
}

export const defaultValue: Readonly<IGrade> = {};
