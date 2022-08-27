<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="student">
        <h2 class="jh-entity-heading" data-cy="studentDetailsHeading">
          <span v-text="$t('bamdadShop2App.student.detail.title')">Student</span> {{ student.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('bamdadShop2App.student.firstName')">First Name</span>
          </dt>
          <dd>
            <span>{{ student.firstName }}</span>
          </dd>
          <dt>
            <span v-text="$t('bamdadShop2App.student.lastName')">Last Name</span>
          </dt>
          <dd>
            <span>{{ student.lastName }}</span>
          </dd>
          <dt>
            <span v-text="$t('bamdadShop2App.student.nationalCode')">National Code</span>
          </dt>
          <dd>
            <span>{{ student.nationalCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('bamdadShop2App.student.mobileNumber')">Mobile Number</span>
          </dt>
          <dd>
            <span>{{ student.mobileNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('bamdadShop2App.student.grade')">Grade</span>
          </dt>
          <dd>
            <span v-text="$t('bamdadShop2App.Grade.' + student.grade)">{{ student.grade }}</span>
          </dd>
          <dt>
            <span v-text="$t('bamdadShop2App.student.studentPackage')">Student Package</span>
          </dt>
          <dd>
            <span v-for="(studentPackage, i) in student.studentPackages" :key="studentPackage.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'StudentPackageView', params: { studentPackageId: studentPackage.id } }">{{
                studentPackage.id
              }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="student.id" :to="{ name: 'StudentEdit', params: { studentId: student.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./student-details.component.ts"></script>
