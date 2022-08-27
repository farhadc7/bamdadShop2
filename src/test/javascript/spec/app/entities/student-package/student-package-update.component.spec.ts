/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StudentPackageUpdateComponent from '@/entities/student-package/student-package-update.vue';
import StudentPackageClass from '@/entities/student-package/student-package-update.component';
import StudentPackageService from '@/entities/student-package/student-package.service';

import StudentService from '@/entities/student/student.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('StudentPackage Management Update Component', () => {
    let wrapper: Wrapper<StudentPackageClass>;
    let comp: StudentPackageClass;
    let studentPackageServiceStub: SinonStubbedInstance<StudentPackageService>;

    beforeEach(() => {
      studentPackageServiceStub = sinon.createStubInstance<StudentPackageService>(StudentPackageService);

      wrapper = shallowMount<StudentPackageClass>(StudentPackageUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          studentPackageService: () => studentPackageServiceStub,
          alertService: () => new AlertService(),

          studentService: () =>
            sinon.createStubInstance<StudentService>(StudentService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.studentPackage = entity;
        studentPackageServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(studentPackageServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.studentPackage = entity;
        studentPackageServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(studentPackageServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStudentPackage = { id: 123 };
        studentPackageServiceStub.find.resolves(foundStudentPackage);
        studentPackageServiceStub.retrieve.resolves([foundStudentPackage]);

        // WHEN
        comp.beforeRouteEnter({ params: { studentPackageId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.studentPackage).toBe(foundStudentPackage);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
