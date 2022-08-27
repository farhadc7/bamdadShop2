<template>
  <div>
    <b-dropdown-item to="/student">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.student')">Student</span>
    </b-dropdown-item>
    <b-dropdown-item to="/stationary-package">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.stationaryPackage')">Stationary Package</span>
    </b-dropdown-item>
    <b-dropdown-item to="/student-package">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.studentPackage')">Student Package</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
