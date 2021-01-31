<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0"
        >Empresas de consultorias e assessorias</span
      >
    </q-toolbar-title>

    <q-btn
      v-if="admin == true"
      color="primary"
      label="Nova empresa de consultoria"
      @click="showAddConsultancyWindow = true"
      dense
      class="float-right"
    />
    <br />
    <br />
    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Nome</th>
          <th class="text-left">CNPJ</th>
          <th class="text-left">Telefone</th>
          <th class="text-left">Email</th>
          <th class="text-left">Data de registro</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="consultancy in consultancyList" :key="consultancy.codigo">
          <td class="text-left">{{ consultancy.nome }}</td>
          <td class="text-left">
            {{
              consultancy.cnpj.replace(
                /^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/,
                "$1.$2.$3/$4-$5"
              )
            }}
          </td>
          <td class="text-left">
            {{
              consultancy.telefone.replace(
                /^(\d{2})(\d{4})(\d{4})/,
                "($1) $2-$3"
              )
            }}
          </td>

          <td class="text-left">{{ consultancy.email }}</td>
          <td class="text-left">
            {{
              moment(consultancy.dataRegistro, "YYYY-MM-DD").format(
                "DD/MM/YYYY"
              )
            }}
          </td>
          <td class="text-left q-gutter-xs">
            <q-btn
              color="teal"
              label="Download"
              dense
              @click="downloadConsultancy(consultancy)"
            />
            <q-btn
              v-if="admin == true"
              color="red"
              label="Excluir"
              dense
              @click="
                consultancyToExclude = consultancy;
                showConsultancyRemoveWindow = true;
              "
            />
          </td>
        </tr>
      </tbody>
    </q-markup-table>

    <q-dialog v-model="showAddConsultancyWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5"> Nova empresa de consultoria </span>
        </q-card-section>

        <q-card-section>
          <div class="row q-col-gutter-xs">
            <q-input
              filled
              v-model="newConsultancy.nome"
              label="Nome"
              class="col-8"
            />

            <q-input
              filled
              v-model="newConsultancy.cnpj"
              label="CNPJ"
              class="col-4"
              mask="##.###.###/####-##"
            />

            <q-input
              filled
              v-model="newConsultancy.telefone"
              label="Telefone"
              class="col-6"
              mask="(##) ####-####"
            />

            <q-input
              filled
              v-model="newConsultancy.email"
              label="E-mail"
              class="col-6"
            />

       
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Cancelar"
            color="red"
            v-close-popup
            @click="resetForm"
          />
          <q-btn
            label="Cadastrar"
            color="primary"
            @click="validate"
            :loading="loadingNew"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-dialog v-model="showConsultancyRemoveWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Excluir empresa de consultoria</span>
        </q-card-section>

        <q-card-section v-if="consultancyToExclude != null">
          <div>
            Tem certeza que deseja excluir a empresa de consultoria
            {{ consultancyToExclude.nome }} ?
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Não"
            color="red"
            v-close-popup
            @click="consultancyToExclude = null"
          />
          <q-btn
            label="Sim"
            color="primary"
            @click="removeConsultancy"
            :loading="loadingRemove"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import Vue from "vue";

import {
  addNewConsultancy,
  getConsultancy,
  uploadConsultancy,
  deleteConsultancy,
  getConsultancyPdf,
} from "../services/Consultoria";

import { isMyUserAdmin } from "../services/Usuario";
import { cnpjValidation } from "../util/validadorCnpj";

import Moment from "moment";

export default {
  name: "NormasTecnicas",
  methods: {
    async removeConsultancy() {
      this.loadingRemove = true;
      let response = await deleteConsultancy(this.consultancyToExclude.codigo);
      this.consultancyToExclude = null;
      this.showConsultancyRemoveWindow = false;

      if (response != null && response.status == 204) {
        this.loadingRemove = false;

        await this.refreshList();
        this.$q.notify({
          color: "positive",
          message: "Empresa de consultoria excluida com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        this.loadingRemove = false;

        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao remover a empresa de consultoria, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    resetForm() {
      this.newConsultancy = {
        nome: "",
        cnpj: "",
        telefone: "",
        email: "",
        codigoUsuario: "",
      };
    },
    async validate() {
      if (this.newConsultancy.nome.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Nome deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      this.newConsultancy.cnpj = this.newConsultancy.cnpj.replace(/\D/g, "");
      if (cnpjValidation(this.newConsultancy.cnpj) == false) {
        this.$q.notify({
          color: "negative",
          message: "CNPJ inválido!!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      this.newConsultancy.telefone = this.newConsultancy.telefone.replace(
        /\D/g,
        ""
      );

      if (this.newConsultancy.telefone.length != 10) {
        this.$q.notify({
          color: "negative",
          message: "Telefone inválido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      let reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      if (!reg.test(this.newConsultancy.email)) {
        this.$q.notify({
          color: "negative",
          message: "E-mail inválido!",
          position: "top",
          timeout: 1000,
        });

        return;
      }


      this.loadingNew = true;

      let body = { ...this.newConsultancy };

      console.log(body);
      let response = await addNewConsultancy(body);
      console.log(response);

      if (response == null || response.status != 201) {
        this.loadingNew = false;

        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao tentar adicionar uma nova empresa de consultoria, por favor tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });

        return;
      }

      this.loadingNew = false;

      this.showAddConsultancyWindow = false;
      this.resetForm();
      this.refreshList();

      this.$q.notify({
        color: "positive",
        message: "Empresa de consultoria adicionada com sucesso!",
        position: "top",
        timeout: 1000,
      });
      return;
    },
    async refreshList() {
      this.consultancyList = await getConsultancy();
    },
    downloadConsultancy(consultancy) {
      getConsultancyPdf(consultancy);
    },
  },
  async mounted() {
    this.admin = isMyUserAdmin();
    this.refreshList();
  },

  data() {
    return {
      loadingNew: false,
      loadingRemove: false,
      admin: false,
      searchText: "",
      newConsultancy: {
        nome: "",
        cnpj: "",
        telefone: "",
        email: "",
        codigoUsuario: "",
      },
      consultancyList: [],
      moment: require("moment"),
      showAddConsultancyWindow: false,
      showConsultancyRemoveWindow: false,
      consultancyToExclude: null
    };
  },
};
</script>


<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>