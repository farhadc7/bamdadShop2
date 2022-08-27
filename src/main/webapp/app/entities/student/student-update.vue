<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bamdadShop2App.student.home.createOrEditLabel"
          data-cy="StudentCreateUpdateHeading"
          v-text="$t('bamdadShop2App.student.home.createOrEditLabel')"
        >
          Create or edit a Student
        </h2>
        <div>
          <div class="form-group" v-if="student.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="student.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bamdadShop2App.student.firstName')" for="student-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="student-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.student.firstName.$invalid, invalid: $v.student.firstName.$invalid }"
              v-model="$v.student.firstName.$model"
              required
            />
            <div v-if="$v.student.firstName.$anyDirty && $v.student.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.student.firstName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bamdadShop2App.student.lastName')" for="student-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="student-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.student.lastName.$invalid, invalid: $v.student.lastName.$invalid }"
              v-model="$v.student.lastName.$model"
              required
            />
            <div v-if="$v.student.lastName.$anyDirty && $v.student.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.student.lastName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
<!--          <div class="form-group">-->
<!--            <label class="form-control-label" v-text="$t('bamdadShop2App.student.nationalCode')" for="student-nationalCode"-->
<!--              >National Code</label-->
<!--            >-->
<!--            <input-->
<!--              type="text"-->
<!--              class="form-control"-->
<!--              name="nationalCode"-->
<!--              id="student-nationalCode"-->
<!--              data-cy="nationalCode"-->
<!--              :class="{ valid: !$v.student.nationalCode.$invalid, invalid: $v.student.nationalCode.$invalid }"-->
<!--              v-model="$v.student.nationalCode.$model"-->
<!--              required-->
<!--            />-->
<!--          </div>-->
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bamdadShop2App.student.mobileNumber')" for="student-mobileNumber"
              >Mobile Number</label
            >
            <input
              type="text"
              class="form-control"
              name="mobileNumber"
              id="student-mobileNumber"
              data-cy="mobileNumber"
              :class="{ valid: !$v.student.mobileNumber.$invalid, invalid: $v.student.mobileNumber.$invalid }"
              v-model="$v.student.mobileNumber.$model"
              required
            />
            <div v-if="$v.student.mobileNumber.$anyDirty && $v.student.mobileNumber.$invalid">
              <small class="form-text text-danger" v-if="!$v.student.mobileNumber.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>

          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bamdadShop2App.student.grade')" for="student-grade">Grade</label>
            <select
              class="form-control"
              name="grade"
              :class="{ valid: !$v.student.grade.$invalid, invalid: $v.student.grade.$invalid }"
              v-model="$v.student.grade.$model"
              id="student-grade"
              data-cy="grade"
              required
            >
              <option v-for="grade in gradeValues" :key="grade" v-bind:value="grade" v-bind:label="$t('bamdadShop2App.Grade.' + grade)">
                {{ grade }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="$t('bamdadShop2App.student.studentPackage')" for="student-studentPackage">Student Package</label>
            <select
              required
              class="form-control"
              id="student-studentPackages"
              data-cy="studentPackage"
              multiple
              name="studentPackage"
              v-if="student.studentPackages !== undefined"
              v-model="student.studentPackages"
            >
              <option
                v-bind:value="getSelected(student.studentPackages, studentPackageOption)"
                v-for="studentPackageOption in studentPackages"
                :key="studentPackageOption.id" v-if="studentPackageOption.grade===student.grade"
              >
                {{ studentPackageOption.packageName }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.student.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./student-update.component.ts"></script>
