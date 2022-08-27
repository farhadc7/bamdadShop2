import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';

import StudentService from './student/student.service';
import StudentPackageService from './student-package/student-package.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();

  @Provide('studentService') private studentService = () => new StudentService();
  @Provide('studentPackageService') private studentPackageService = () => new StudentPackageService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
