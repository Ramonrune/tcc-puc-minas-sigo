<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0">Usuários</span>
    </q-toolbar-title>
    <q-btn color="primary" label="Novo usuário" dense class="float-right" />

    <br />
    <br />

    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Email</th>
          <th class="text-left">Nome</th>
          <th class="text-left">Status</th>
          <th class="text-left">Regras de acesso</th>
          <th class="text-left">Criado em</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="text-left">ramonrune@gmail.com</td>
          <td class="text-left">Ramon Lacava Gutierrez Gonçales</td>
          <td class="text-left">
            <q-badge outline color="green" label="Ativo" />
          </td>
          <td class="text-left">
            <q-badge color="primary">ROLE_ADMIN</q-badge>
            <br />
            <q-badge color="primary">ROLE_ADMIN</q-badge>
          </td>
          <td class="text-left">20/01/2020</td>
          <td class="text-left q-gutter-xs">
            <q-btn color="primary" label="Visualizar" dense />
            <q-btn color="teal" label="Editar" dense />
            <q-btn color="red" label="Excluir" dense />
          </td>
        </tr>
      </tbody>
    </q-markup-table>
  </div>
</template>
<script>
export default {
  name: "Usuarios",
};
</script>


<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>