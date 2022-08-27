<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bamdadShop2App.studentPackage.home.createOrEditLabel"
          data-cy="StudentPackageCreateUpdateHeading"
          v-text="$t('bamdadShop2App.studentPackage.home.createOrEditLabel')"
        >
          Create or edit a StudentPackage
        </h2>
        <div>
          <div class="form-group" v-if="studentPackage.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="studentPackage.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bamdadShop2App.studentPackage.packageName')" for="student-package-packageName"
              >Package Name</label
            >
            <input
              type="text"
              class="form-control"
              name="packageName"
              id="student-package-packageName"
              data-cy="packageName"
              :class="{ valid: !$v.studentPackage.packageName.$invalid, invalid: $v.studentPackage.packageName.$invalid }"
              v-model="$v.studentPackage.packageName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bamdadShop2App.studentPackage.grade')" for="student-package-grade">Grade</label>
            <select
              class="form-control"
              name="grade"
              :class="{ valid: !$v.studentPackage.grade.$invalid, invalid: $v.studentPackage.grade.$invalid }"
              v-model="$v.studentPackage.grade.$model"
              id="student-package-grade"
              data-cy="grade"
            >
              <option v-for="grade in gradeValues" :key="grade" v-bind:value="grade" v-bind:label="$t('bamdadShop2App.Grade.' + grade)">
                {{ grade }}
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
            :disabled="$v.studentPackage.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./student-package-update.component.ts"></script>
