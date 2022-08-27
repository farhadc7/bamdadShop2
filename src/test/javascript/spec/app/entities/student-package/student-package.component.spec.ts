/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StudentPackageComponent from '@/entities/student-package/student-package.vue';
import StudentPackageClass from '@/entities/student-package/student-package.component';
import StudentPackageService from '@/entities/student-package/student-package.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('StudentPackage Management Component', () => {
    let wrapper: Wrapper<StudentPackageClass>;
    let comp: StudentPackageClass;
    let studentPackageServiceStub: SinonStubbedInstance<StudentPackageService>;

    beforeEach(() => {
      studentPackageServiceStub = sinon.createStubInstance<StudentPackageService>(StudentPackageService);
      studentPackageServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StudentPackageClass>(StudentPackageComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          studentPackageService: () => studentPackageServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      studentPackageServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStudentPackages();
      await comp.$nextTick();

      // THEN
      expect(studentPackageServiceStub.retrieve.called).toBeTruthy();
      expect(comp.studentPackages[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      studentPackageServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(studentPackageServiceStub.retrieve.callCount).toEqual(1);

      comp.removeStudentPackage();
      await comp.$nextTick();

      // THEN
      expect(studentPackageServiceStub.delete.called).toBeTruthy();
      expect(studentPackageServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
