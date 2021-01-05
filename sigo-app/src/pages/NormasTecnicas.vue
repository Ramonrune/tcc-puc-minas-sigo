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
      v-model="text"
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
          <th class="text-left">Início de validade</th>
          <th class="text-left">Categoria</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="text-left">ABNT</td>
          <td class="text-left">ISO 9001</td>
          <td class="text-left">2020</td>
          <td class="text-left">1</td>
          <td class="text-left">20/01/2020</td>
          <td class="text-left">QUALIDADE</td>
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
export default {
  name: "NormasTecnicas",
};
</script>


<style scoped>
.q-table__card{
      box-shadow: none !important;
}
</style>