import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStudentPackage } from '@/shared/model/student-package.model';
import StudentPackageService from './student-package.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class StudentPackageDetails extends Vue {
  @Inject('studentPackageService') private studentPackageService: () => StudentPackageService;
  @Inject('alertService') private alertService: () => AlertService;

  public studentPackage: IStudentPackage = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.studentPackageId) {
        vm.retrieveStudentPackage(to.params.studentPackageId);
      }
    });
  }

  public retrieveStudentPackage(studentPackageId) {
    this.studentPackageService()
      .find(studentPackageId)
      .then(res => {
        this.studentPackage = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
