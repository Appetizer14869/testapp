import dayjs from 'dayjs';
import { IDepartment } from 'app/shared/model/department.model';

export interface ITeacher {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string | null;
  hireDate?: dayjs.Dayjs | null;
  salary?: number | null;
  department?: IDepartment | null;
}

export const defaultValue: Readonly<ITeacher> = {};
