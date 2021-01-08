<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0">Usuários</span>
    </q-toolbar-title>
    <q-btn
      color="primary"
      label="Novo usuário"
      dense
      class="float-right"
      @click="
        mode = 'ADD';
        showUserWindow = true;
      "
    />

    <br />
    <br />

    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Nome</th>
          <th class="text-left">Email</th>
          <th class="text-left">Permissões</th>
          <th class="text-left">Filiais com acesso</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in userList" :key="user.codigo">
          <td class="text-left">{{ user.nome }}</td>

          <td class="text-left">{{ user.email }}</td>
          <td class="text-left">
            <div v-for="permissao in user.permissoes" :key="permissao.codigo">
              <q-badge color="primary">{{ permissao.descricao }}</q-badge>
            </div>
          </td>
          <td class="text-left">
            <div v-for="filial in user.filiais" :key="filial.codigo">
              <q-badge outline color="green" :label="filial.nome" />
            </div>
          </td>

          <td class="text-left q-gutter-xs">
            <q-btn color="teal" label="Editar" dense @click="showEdit(user)" />
            <q-btn
              color="red"
              label="Excluir"
              dense
              @click="
                userToExclude = user;
                showUserRemoveWindow = true;
              "
            />
          </td>
        </tr>
      </tbody>
    </q-markup-table>
    <q-dialog v-model="showUserWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">{{
            mode == "ADD" ? "Novo usuário" : "Editar usuário"
          }}</span>
        </q-card-section>

        <q-card-section>
          <div class="row q-col-gutter-xs">
            <q-input
              filled
              v-model="newUser.nome"
              label="Nome"
              class="col-12"
            />

            <q-input
              filled
              v-model="newUser.email"
              label="Email"
              class="col-12"
            />
            <q-input
              filled
              v-model="newUser.senha"
              label="Senha"
              class="col-6"
            />

            <q-input
              filled
              v-model="newUser.confirmarSenha"
              label="Confirmar senha"
              class="col-6"
            />

            <div class="row col-12">
              <q-select
                filled
                v-model="newUser.filiais"
                :options="companiesList"
                option-value="codigo"
                option-label="nome"
                emit-value
                map-options
                label="Filiais"
                class="col-8"
              />

              <q-btn
                color="primary"
                label="Adicionar filial"
                dense
                class="col-4"
                style="height: 55px;"
              />
            </div>
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

    <q-dialog v-model="showUserRemoveWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Excluir filial</span>
        </q-card-section>

        <q-card-section v-if="userToExclude != null">
          <div>
            Tem certeza que deseja excluir a filial
            {{ userToExclude.nome }} ?
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Não"
            color="red"
            v-close-popup
            @click="userToExclude = null"
          />
          <q-btn label="Sim" color="primary" @click="removeUser" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import axios from "axios";
import { cnpjValidation } from "../util/validadorCnpj";
import {
  addNewUser,
  editUser,
  getUsers,
  userExists,
  deleteUser,
} from "../services/Usuario";

import { getCompanies } from "../services/Filial";

export default {
  name: "Usuarios",

  data() {
    return {
      userList: [],
      companiesList: [],
      showUserWindow: false,
      showUserRemoveWindow: false,
      userToExclude: null,

      mode: "",
      newUser: {
        nome: "",
        email: "",
        senha: "",
        confirmarSenha: "",
        permissoes: [],
        filiais: [],
      },
    };
  },

  async mounted() {
    await this.refreshList();
  },
  methods: {
    resetForm() {
      this.newUser = {
        nome: "",
        email: "",
        senha: "",
        confirmarSenha: "",
        filiais: [],
        permissoes: [],
      };
    },
    showEdit(company) {
      this.mode = "EDIT";
      const cloneCompany = { ...company };

      this.newUser = cloneCompany;
      this.newUser.estado = this.stateOptions.filter(
        (e) => e.value == cloneCompany.estado
      )[0];
      this.newUser.telefone = cloneCompany.telefone.replace(
        /^(\d{2})(\d{4})(\d{4})/,
        "($1) $2-$3"
      );
      this.newUser.cep = cloneCompany.cep.replace(/^(\d{5})(\d{3})/, "$1-$2");

      console.log(this.newUser);
      this.showUserWindow = true;
    },
    async removeUser() {
      let response = await deleteUser(this.userToExclude.codigo);
      this.userToExclude = null;
      this.showUserRemoveWindow = false;

      if (response != null && response.status == 204) {
        await this.refreshList();
        this.$q.notify({
          color: "positive",
          message: "Usuário excluido com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao remover o usuário, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    async refreshList() {
      this.userList = await getUsers();
      console.log(this.userList);
      this.companiesList = await getCompanies();
    },
    async emailExists(email) {
      return await this.emailExists(email);
    },

    async validate() {
      if (this.newUser.nome.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Nome deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

      if (!re.test(String(this.newUser.email).toLowerCase())) {
        this.$q.notify({
          color: "negative",
          message: "E-mail inválido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.mode == "ADD") {
        let userExists = await this.emailExists(this.newUser.email);
        if (userExists) {
          this.$q.notify({
            color: "negative",
            message: "Já existe um usuário com esse e-mail!",
            position: "top",
            timeout: 1000,
          });
          return;
        }
      }

      if (this.newUser.senha == "") {
        this.$q.notify({
          color: "negative",
          message: "Senha não pode ser vazia!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newUser.confirmarSenha == "") {
        this.$q.notify({
          color: "negative",
          message: "Confirmação de senha não pode ser vazia!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newUser.senha != this.newUser.confirmarSenha) {
        this.$q.notify({
          color: "negative",
          message: "As senhas informadas não são iguais!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      let body = {
        nome: this.newUser.nome,
        email: this.newUser.email,
        senha: this.newUser.senha,
      };

      if (this.mode == "ADD") {
        let response = await addNewUser(body);
        if (response != null && response.status == 201) {
          this.$q.notify({
            color: "positive",
            message: "Usuário cadastrado com sucesso!",
            position: "top",
            timeout: 1000,
          });

          this.resetForm();

          this.showUserWindow = false;
          await this.refreshList();
        } else {
          this.$q.notify({
            color: "negative",
            message:
              "Ocorreu um problema ao cadastrar um novo usuário, tente novamente mais tarde!",
            position: "top",
            timeout: 1000,
          });
          return;
        }
      }

      if (this.mode == "EDIT") {
        body.codigo = this.newUser.codigo;
        let response = await editUser(body);
        if (response != null && response.status == 200) {
          this.$q.notify({
            color: "positive",
            message: "Usuário editado com sucesso!",
            position: "top",
            timeout: 1000,
          });

          this.resetForm();

          this.showUserWindow = false;
          await this.refreshList();
        } else {
          this.$q.notify({
            color: "negative",
            message:
              "Ocorreu um problema ao editar o usuário, tente novamente mais tarde!",
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