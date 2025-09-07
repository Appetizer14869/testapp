export interface IParent {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string | null;
  phoneNumber?: string | null;
}

export const defaultValue: Readonly<IParent> = {};
