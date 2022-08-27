import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Student = () => import('@/entities/student/student.vue');
// prettier-ignore
const StudentUpdate = () => import('@/entities/student/student-update.vue');
// prettier-ignore
const StudentDetails = () => import('@/entities/student/student-details.vue');
// prettier-ignore
const StudentPackage = () => import('@/entities/student-package/student-package.vue');
// prettier-ignore
const StudentPackageUpdate = () => import('@/entities/student-package/student-package-update.vue');
// prettier-ignore
const StudentPackageDetails = () => import('@/entities/student-package/student-package-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'student',
      name: 'Student',
      component: Student,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/new',
      name: 'StudentCreate',
      component: StudentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/:studentId/edit',
      name: 'StudentEdit',
      component: StudentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/:studentId/view',
      name: 'StudentView',
      component: StudentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student-package',
      name: 'StudentPackage',
      component: StudentPackage,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student-package/new',
      name: 'StudentPackageCreate',
      component: StudentPackageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student-package/:studentPackageId/edit',
      name: 'StudentPackageEdit',
      component: StudentPackageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student-package/:studentPackageId/view',
      name: 'StudentPackageView',
      component: StudentPackageDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
