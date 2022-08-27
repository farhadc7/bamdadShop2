import { IStudent } from '@/shared/model/student.model';

import { Grade } from '@/shared/model/enumerations/grade.model';
export interface IStudentPackage {
  id?: number;
  packageName?: string | null;
  grade?: Grade | null;
  students?: IStudent[] | null;
}

export class StudentPackage implements IStudentPackage {
  constructor(public id?: number, public packageName?: string | null, public grade?: Grade | null, public students?: IStudent[] | null) {}
}
