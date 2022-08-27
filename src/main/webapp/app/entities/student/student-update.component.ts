import { Component, Vue, Inject } from 'vue-property-decorator';

import {numeric, required} from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import StudentPackageService from '@/entities/student-package/student-package.service';
import { IStudentPackage } from '@/shared/model/student-package.model';

import { IStudent, Student } from '@/shared/model/student.model';
import StudentService from './student.service';
import { Grade } from '@/shared/model/enumerations/grade.model';

const validations: any = {
  student: {
    firstName: {
      required,
    },
    lastName: {
      required,
    },
    nationalCode: {
    },
    mobileNumber: {
      required,

    },
    grade: {
      required,

    },
    studentPackages:{
      required,
    }
  },
};

@Component({
  validations,
})
export default class StudentUpdate extends Vue {
  @Inject('studentService') private studentService: () => StudentService;
  @Inject('alertService') private alertService: () => AlertService;

  public student: IStudent = new Student();

  @Inject('studentPackageService') private studentPackageService: () => StudentPackageService;

  public studentPackages: IStudentPackage[] = [];
  public gradeValues: string[] = Object.keys(Grade);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.studentId) {
        vm.retrieveStudent(to.params.studentId);
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
    this.student.studentPackages = [];
  }

  public save(): void {
    this.isSaving = true;
    // if (this.student.id) {
    //   this.studentService()
    //     .update(this.student)
    //     .then(param => {
    //       this.isSaving = false;
    //       this.$router.go(-1);
    //       const message = this.$t('bamdadShop2App.student.updated', { param: param.id });
    //       return this.$root.$bvToast.toast(message.toString(), {
    //         toaster: 'b-toaster-top-center',
    //         title: 'Info',
    //         variant: 'info',
    //         solid: true,
    //         autoHideDelay: 5000,
    //       });
    //     })
    //     .catch(error => {
    //       this.isSaving = false;
    //       this.alertService().showHttpError(this, error.response);
    //     });
    // } else
    {
      this.studentService()
        .create(this.student)
        .then(param => {
          this.isSaving = false;
          this.$router.push('/');
          const message = this.$t('bamdadShop2App.student.created', { param: param.id });
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

  public retrieveStudent(studentId): void {
    this.studentService()
      .find(studentId)
      .then(res => {
        this.student = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.push('/');
  }

  public initRelationships(): void {
    this.studentPackageService()
      .retrieve()
      .then(res => {
        this.studentPackages = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      return selectedVals.find(value => option.id === value.id) ?? option;
    }
    return option;
  }
}
