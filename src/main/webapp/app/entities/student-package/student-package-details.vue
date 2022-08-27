<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="studentPackage">
        <h2 class="jh-entity-heading" data-cy="studentPackageDetailsHeading">
          <span v-text="$t('bamdadShop2App.studentPackage.detail.title')">StudentPackage</span> {{ studentPackage.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('bamdadShop2App.studentPackage.packageName')">Package Name</span>
          </dt>
          <dd>
            <span>{{ studentPackage.packageName }}</span>
          </dd>
          <dt>
            <span v-text="$t('bamdadShop2App.studentPackage.grade')">Grade</span>
          </dt>
          <dd>
            <span v-text="$t('bamdadShop2App.Grade.' + studentPackage.grade)">{{ studentPackage.grade }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="studentPackage.id"
          :to="{ name: 'StudentPackageEdit', params: { studentPackageId: studentPackage.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./student-package-details.component.ts"></script>
