<template>
  <div>
    <h2 id="page-heading" data-cy="StudentHeading">
      <span v-text="$t('bamdadShop2App.student.home.title')" id="student-heading">Students</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('bamdadShop2App.student.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'StudentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-student"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('bamdadShop2App.student.home.createLabel')"> Create a new Student </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && students && students.length === 0">
      <span v-text="$t('bamdadShop2App.student.home.notFound')">No students found</span>
    </div>
    <div class="table-responsive" v-if="students && students.length > 0">
      <table class="table table-striped" aria-describedby="students">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('bamdadShop2App.student.firstName')">First Name</span></th>
            <th scope="row"><span v-text="$t('bamdadShop2App.student.lastName')">Last Name</span></th>
            <th scope="row"><span v-text="$t('bamdadShop2App.student.nationalCode')">National Code</span></th>
            <th scope="row"><span v-text="$t('bamdadShop2App.student.mobileNumber')">Mobile Number</span></th>
            <th scope="row"><span v-text="$t('bamdadShop2App.student.grade')">Grade</span></th>
            <th scope="row"><span v-text="$t('bamdadShop2App.student.studentPackage')">Student Package</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="student in students" :key="student.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StudentView', params: { studentId: student.id } }">{{ student.id }}</router-link>
            </td>
            <td>{{ student.firstName }}</td>
            <td>{{ student.lastName }}</td>
            <td>{{ student.nationalCode }}</td>
            <td>{{ student.mobileNumber }}</td>
            <td v-text="$t('bamdadShop2App.Grade.' + student.grade)">{{ student.grade }}</td>
            <td>
              <span v-for="(studentPackage, i) in student.studentPackages" :key="studentPackage.id"
                >{{ i > 0 ? ', ' : '' }}
                <router-link
                  class="form-control-static"
                  :to="{ name: 'StudentPackageView', params: { studentPackageId: studentPackage.id } }"
                  >{{ studentPackage.id }}</router-link
                >
              </span>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StudentView', params: { studentId: student.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StudentEdit', params: { studentId: student.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(student)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="bamdadShop2App.student.delete.question" data-cy="studentDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-student-heading" v-text="$t('bamdadShop2App.student.delete.question', { id: removeId })">
          Are you sure you want to delete this Student?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-student"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeStudent()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./student.component.ts"></script>
