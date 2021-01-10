<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0"
        >Normas técnicas</span
      >
    </q-toolbar-title>

    <br />

    <q-input
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
        <q-icon name="send" @click="text = ''" class="cursor-pointer" />
      </template>
    </q-input>
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
  </div>
</template>
<script>
import { getStandards } from "../services/Norma";

export default {
  name: "NormasTecnicas",
  methods: {
    doSearch(){
      if(this.searchText == ''){
        this.standardListSearch = this.standardList;
        return;
      }
      this.searchText  = this.searchText.toLowerCase();
      this.standardListSearch = this.standardList.filter( e=> e.orgao.toLowerCase().includes(this.searchText) || e.categoria.toLowerCase().includes(this.searchText) || e.titulo.toLowerCase().includes(this.searchText) || e.versao.toLowerCase().includes(this.searchText))
    },
  },
  async mounted() {
    this.standardList = await getStandards();
    this.standardListSearch = this.standardList;
  },
  data() {
    return {
      searchText: "",
      standardList: [],
      standardListSearch: [],

      moment: require("moment"),
    };
  },
};
</script>


<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>