<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0"
        >Normas técnicas</span
      >
    </q-toolbar-title>

    <br />

    <div class="row q-col-gutter-xs">
      <q-input
        class="col-10"
        filled
        @input="doSearch"
        v-model="searchText"
        :dense="true"
        placeholder="Buscar norma (busque por título, conteúdo, etc)"
        hint="Clique no ícone a direita para buscar"
      >
        <template v-slot:prepend>
          <q-icon name="search" />
        </template>
        <template v-slot:append>
          <q-icon name="send" @click="doSearch" class="cursor-pointer" />
        </template>
      </q-input>

      <q-btn
        class="col-2"
        color="primary"
        label="Nova norma"
        @click="showAddNormaWindow = true"
        dense
        style="height: 40px; top: 5px"
      />
    </div>
    <br />

    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Orgão</th>
          <th class="text-left">Título</th>
          <th class="text-left">Versão</th>
          <th class="text-left">Edição</th>
          <th class="text-left">Data de edição</th>
          <th class="text-left">Início de validade</th>
          <th class="text-left">Categoria</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="standard in standardListSearch" :key="standard.codigo">
          <td class="text-left">{{ standard.orgao }}</td>
          <td class="text-left">{{ standard.titulo }}</td>
          <td class="text-left">{{ standard.versao }}</td>
          <td class="text-left">{{ standard.numeroEdicao }}</td>
          <td class="text-left">
            {{ moment(standard.dataEdicao, "YYYY-MM-DD").format("DD/MM/YYYY") }}
          </td>
          <td class="text-left">
            {{
              moment(standard.inicioValidade, "YYYY-MM-DD").format("DD/MM/YYYY")
            }}
          </td>
          <td class="text-left">{{ standard.categoria }}</td>
          <td class="text-left q-gutter-xs">
            <q-btn color="primary" label="Visualizar" dense />
            <q-btn color="teal" label="Download" dense />
          </td>
        </tr>
      </tbody>
    </q-markup-table>

    <q-dialog v-model="showAddNormaWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5"> Nova norma </span>
        </q-card-section>

        <q-card-section>
          <div class="row q-col-gutter-xs">
            <q-input
              filled
              v-model="newStandard.orgao"
              label="Orgão"
              class="col-4"
            />

            <q-input
              filled
              v-model="newStandard.titulo"
              label="Título"
              class="col-8"
            />

            <q-input
              filled
              v-model="newStandard.resumo"
              label="Resumo"
              class="col-8"
            />

            <q-input
              filled
              v-model="newStandard.categoria"
              label="Categoria"
              class="col-4"
            />

            <q-input
              filled
              mask="####"
              v-model="newStandard.versao"
              label="Versão (ano da publicação)"
              class="col-6"
            />

            <q-input
              filled
              type="number"
              v-model="newStandard.numeroEdicao"
              label="Número edição"
              class="col-6"
            />

            <q-input
              filled
              mask="##/##/####"
              v-model="newStandard.dataEdicao"
              label="Data edição"
              class="col-6"
            />

            <q-input
              filled
              mask="##/##/####"
              v-model="newStandard.inicioValidade"
              label="Data de início de validade"
              class="col-6"
            />

            <q-input
              filled
              type="textarea"
              v-model="newStandard.descricao"
              label="Descrição"
              class="col-12"
            />

            <q-file filled bottom-slots v-model="file" label="Arquivo" counter>
              <template v-slot:prepend>
                <q-icon name="cloud_upload"/>
              </template>

              <template v-slot:hint>Arquivo da Norma</template>
            </q-file>
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Cancelar"
            color="red"
            v-close-popup
            @click="resetForm"
          />
          <q-btn label="Cadastrar" color="primary" @click="validate" />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-dialog v-model="showNormaRemoveWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Excluir filial</span>
        </q-card-section>

        <q-card-section v-if="normaToExclude != null">
          <div>
            Tem certeza que deseja excluir a filial
            {{ normaToExclude.titulo }} ?
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Não"
            color="red"
            v-close-popup
            @click="normaToExclude = null"
          />
          <q-btn label="Sim" color="primary" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import { getStandards } from "../services/Norma";

export default {
  name: "NormasTecnicas",
  methods: {
    resetForm() {
      this.newStandard = {
        orgao: "",
        titulo: "",
        resumo: "",
        descricao: "",
        versao: "",
        numeroEdicao: "",
        dataEdicao: "",
        inicioValidade: "",
        categoria: "",
      };
    },
    validate() {
      if (this.newStandard.orgao.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Orgão deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newStandard.titulo.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Título deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newStandard.resumo.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Resumo deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newStandard.categoria.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Categoria deve ser preenchida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newStandard.versao.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Versão deve ser preenchida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newStandard.numeroEdicao.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Número da edição deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newStandard.dataEdicao.match(/\d/g).join("").length != 8) {
        this.$q.notify({
          color: "negative",
          message: "Data da edição inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newStandard.inicioValidade.match(/\d/g).join("").length != 8) {
        this.$q.notify({
          color: "negative",
          message: "Data de início de validade inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newStandard.descricao.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Descrição deve ser preenchida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }
    },
    doSearch() {
      if (this.searchText == "") {
        this.standardListSearch = this.standardList;
        return;
      }
      this.searchText = this.searchText.toLowerCase();
      this.standardListSearch = this.standardList.filter(
        (e) =>
          e.orgao.toLowerCase().includes(this.searchText) ||
          e.categoria.toLowerCase().includes(this.searchText) ||
          e.titulo.toLowerCase().includes(this.searchText) ||
          e.versao.toLowerCase().includes(this.searchText)
      );
    },
  },
  async mounted() {
    this.standardList = await getStandards();
    this.standardListSearch = this.standardList;
  },
  data() {
    return {
      searchText: "",
      newStandard: {
        orgao: "",
        titulo: "",
        resumo: "",
        descricao: "",
        versao: "",
        numeroEdicao: "",
        dataEdicao: "",
        inicioValidade: "",
        categoria: "",
      },
      standardList: [],
      standardListSearch: [],
      moment: require("moment"),
      showAddNormaWindow: false,
      showNormaRemoveWindow: false,
      normaToExclude: null,
      file: null
    };
  },
};
</script>


<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>