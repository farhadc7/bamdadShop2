import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import StudentService from '@/entities/student/student.service';
import { IStudent } from '@/shared/model/student.model';

import { IStudentPackage, StudentPackage } from '@/shared/model/student-package.model';
import StudentPackageService from './student-package.service';
import { Grade } from '@/shared/model/enumerations/grade.model';

const validations: any = {
  studentPackage: {
    packageName: {},
    grade: {},
  },
};

@Component({
  validations,
})
export default class StudentPackageUpdate extends Vue {
  @Inject('studentPackageService') private studentPackageService: () => StudentPackageService;
  @Inject('alertService') private alertService: () => AlertService;

  public studentPackage: IStudentPackage = new StudentPackage();

  @Inject('studentService') private studentService: () => StudentService;

  public students: IStudent[] = [];
  public gradeValues: string[] = Object.keys(Grade);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.studentPackageId) {
        vm.retrieveStudentPackage(to.params.studentPackageId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.studentPackage.id) {
      this.studentPackageService()
        .update(this.studentPackage)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('bamdadShop2App.studentPackage.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.studentPackageService()
        .create(this.studentPackage)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('bamdadShop2App.studentPackage.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveStudentPackage(studentPackageId): void {
    this.studentPackageService()
      .find(studentPackageId)
      .then(res => {
        this.studentPackage = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.studentService()
      .retrieve()
      .then(res => {
        this.students = res.data;
      });
  }
}
