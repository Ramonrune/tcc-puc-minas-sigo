<template>
  <q-page
    class="window-height window-width row justify-center items-center"
    style="
      background-color: #fff;
      background-repeat: no-repeat;
      background-size: cover;
      background-position: right;
    "
  >
    <div class="column q-pa-lg">
      <div class="row">
        <q-card square class="shadow-5" style="width: 400px">
          <q-card-section class="bg-white">
            <br />
            <img
              alt="Sigo"
              src="~assets/sigo-logo.png"
              style="position: absolute; left: 8px; top: 30px"
            />
            <br />
            <span style="position: relative; top: 20px; font-size: 18px"
              >Entre com seu e-mail e senha</span
            >
          </q-card-section>
          <q-card-section>
            <q-form class="">
              <q-input
                square
                clearable
                v-model="email"
                type="email"
                label="Email"
              >
                <template v-slot:prepend>
                  <q-icon name="email" />
                </template>
              </q-input>
              <q-input
                square
                clearable
                v-model="password"
                type="password"
                label="Senha"
              >
                <template v-slot:prepend>
                  <q-icon name="lock" />
                </template>
              </q-input>
            </q-form>
          </q-card-section>

          <q-card-actions class="q-px-lg">
            <q-btn
              unelevated
              size="md"
              :loading="loading"
              color="blue"
              class="full-width text-white"
              label="Entrar"
              @click="this.validate"
            />
          </q-card-actions>
          <br>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script>
import { login } from "../services/Login";
import { getUserInfo } from "../services/Usuario";
import {
  isMyUserAdmin,
  isMyUserCommon,
  isMyUserAudit,
} from "../services/Usuario";

export default {
  name: "Login",

  data() {
    return {
      //email: "admin@sigo.com",
      //password: "admin",
      email: 'consultor@gmail.com',
      password: '1111',
      loading: false,
    };
  },

  beforeMount() {
    let token = localStorage.getItem("TOKEN");

    if (token != "null" && token != null) {
      window.location.href = "#/sigo";
    }
  },

  methods: {
    async validate() {
      let reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      if (!reg.test(this.email)) {
        this.$q.notify({
          color: "negative",
          message: "E-mail inválido!",
          position: "top",
          timeout: 1000,
        });

        return;
      }

      if (this.password.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Informe a senha!",
          position: "top",
          timeout: 1000,
        });

        return;
      }
      this.loading = true;
      let response = await login(this.email, this.password);
      if (response != null && response.status == 200) {
        let { access_token, codigo, nome } = response.data;
        localStorage.setItem("TOKEN", access_token);
        localStorage.setItem("CODE", codigo);
        localStorage.setItem("NAME", nome);

        let responseUserInfo = await getUserInfo();

        if (responseUserInfo != null) {
          localStorage.setItem(
            "USER_DATA",
            JSON.stringify(responseUserInfo.data)
          );


          if (isMyUserAdmin() || isMyUserCommon()) {
            window.location.href = "#/sigo";
          }

          if (isMyUserAudit()) {
            window.location.href = "#/sigo/consultor";
          }

        } else {
          this.$q.notify({
            color: "negative",
            message:
              "Ocorreu um problema no login, tente novamente mais tarde!",
            position: "top",
            timeout: 1000,
          });
        }
      } else {
        if (response.status == 400) {
          this.$q.notify({
            color: "negative",
            message: "Email e/ou senha inválidos!",
            position: "top",
            timeout: 1000,
          });

          return;
        }

        this.$q.notify({
          color: "negative",
          message: "Ocorreu um problema no login, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }

      this.loading = false;

      console.log(response);
    },
  },
};
</script>
