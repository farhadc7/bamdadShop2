<template>
  <div>
    <div class="row">
      <div class="col-md-3">

      </div>
      <div class="col-md-9">
<!--        <h1 v-text="$t('error.title')">Error Page!</h1>-->

        <div v-if="errorMessage">

        </div>

      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./error.component.ts"></script>
