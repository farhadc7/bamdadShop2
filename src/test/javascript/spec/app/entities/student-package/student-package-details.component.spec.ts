/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import StudentPackageDetailComponent from '@/entities/student-package/student-package-details.vue';
import StudentPackageClass from '@/entities/student-package/student-package-details.component';
import StudentPackageService from '@/entities/student-package/student-package.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('StudentPackage Management Detail Component', () => {
    let wrapper: Wrapper<StudentPackageClass>;
    let comp: StudentPackageClass;
    let studentPackageServiceStub: SinonStubbedInstance<StudentPackageService>;

    beforeEach(() => {
      studentPackageServiceStub = sinon.createStubInstance<StudentPackageService>(StudentPackageService);

      wrapper = shallowMount<StudentPackageClass>(StudentPackageDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { studentPackageService: () => studentPackageServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStudentPackage = { id: 123 };
        studentPackageServiceStub.find.resolves(foundStudentPackage);

        // WHEN
        comp.retrieveStudentPackage(123);
        await comp.$nextTick();

        // THEN
        expect(comp.studentPackage).toBe(foundStudentPackage);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStudentPackage = { id: 123 };
        studentPackageServiceStub.find.resolves(foundStudentPackage);

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
