<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0">Processos</span>
    </q-toolbar-title>

    <br />

    <div class="row q-gutter-sm">
      <q-select
        filled
        dense
        v-model="model"
        :options="options"
        label="Status"
        style="bottom: 10px; position: relative; width: 200px"
      />

      <q-input
        filled
        v-model="date"
        mask="##/##/####"
        hint="Data de ínicio"
        :rules="['date']"
        dense
        style="max-width: 300px"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              ref="qDateProxy"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date v-model="date" mask="DD/MM/YYYY">
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Close" color="primary" flat />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>

      <q-input
        filled
        v-model="date"
        mask="##/##/####"
        hint="Data de fim"
        :rules="['date']"
        dense
        style="max-width: 300px"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              ref="qDateProxy"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date v-model="date" mask="DD/MM/YYYY">
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Close" color="primary" flat />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
    </div>
    <br />

    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Status</th>
          <th class="text-left">Indicativo</th>
          <th class="text-left">Descrição</th>
          <th class="text-left">Planejamento iniciado</th>
          <th class="text-left">Criado por</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="text-left">Aguardando</td>
          <td class="text-left">
            <q-icon name="info" color="green" size="25px" />
          </td>
          <td class="text-left">Material Requirements Planning (MRP)</td>
          <td class="text-left">04/01/2021 16:00:00</td>
          <td class="text-left">Ramon Lacava</td>
          <td class="text-left q-gutter-xs">
            <q-btn color="primary" label="Visualizar" dense />

            <q-btn-dropdown color="teal" label="Ações" dense>
              <q-list>
                <q-item clickable v-close-popup @click="onItemClick">
                  <q-item-section>
                    <q-item-label>Marcar como em andamento</q-item-label>
                  </q-item-section>
                </q-item>

                <q-item clickable v-close-popup @click="onItemClick">
                  <q-item-section>
                    <q-item-label>Marcar como concluido</q-item-label>
                  </q-item-section>
                </q-item>

                <q-item clickable v-close-popup @click="onItemClick">
                  <q-item-section>
                    <q-item-label>Marcar como cancelado</q-item-label>
                  </q-item-section>
                </q-item>

                <q-item clickable v-close-popup @click="onItemClick">
                  <q-item-section>
                    <q-item-label>Marcar como falha</q-item-label>
                  </q-item-section>
                </q-item>

                <q-separator />
                <q-item clickable v-close-popup @click="onItemClick">
                  <q-item-section>
                    <q-item-label>Editar</q-item-label>
                  </q-item-section>
                </q-item>

                <q-item clickable v-close-popup @click="onItemClick">
                  <q-item-section>
                    <q-item-label>Excluir</q-item-label>
                  </q-item-section>
                </q-item>
              </q-list>
            </q-btn-dropdown>
          </td>
        </tr>
      </tbody>
    </q-markup-table>
  </div>
</template>

<script>
export default {
  name: "Processos",
  data() {
    return {
      tab: "mails",
      date: null,
      model: "Todos",
      options: [
        "Todos",
        "Aguardando",
        "Em andamento",
        "Concluido",
        "Falha",
        "Cancelado",
      ],
    };
  },
};
</script>

<style scoped>
.q-table__card{
      box-shadow: none !important;
}
</style>