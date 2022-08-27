<template>
  <div class="home row">
    <div class="col-md-9">
      <div id="start-farhad">
        <div
          center
          href="javascript:void(0);"
          id="account-menu"
          :class="{ 'router-link-active': subIsActive('/account') }"
          active-class="active"
          class="pointer"
          data-cy="accountMenu"
        >
          <span slot="button-content" class="navbar-dropdown-menu">
            <font-awesome-icon icon="user" />
            <span class="no-bold" v-text="$t('global.menu.account.main')"> Account </span>
          </span>
          <b-dropdown-item data-cy="settings" to="/account/settings" tag="b-dropdown-item" v-if="authenticated" active-class="active">
            <font-awesome-icon icon="wrench" />
            <span v-text="$t('global.menu.account.settings')">Settings</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="passwordItem" to="/account/password" tag="b-dropdown-item" v-if="authenticated" active-class="active">
            <font-awesome-icon icon="lock" />
            <span v-text="$t('global.menu.account.password')">Password</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="logout" v-if="authenticated" v-on:click="logout()" id="logout" active-class="active">
            <font-awesome-icon icon="sign-out-alt" />
            <span v-text="$t('global.menu.account.logout')">Sign out</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="login" v-if="!authenticated" v-on:click="openLogin()" id="login" active-class="active">
            <font-awesome-icon icon="sign-in-alt" />
            <span v-text="$t('global.menu.account.login')">Sign in</span>
          </b-dropdown-item>
          <b-dropdown-item
            data-cy="register"
            to="/register"
            tag="b-dropdown-item"
            id="register"
            v-if="!authenticated"
            active-class="active"
          >
            <font-awesome-icon icon="user-plus" />
            <span v-text="$t('global.menu.account.register')">Register</span>
          </b-dropdown-item>
        </div>
      </div>




      <div class="modal-body">
        <div class="row justify-content-center">
          <div class="col-md-8">
            <b-alert show data-cy="loginError" variant="danger" v-if="authenticationError" v-html="$t('login.messages.error.authentication')">
              <strong>Failed to sign in!</strong> Please check your credentials and try again.
            </b-alert>
          </div>
          <div class="col-md-8">
            <b-form role="form" v-on:submit.prevent="doLogin()">
              <b-form-group v-bind:label="$t('global.form[\'username.label\']')" label-for="username">
                <b-form-input
                  id="username"
                  type="text"
                  name="username"
                  autofocus
                  v-bind:placeholder="$t('global.form[\'username.placeholder\']')"
                  v-model="login"
                  data-cy="username"
                >
                </b-form-input>
              </b-form-group>
              <b-form-group v-bind:label="$t('login.form.password')" label-for="password">
                <b-form-input
                  id="password"
                  type="password"
                  name="password"
                  v-model.trim="name"
                  v-bind:placeholder="$t('login.form[\'password.placeholder\']')"
                  v-model="password"
                  data-cy="password"
                >
                </b-form-input>
              </b-form-group>
              <b-form-checkbox id="rememberMe" name="rememberMe" v-model="rememberMe" checked>
                <span v-text="$t('login.form.rememberme')">Remember me</span>
              </b-form-checkbox>
              <div>
                <b-button data-cy="submit" type="submit" variant="primary" v-text="$t('login.form.button')">Sign in</b-button>
              </div>
            </b-form>
            <p></p>
            <div>
              <b-alert show variant="warning">
                <b-link
                  :to="'/account/reset/request'"
                  class="alert-link"
                  v-text="$t('login.password.forgot')"
                  data-cy="forgetYourPasswordSelector"
                >Did you forget your password?</b-link
                >
              </b-alert>
            </div>
            <div>

            </div>
          </div>
        </div>
      </div>


    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>

