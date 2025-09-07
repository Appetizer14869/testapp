export interface IClassRoom {
  id?: number;
  roomNumber?: string;
  capacity?: number | null;
}

export const defaultValue: Readonly<IClassRoom> = {};
