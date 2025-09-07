import dayjs from 'dayjs';
import { IClassRoom } from 'app/shared/model/class-room.model';
import { ICourse } from 'app/shared/model/course.model';
import { IParent } from 'app/shared/model/parent.model';

export interface IStudent {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string | null;
  enrollmentDate?: dayjs.Dayjs | null;
  classroom?: IClassRoom | null;
  courses?: ICourse[] | null;
  parent?: IParent | null;
}

export const defaultValue: Readonly<IStudent> = {};
