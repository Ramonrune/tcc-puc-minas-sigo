<template>
  <div class="q-pa-md">
    <q-toolbar style="padding-left: 0px">
      <q-toolbar-title shrink>
        <span
          class="q-ml-sm"
          style="font-weight: 500; margin-left: 0px"
          v-if="process != null"
          >{{ process.nome }}</span
        >
      </q-toolbar-title>
      <q-space />
    </q-toolbar>

    <span
      class="q-ml-sm"
      style="margin: 0; font-weight: 500"
      v-if="process != null"
      >Status: {{ getStatusDesc(process.status) }}.</span
    >
    &nbsp;

    <span
      class="q-ml-sm"
      style="margin: 0; font-weight: 500"
      v-if="process != null"
      >De {{ moment(process.dataInicioPlanejamento).format("DD/MM/YYYY") }} até
      {{ moment(process.dataFimPlanejamento).format("DD/MM/YYYY") }}</span
    >
    <br />

    <br />

    <div class="row q-gutter-sm">
      <q-btn
        v-if="admin == true"
        color="teal"
        label="Novo item de processo"
        @click="showProcessWindow = true"
        dense
      />
    </div>
    <br />
    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Status</th>
          <th class="text-left">Indicativo</th>
          <th class="text-left">Descrição</th>
          <th class="text-left">Data de início</th>
          <th class="text-left">H. planejadas</th>
          <th class="text-left">H. realizadas</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="industryManagement in industryManagementList"
          :key="industryManagement.codigo + new Date().getTime()"
        >
          <td class="text-left">
            <q-select
              :disable="admin == false"
              @input="onSelectedStatusChange(industryManagement)"
              filled
              dense
              option-value="codigo"
              option-label="nome"
              emit-value
              map-options
              v-model="industryManagement.status"
              :options="statusOptions.slice(1, statusOptions.length)"
              style="width: 150px"
            />
          </td>
          <td class="text-left">
            <q-icon
              name="info"
              color="grey"
              size="25px"
              v-if="industryManagement.status == 0"
            />

            <q-icon
              name="info"
              color="blue"
              size="25px"
              v-if="industryManagement.status == 2"
            />

            <q-icon
              name="av_timer"
              color="blue"
              size="25px"
              v-if="industryManagement.status == 1"
            />

            <q-icon
              name="check_box"
              color="green"
              size="25px"
              v-if="industryManagement.status == 4"
            />

            <q-icon
              name="error"
              color="red"
              size="25px"
              v-if="industryManagement.status == 3"
            />

            <q-icon
              name="cancel"
              color="red"
              size="25px"
              v-if="industryManagement.status == 5"
            />
          </td>
          <td class="text-left">{{ industryManagement.nome }}</td>
          <td class="text-left">
            {{
              moment(
                industryManagement.dataInicio,
                "YYYY-MM-DD HH:mm:ss"
              ).format("DD/MM/YYYY HH:mm:ss")
            }}
          </td>
          <td class="text-left">
            {{
              moment
                .utc(industryManagement.qtdHorasPrevista * 60 * 1000)
                .format("HH:mm")
            }}
          </td>
          <td class="text-left">
            <q-input
              style="max-width: 125px"
              @input="
                (val) => {
                  inputHour(val, industryManagement);
                }
              "
              :ref="industryManagement.codigo"
              filled
              mask="##:##"
              :value="
                moment
                  .utc(industryManagement.qtdHorasRealizada * 60 * 1000)
                  .format('HH:mm')
              "
              dense
            >
              <template slot:append>
                <q-btn
                  flat
                  dense
                  color="positive"
                  icon="check_circle"
                  @click="changeHour(industryManagement)"
                />
              </template>
            </q-input>
          </td>

          <td class="text-left q-gutter-xs">
            <q-btn
              v-if="admin == true"
              color="red"
              label="Excluir"
              @click="
                processToExclude = industryManagement;
                showProcessRemoveWindow = true;
              "
              dense
            />
          </td>
        </tr>
      </tbody>
    </q-markup-table>

    <q-dialog v-model="showProcessWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Novo item de processo</span>
        </q-card-section>

        <q-card-section>
          <div class="row q-col-gutter-xs">
            <q-input
              filled
              v-model="newProcess.nome"
              label="Nome"
              class="col-12"
            />

            <q-input
              filled
              mask="##/##/#### ##:##:##"
              v-model="newProcess.dataInicio"
              label="Data de inicio"
              class="col-6"
            />

            <q-input
              filled
              mask="##:##"
              v-model="newProcess.qtdHorasPrevista"
              label="Quantidade de horas estimadas"
              class="col-6"
            />

            <q-input
              filled
              type="textarea"
              v-model="newProcess.descricao"
              label="Descrição"
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
          <q-btn label="Cadastrar" color="primary" @click="validate" />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-dialog v-model="showProcessRemoveWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Excluir processo</span>
        </q-card-section>

        <q-card-section v-if="processToExclude != null">
          <div>
            Tem certeza que deseja excluir o processo
            {{ processToExclude.nome }} ?
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Não"
            color="red"
            v-close-popup
            @click="processToExclude = null"
          />
          <q-btn label="Sim" color="primary" @click="removeProcess" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import {
  getStatusDescription,
  getStatus,
} from "../services/ProcessoIndustrial";

import {
  getIndustryManagementItemsList,
  addNewProcessItem,
  updateItemProcessStatus,
  updateItemProcessHour,
  deleteProcessItem,
} from "../services/ProcessoIndustrialItem";

import { isMyUserAdmin } from "../services/Usuario";

export default {
  name: "Processo",
  methods: {
    async changeHour(industryManagement){
      let ref= this.$refs[industryManagement.codigo];
      let value = ref[0].$el.getElementsByTagName('input')[0].value;


      let hour = this.moment
        .duration(value, "HH:mm")
        .asMinutes();

      let body = {
        codigo: industryManagement.codigo,
        hour: hour
      }

      let response = await updateItemProcessHour(body);

      if (response != null && response.status == 200) {

        industryManagement.qtdHorasRealizada = hour;
        
        this.$q.notify({
          color: "positive",
          message: "Hora realizada do item do processo atualizado com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        await this.onFilterChange();
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao atualizar as horas realizadas do item do processo, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }

      

      
    },
    inputHour(val, industryManagement) {
    },
    async onSelectedStatusChange(industryManagement) {
      console.log(industryManagement);
      let response = await updateItemProcessStatus(industryManagement);

      if (response != null && response.status == 200) {
        this.$q.notify({
          color: "positive",
          message: "Status do item do processo atualizado com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        await this.onFilterChange();
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao atualizar o status do item do processo, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    async removeProcess() {
      let response = await deleteProcessItem(this.processToExclude);
      this.processToExclude = null;
      this.showProcessRemoveWindow = false;

      if (response != null && response.status == 204) {
        await this.onFilterChange();
        this.$q.notify({
          color: "positive",
          message: "Item do processo excluido com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao remover o item do processo, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    resetForm() {
      this.newProcess = {
        codigoProcessoIndustrial: "",
        nome: "",
        status: 0,
        descricao: "",
        dataInicio: "",
        qtdHorasPrevista: "",
        qtdHorasRealizada: 0,
        codigoExternoProcessoIndustrialItem: "",
      };
    },
    async validate() {
      if (this.newProcess.nome.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Nome deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newProcess.dataInicio.length != 19) {
        this.$q.notify({
          color: "negative",
          message: "Data de início inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newProcess.qtdHorasPrevista.length != 5) {
        this.$q.notify({
          color: "negative",
          message: "Quantidade de horas inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newProcess.descricao.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Descrição deve ser preenchida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      let body = { ...this.newProcess };

      body.dataInicio = this.moment(
        this.newProcess.dataInicio,
        "DD/MM/YYYY HH:mm:ss"
      ).format("YYYY-MM-DD HH:mm:ss");

      body.qtdHorasPrevista = this.moment
        .duration(this.newProcess.qtdHorasPrevista, "HH:mm")
        .asMinutes();

      body.codigoProcessoIndustrial = this.process.codigo;

      console.log(body);

      let response = await addNewProcessItem(body);
      if (response == null || response.status != 201) {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao tentar adicionar um novo item no processo, por favor tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });

        return;
      }

      this.resetForm();
      await this.onFilterChange();
      this.showProcessWindow = false;

      this.$q.notify({
        color: "positive",
        message: "Item do processo adicionado com sucesso!",
        position: "top",
        timeout: 1000,
      });
      return;
    },

    async onFilterChange() {
      let response = await getIndustryManagementItemsList(this.process.codigo);
      this.industryManagementList = response;
    },

    getStatusDesc(code) {
      return getStatusDescription(code);
    },
  },
  async mounted() {
    this.admin = isMyUserAdmin();
    this.companies = JSON.parse(localStorage.getItem("USER_DATA")).filiais;

    let industryManagementRouteParam = /* {
      codigo: 1,
      nome: "bbbbbbbbbbbbbb",
      status: 1,
      descricao: "aadssadsad",
      dataInicioPlanejamento: "2021-01-21",
      dataFimPlanejamento: "2021-01-21",
      codigoUsuario: 1,
      codigoFilial: 1,
      codigoExterno: "366b3189-e848-46ab-8a07-1bd4d05187e2",
    }; */ this
      .$route.params.industryManagement;

    let success = false;

    for (let company of this.companies) {
      if (company.codigo == industryManagementRouteParam.codigoFilial) {
        success = true;
      }
    }

    if (success) {
      let newProcess = industryManagementRouteParam;
      this.process = newProcess;

      let response = await getIndustryManagementItemsList(newProcess.codigo);

      this.industryManagementList = response;
      this.statusOptions = getStatus();

      this.newProcess.dataInicio = this.moment(new Date()).format(
        "DD/MM/YYYY HH:mm:ss"
      );
    }
  },
  data() {
    return {
      admin: false,
      process: null,
      newProcess: {
        codigoProcessoIndustrial: "",
        nome: "",
        status: 0,
        descricao: "",
        dataInicio: "",
        qtdHorasPrevista: "08:00",
        qtdHorasRealizada: 0,
        codigoExternoProcessoIndustrialItem: "",
      },
      processToExclude: null,
      showProcessWindow: false,
      showProcessRemoveWindow: false,
      industryManagementList: [],

      companies: [],
      moment: require("moment"),
      statusOptions: [],
    };
  },
};
</script>

<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>