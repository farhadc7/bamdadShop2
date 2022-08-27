import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStudentPackage } from '@/shared/model/student-package.model';

import StudentPackageService from './student-package.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class StudentPackage extends Vue {
  @Inject('studentPackageService') private studentPackageService: () => StudentPackageService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public studentPackages: IStudentPackage[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStudentPackages();
  }

  public clear(): void {
    this.retrieveAllStudentPackages();
  }

  public retrieveAllStudentPackages(): void {
    this.isFetching = true;
    this.studentPackageService()
      .retrieve()
      .then(
        res => {
          this.studentPackages = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IStudentPackage): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStudentPackage(): void {
    this.studentPackageService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('bamdadShop2App.studentPackage.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllStudentPackages();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
