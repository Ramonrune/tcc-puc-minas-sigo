<template>
  <div class="q-pa-md" v-if="render">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0">Filiais</span>
    </q-toolbar-title>
    <q-btn
      color="primary"
      label="Nova filial"
      dense
      class="float-right"
      @click="
        mode = 'ADD';
        showCompanyWindow = true;
      "
    />

    <br />
    <br />

    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Nome</th>
          <th class="text-left">CNPJ</th>
          <th class="text-left">Telefone</th>
          <th class="text-left">Cidade</th>
          <th class="text-left">Estado</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="company in companyList" :key="company.codigo">
          <td class="text-left">{{ company.nome }}</td>
          <td class="text-left">
            {{
              company.cnpj.replace(
                /^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/,
                "$1.$2.$3/$4-$5"
              )
            }}
          </td>
          <td class="text-left">
            {{
              company.telefone.replace(/^(\d{2})(\d{4})(\d{4})/, "($1) $2-$3")
            }}
          </td>

          <td class="text-left">{{ company.cidade }}</td>
          <td class="text-left">{{ company.estado }}</td>

          <td class="text-left q-gutter-xs">
            <q-btn
              color="teal"
              label="Editar"
              dense
              @click="showEdit(company)"
            />
            <q-btn
              color="red"
              label="Excluir"
              dense
              @click="
                companyToExclude = company;
                showCompanyRemoveWindow = true;
              "
            />
          </td>
        </tr>
      </tbody>
    </q-markup-table>
    <q-dialog v-model="showCompanyWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">{{
            mode == "ADD" ? "Nova filial" : "Editar filial"
          }}</span>
        </q-card-section>

        <q-card-section>
          <div class="row q-col-gutter-xs">
            <q-input
              filled
              v-model="newCompany.nome"
              label="Nome"
              class="col-8"
            />

            <q-input
              filled
              v-model="newCompany.cnpj"
              label="CNPJ"
              class="col-4"
              mask="##.###.###/####-##"
              @input="onCepChange"
              :disable="mode == 'ADD' ? false : true"
            />

            <q-input
              filled
              v-model="newCompany.telefone"
              label="Telefone"
              class="col-4"
              mask="(##) ####-####"
            />
            <q-input
              filled
              v-model="newCompany.cep"
              label="CEP"
              class="col-4"
              mask="#####-###"
              @input="onCepChange"
            />

            <q-select
              filled
              v-model="newCompany.estado"
              :options="stateOptions"
              label="Estado"
              class="col-4"
            />

            <q-input
              filled
              v-model="newCompany.cidade"
              label="Cidade"
              class="col-12"
            />

            <q-input
              filled
              v-model="newCompany.logradouro"
              label="Logradouro"
              class="col-12"
            />
            <q-input
              filled
              v-model="newCompany.bairro"
              label="Bairro"
              class="col-8"
            />
            <q-input
              filled
              v-model="newCompany.numero"
              label="Número"
              class="col-4"
            />
            <q-input
              filled
              v-model="newCompany.complemento"
              label="Complemento"
              class="col-12"
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
            :label="mode == 'ADD' ? 'Cadastrar' : 'Editar'"
            color="primary"
            @click="validate"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-dialog v-model="showCompanyRemoveWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Excluir filial</span>
        </q-card-section>

        <q-card-section v-if="companyToExclude != null">
          <div>
            Tem certeza que deseja excluir a filial
            {{ companyToExclude.nome }} ?
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Não"
            color="red"
            v-close-popup
            @click="companyToExclude = null"
          />
          <q-btn label="Sim" color="primary" @click="removeCompany" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import axios from "axios";
import { cnpjValidation } from "../util/validadorCnpj";
import {
  addNewCompany,
  editCompany,
  getCompanies,
  companyExists,
  deleteCompany,
} from "../services/Filial";

import {isMyUserAdmin} from '../services/Usuario';

export default {
  name: "Filiais",

  data() {
    return {
      render: false,
      companyList: [],
      showCompanyWindow: false,
      showCompanyRemoveWindow: false,
      companyToExclude: null,
      mode: "",

      stateOptions: [
        { label: "Acre", value: "AC" },
        { label: "Alagoas", value: "AL" },
        { label: "Amapá", value: "AP" },
        { label: "Amazonas", value: "AM" },
        { label: "Bahia", value: "BA" },
        { label: "Ceará", value: "CE" },
        { label: "Distrito Federal", value: "DF" },
        { label: "Espírito Santo", value: "ES" },
        { label: "Goiás", value: "GO" },
        { label: "Maranhão", value: "MA" },
        { label: "Mato Grosso", value: "MT" },
        { label: "Mato Grosso do Sul", value: "MS" },
        { label: "Minas Gerais", value: "MG" },
        { label: "Pará", value: "PA" },
        { label: "Paraíba", value: "PB" },
        { label: "Paraná", value: "PR" },
        { label: "Pernambuco", value: "PE" },
        { label: "Piauí", value: "PI" },
        { label: "Rio de Janeiro", value: "RJ" },
        { label: "Rio Grande do Norte", value: "RN" },
        { label: "Rio Grande do Sul", value: "RS" },
        { label: "Rondônia", value: "RO" },
        { label: "Roraima", value: "RR" },
        { label: "Santa Catarina", value: "SC" },
        { label: "São Paulo", value: "SP" },
        { label: "Sergipe", value: "SE" },
        { label: "Tocantins", value: "TO" },
      ],
      newCompany: {
        nome: "",
        cnpj: "",
        telefone: "",
        logradouro: "",
        numero: "",
        complemento: "",
        bairro: "",
        cep: "",
        cidade: "",
        estado: { label: "São Paulo", value: "SP" },
      },
    };
  },

  beforeMount() {
    console.log(isMyUserAdmin() + " ------------------------")
    if (!isMyUserAdmin()) {
      window.location.href = "#/sigo/403";
      return;
    }
    this.render = true;
  },
  async mounted() {
    await this.refreshList();
  },
  methods: {
    resetForm() {
      this.newCompany = {
        nome: "",
        cnpj: "",
        telefone: "",
        logradouro: "",
        numero: "",
        complemento: "",
        bairro: "",
        cep: "",
        cidade: "",
        estado: { label: "São Paulo", value: "SP" },
      };
    },
    showEdit(company) {
      this.mode = "EDIT";
      const cloneCompany = { ...company };

      this.newCompany = cloneCompany;
      this.newCompany.estado = this.stateOptions.filter(
        (e) => e.value == cloneCompany.estado
      )[0];
      this.newCompany.telefone = cloneCompany.telefone.replace(
        /^(\d{2})(\d{4})(\d{4})/,
        "($1) $2-$3"
      );
      this.newCompany.cep = cloneCompany.cep.replace(
        /^(\d{5})(\d{3})/,
        "$1-$2"
      );

      console.log(this.newCompany);
      this.showCompanyWindow = true;
    },
    async removeCompany() {
      let response = await deleteCompany(this.companyToExclude.codigo);
      this.companyToExclude = null;
      this.showCompanyRemoveWindow = false;

      if (response != null && response.status == 204) {
        await this.refreshList();
        this.$q.notify({
          color: "positive",
          message: "Filial excluida com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        if (response.status == 409) {
          this.$q.notify({
            color: "negative",
            message: "Essa filial está vinculada a um usuário!",
            position: "top",
            timeout: 1000,
          });
          return;
        }

        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao remover a filial, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    async refreshList() {
      this.companyList = await getCompanies();
    },
    async cnpjExists(cnpj) {
      return await companyExists(cnpj);
    },
    async onCepChange(cep) {
      cep = cep.replace("-", "");
      if (cep.length != 8) {
        return;
      }
      let response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
      console.log(response);
      if (response.status == 200) {
        let {
          bairro,
          localidade,
          logradouro,
          estado,
          complemento,
          uf,
        } = response.data;
        this.newCompany.bairro = bairro;
        this.newCompany.logradouro = logradouro;
        this.newCompany.cidade = localidade;
        this.newCompany.complemento = complemento;
        this.newCompany.estado = this.stateOptions.filter(
          (e) => e.value == uf
        )[0];
      }
    },
    async validate() {
      if (this.newCompany.nome.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Nome deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (cnpjValidation(this.newCompany.cnpj) == false) {
        this.$q.notify({
          color: "negative",
          message: "CNPJ inválido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.mode == "ADD") {
        let companyExists = await this.cnpjExists(this.newCompany.cnpj);
        if (companyExists) {
          this.$q.notify({
            color: "negative",
            message: "Filial já cadastrada",
            position: "top",
            timeout: 1000,
          });
          return;
        }
      }

      if (this.newCompany.telefone.length != 14) {
        this.$q.notify({
          color: "negative",
          message: "Telefone inválido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newCompany.cep.length != 9) {
        this.$q.notify({
          color: "negative",
          message: "CEP inválido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newCompany.cidade.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Cidade deve ser preenchida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newCompany.estado == null) {
        this.$q.notify({
          color: "negative",
          message: "Estado deve ser preenchida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newCompany.logradouro.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Estado deve ser preenchida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newCompany.bairro.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Bairro deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newCompany.numero.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Número deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      let body = {
        nome: this.newCompany.nome,
        cnpj: this.newCompany.cnpj.match(/\d/g).join(""),
        telefone: this.newCompany.telefone.match(/\d/g).join(""),
        logradouro: this.newCompany.logradouro,
        numero: this.newCompany.numero,
        complemento: this.newCompany.complemento,
        bairro: this.newCompany.bairro,
        cep: this.newCompany.cep,
        cidade: this.newCompany.cidade,
        estado: this.newCompany.estado.value,
      };

      if (this.mode == "ADD") {
        let response = await addNewCompany(body);
        if (response != null && response.status == 201) {
          this.$q.notify({
            color: "positive",
            message: "Filial cadastrada com sucesso!",
            position: "top",
            timeout: 1000,
          });
          this.newCompany = {
            nome: "",
            cnpj: "",
            telefone: "",
            logradouro: "",
            numero: "",
            complemento: "",
            bairro: "",
            cep: "",
            cidade: "",
            estado: { label: "São Paulo", value: "SP" },
          };

          this.showCompanyWindow = false;
          await this.refreshList();
        } else {
          this.$q.notify({
            color: "negative",
            message:
              "Ocorreu um problema ao cadastrar uma nova filial, tente novamente mais tarde!",
            position: "top",
            timeout: 1000,
          });
          return;
        }
      }

      if (this.mode == "EDIT") {
        body.codigo = this.newCompany.codigo;
        let response = await editCompany(body);
        if (response != null && response.status == 200) {
          this.$q.notify({
            color: "positive",
            message: "Filial editada com sucesso!",
            position: "top",
            timeout: 1000,
          });
          this.newCompany = {
            nome: "",
            cnpj: "",
            telefone: "",
            logradouro: "",
            numero: "",
            complemento: "",
            bairro: "",
            cep: "",
            cidade: "",
            estado: { label: "São Paulo", value: "SP" },
          };

          this.showCompanyWindow = false;
          await this.refreshList();
        } else {
          this.$q.notify({
            color: "negative",
            message:
              "Ocorreu um problema ao editar a filial, tente novamente mais tarde!",
            position: "top",
            timeout: 1000,
          });
          return;
        }
      }

      this.mode = "";
    },
  },
};
</script>


<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>