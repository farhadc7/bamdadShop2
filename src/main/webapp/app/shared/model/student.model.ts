import { IStudentPackage } from '@/shared/model/student-package.model';

import { Grade } from '@/shared/model/enumerations/grade.model';
export interface IStudent {
  id?: number;
  firstName?: string;
  lastName?: string;
  nationalCode?: string ;
  mobileNumber?: string ;
  grade?: Grade ;
  studentPackages?: IStudentPackage[] ;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public nationalCode?: string ,
    public mobileNumber?: string ,
    public grade?: Grade ,
    public studentPackages?: IStudentPackage[] | null
  ) {}
}
