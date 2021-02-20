<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0"
        >Gestão de processos industriais</span
      >
    </q-toolbar-title>

    <br />

    <div class="row q-gutter-sm">
      <q-select
        filled
        dense
        v-model="selectedCompany"
        :options="companies"
        @input="onFilterChange"
        option-value="codigo"
        option-label="nome"
        emit-value
        map-options
        label="Filial"
        style="width: 300px"
      />
      <q-select
        @input="onStatusChange"
        filled
        dense
        option-value="codigo"
        option-label="nome"
        emit-value
        map-options
        v-model="selectedStatus"
        :options="statusOptions"
        label="Status"
        style="width: 150px"
      />

      <q-input
        @input="onFilterChange"
        filled
        v-model="startDate"
        mask="##/##/####"
        label="Data de ínicio"
        dense
        style="max-width: 150px"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              ref="qDateProxy"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date
                @input="onFilterChange"
                lang="pt-BR"
                :locale="dateLocale"
                v-model="startDate"
                mask="DD/MM/YYYY"
              >
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Fechar" color="primary" flat />
                  <q-btn label="OK" color="primary" flat v-close-popup />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>

      <q-input
        @input="onFilterChange"
        filled
        v-model="endDate"
        mask="##/##/####"
        label="Data de fim"
        dense
        style="max-width: 150px"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              ref="qDateProxy"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date
                @input="onFilterChange"
                lang="pt-BR"
                :locale="dateLocale"
                v-model="endDate"
                mask="DD/MM/YYYY"
              >
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Fechar" color="primary" flat />
                  <q-btn label="OK" color="primary" flat v-close-popup />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
      <q-btn
        v-if="admin == true"
        color="teal"
        label="Novo processo"
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
          <th class="text-left">Planejamento iniciado</th>
          <th class="text-left">Data prevista de encerramento</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="industryManagement in industryManagementListFilter"
          :key="industryManagement.codigo + new Date().getTime()"
        >
          <td class="text-left">
            <!-- {{ getStatusDesc(industryManagement.status) }} -->
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
                industryManagement.dataInicioPlanejamento,
                "YYYY-MM-DD"
              ).format("DD/MM/YYYY")
            }}
          </td>
          <td class="text-left">
            {{
              moment(
                industryManagement.dataFimPlanejamento,
                "YYYY-MM-DD"
              ).format("DD/MM/YYYY")
            }}
          </td>
          <td class="text-left q-gutter-xs">
            <q-btn
              v-if="admin == true"
              color="orange"
              label="Nova ocorrência"
              dense
              @click="
                showOccurenceWindow = true;
                ocurrenceIndustryManagement = industryManagement;
              "
            />

            <q-btn
              color="teal"
              label="Gerenciar"
              dense
              @click="navigate(industryManagement)"
            />
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
          <span class="text-bold text-h5">Novo processo</span>
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
              mask="##/##/####"
              v-model="newProcess.dataInicioPlanejamento"
              label="Data de inicio de planejamento"
              class="col-6"
            />

            <q-input
              filled
              mask="##/##/####"
              v-model="newProcess.dataFimPlanejamento"
              label="Data prevista de entrega"
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

    <q-dialog v-model="showOccurenceWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Nova ocorrência</span>
        </q-card-section>

        <q-card-section>
          <div class="row q-col-gutter-xs">
            <q-input
              filled
              v-model="newOccurence.nome"
              label="Nome"
              class="col-12"
            />

            <q-input
              filled
              mask="##/##/#### ##:##:##"
              v-model="newOccurence.dataOcorrencia"
              label="Data da ocorrência"
              class="col-12"
            />

            <q-input
              filled
              type="textarea"
              v-model="newOccurence.descricao"
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
            @click="resetOccurenceForm"
          />
          <q-btn label="Cadastrar" color="primary" @click="validateOccurence" />
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
import Pusher from "pusher-js";

import {
  getIndustryManagementList,
  getStatusDescription,
  getStatus,
  addNewProcess,
  deleteProcess,
  updateProcessStatus,
} from "../services/ProcessoIndustrial";

import { addNewOccurrence } from "../services/Ocorrencias";

import { isMyUserAdmin } from "../services/Usuario";

export default {
  name: "Processos",
  methods: {
    navigate(industryManagement) {
      this.$router.push({
        name: "processo",
        params: { industryManagement },
      });
    },
    async onSelectedStatusChange(industryManagement) {
      console.log(industryManagement);
      let response = await updateProcessStatus(industryManagement);

      if (response != null && response.status == 200) {
        this.$q.notify({
          color: "positive",
          message: "Status do processo atualizado com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        await this.onFilterChange();
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao atualizar o status do processo, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    async removeProcess() {
      let response = await deleteProcess(this.processToExclude);
      this.processToExclude = null;
      this.showProcessRemoveWindow = false;

      if (response != null && response.status == 204) {
        await this.onFilterChange();
        this.$q.notify({
          color: "positive",
          message: "Processo excluido com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao remover o processo, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    resetForm() {
      this.newProcess = {
        nome: "",
        status: 0,
        descricao: "",
        dataInicioPlanejamento: "",
        dataFimPlanejamento: "",
        codigoUsuario: "",
        codigoFilial: "",
        codigoExterno: "",
      };
    },

    resetOccurenceForm() {
      this.newOccurence = {
        nome: "",
        status: 0,
        descricao: "",
        dataOcorrencia: "",
        codigoUsuario: "",
        codigoFilial: "",
        codigoProcessoIndustrial: "",
      };
      this.codigoProcessoIndustrial = null;
    },
    async validateOccurence() {
      if (this.newOccurence.nome.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Nome deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newOccurence.dataOcorrencia.length != 19) {
        this.$q.notify({
          color: "negative",
          message: "Data da ocorrência inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newOccurence.descricao.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Descrição deve ser preenchida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      this.newOccurence.codigoUsuario = JSON.parse(
        localStorage.getItem("USER_DATA")
      ).codigo;
      this.newOccurence.codigoFilial =
        typeof this.selectedCompany === "object" &&
        this.selectedCompany !== null
          ? this.selectedCompany.codigo
          : this.selectedCompany;

      this.newOccurence.codigoProcessoIndustrial = this.ocurrenceIndustryManagement.codigo;

      console.log( this.newOccurence);


      let body = { ...this.newOccurence };

      body.dataOcorrencia = this.moment(
        this.newOccurence.dataOcorrencia,
        "DD/MM/YYYY HH:mm:ss"
      ).format("YYYY-MM-DD HH:mm:ss");

      let response = await addNewOccurrence(body);
      if (response == null || response.status != 201) {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao tentar adicionar uma nova ocorrência, por favor tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });

        return;
      }

      this.resetOccurenceForm();
      this.showOccurenceWindow = false;

      this.$q.notify({
        color: "positive",
        message: "Ocorrência adicionada com sucesso!",
        position: "top",
        timeout: 1000,
      });
      return;
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

      if (this.newProcess.dataInicioPlanejamento.length != 10) {
        this.$q.notify({
          color: "negative",
          message: "Data de início inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newProcess.dataFimPlanejamento.length != 10) {
        this.$q.notify({
          color: "negative",
          message: "Data prevista de encerramento inválida!",
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

      console.log(this.selectedCompany);
      this.newProcess.codigoUsuario = JSON.parse(
        localStorage.getItem("USER_DATA")
      ).codigo;
      this.newProcess.codigoFilial =
        typeof this.selectedCompany === "object" &&
        this.selectedCompany !== null
          ? this.selectedCompany.codigo
          : this.selectedCompany;

      this.newProcess.codigoExterno = "";

      let body = { ...this.newProcess };

      body.dataInicioPlanejamento = this.moment(
        this.newProcess.dataInicioPlanejamento,
        "DD/MM/YYYY"
      ).format("YYYY-MM-DD");

      body.dataFimPlanejamento = this.moment(
        this.newProcess.dataFimPlanejamento,
        "DD/MM/YYYY"
      ).format("YYYY-MM-DD");

      console.log(body);
      let response = await addNewProcess(body);
      if (response == null || response.status != 201) {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao tentar adicionar um novo processo, por favor tente novamente mais tarde!",
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
        message: "Processo adicionado com sucesso!",
        position: "top",
        timeout: 1000,
      });
      return;
    },
    async onFilterChange() {
      this.selectedStatus = this.statusOptions[0];

      this.industryManagementList = await getIndustryManagementList(
        typeof this.selectedCompany === "object" &&
          this.selectedCompany !== null
          ? this.selectedCompany.codigo
          : this.selectedCompany,
        this.moment(this.startDate, "DD/MM/YYYY").format("YYYY-MM-DD"),
        this.moment(this.endDate, "DD/MM/YYYY").format("YYYY-MM-DD")
      );
      this.industryManagementListFilter = this.industryManagementList;

      this.setupPusher(
        typeof this.selectedCompany === "object" &&
          this.selectedCompany !== null
          ? this.selectedCompany.codigo
          : this.selectedCompany
      );
    },
    onStatusChange() {
      if (this.selectedStatus == -1) {
        this.industryManagementListFilter = this.industryManagementList;
        return;
      }

      this.industryManagementListFilter = this.industryManagementList.filter(
        (e) => e.status == this.selectedStatus
      );
    },
    getStatusDesc(code) {
      return getStatusDescription(code);
    },
    setupPusher(codigo) {
      console.log("connected to " + codigo);
      try {
        this.pusher.disconnect();
      } catch (err) {}

      this.pusher = new Pusher("***REMOVED***", {
        cluster: "us2",
      });

      this.channel = this.pusher.subscribe(
        "sigo-industry-management-" + codigo
      );

      /*
      {"processoIndustrial":{"codigo":2,"nome":"aaaaaaaaaaaaaaa","status":0,"descricao":"aaaaaaaaaaaaaaaaaaa","dataInicioPlanejamento":{"year":2021,"month":1,"day":21},"dataFimPlanejamento":{"year":2021,"month":1,"day":21},"codigoUsuario":1,"codigoFilial":2,"codigoExterno":"9b40aa8e-2a0d-47e9-8758-eeea62662b28"},"mode":"INSERT"}
      */
      this.channel.bind("INSERT", (data) => {
        console.log(data);
        data.dataInicioPlanejamento = this.moment(
          new Date(
            data.dataInicioPlanejamento.year,
            data.dataInicioPlanejamento.month,
            data.dataFimPlanejamento.day
          )
        ).format("YYYY-MM-DD");
        data.dataFimPlanejamento = this.moment(
          new Date(
            data.dataFimPlanejamento.year,
            data.dataFimPlanejamento.month,
            data.dataFimPlanejamento.day
          )
        ).format("YYYY-MM-DD");

        this.industryManagementListFilter.push(data);
        //this.industryManagementListFilter.push(data);
        console.log(JSON.stringify(data));
      });
      /*
      {"processoIndustrial":{"codigo":1,"codigoFilial":2},"mode":"DELETE"}
      */

      this.channel.bind("DELETE", (data) => {
        console.log(data);

        let arr = [];

        for (let item of this.industryManagementListFilter) {
          if (item.codigo != data.codigo) {
            arr.push(item);
          }
        }

        this.industryManagementListFilter = arr;
      });

      /*
      {"processoIndustrial":{"codigo":1,"nome":"bbbbbbbbbbbbbb","status":1,"descricao":"aadssadsad","dataInicioPlanejamento":{"year":2021,"month":1,"day":21},"dataFimPlanejamento":{"year":2021,"month":1,"day":21},"codigoUsuario":1,"codigoFilial":2,"codigoExterno":"366b3189-e848-46ab-8a07-1bd4d05187e2","items":[]},"mode":"UPDATE"}
      */
      this.channel.bind("UPDATE", (data) => {
        console.log(data);

        for (let item of this.industryManagementListFilter) {
          let indexOf = this.industryManagementListFilter.indexOf(item);
          console.log(indexOf);
          if (item.codigo == data.codigo) {
            data.dataInicioPlanejamento = this.moment(
              new Date(
                data.dataInicioPlanejamento.year,
                data.dataInicioPlanejamento.month,
                data.dataFimPlanejamento.day
              )
            ).format("YYYY-MM-DD");
            data.dataFimPlanejamento = this.moment(
              new Date(
                data.dataFimPlanejamento.year,
                data.dataFimPlanejamento.month,
                data.dataFimPlanejamento.day
              )
            ).format("YYYY-MM-DD");
            this.industryManagementListFilter.splice(indexOf, 1, data);
          }
        }
      });
    },
  },
  async mounted() {
    this.admin = isMyUserAdmin();
    let userData = JSON.parse(localStorage.getItem("USER_DATA"));
    this.companies = userData.filiais;
    this.selectedCompany = this.companies[0];

    let date = new Date();
    this.startDate = this.moment(
      new Date(date.getFullYear(), date.getMonth(), 1)
    ).format("DD/MM/YYYY");
    this.endDate = this.moment(
      new Date(date.getFullYear(), date.getMonth() + 1, 0)
    ).format("DD/MM/YYYY");

    this.statusOptions = getStatus();
    this.selectedStatus = this.statusOptions[0];

    this.industryManagementList = await getIndustryManagementList(
      this.selectedCompany.codigo,
      this.moment(this.startDate, "DD/MM/YYYY").format("YYYY-MM-DD"),
      this.moment(this.endDate, "DD/MM/YYYY").format("YYYY-MM-DD")
    );
    this.industryManagementListFilter = this.industryManagementList;

    this.setupPusher(this.selectedCompany.codigo);
  },
  beforeDestroy() {
    this.pusher.disconnect();
  },
  data() {
    return {
      pusher: null,
      channel: null,
      admin: false,
      newProcess: {
        nome: "",
        status: 0,
        descricao: "",
        dataInicioPlanejamento: "",
        dataFimPlanejamento: "",
        codigoUsuario: "",
        codigoFilial: "",
        codigoExterno: "",
      },
      newOccurence: {
        nome: "",
        status: 0,
        descricao: "",
        dataOcorrencia: "",
        codigoUsuario: "",
        codigoFilial: "",
        codigoProcessoIndustrial: "",
      },
      showOccurenceWindow: false,
      ocurrenceIndustryManagement: null,
      processToExclude: null,
      showProcessWindow: false,
      showProcessRemoveWindow: false,
      industryManagementList: [],
      industryManagementListFilter: [],

      companies: [],
      selectedCompany: null,
      startDate: null,
      endDate: null,
      dateLocale: {
        /* starting with Sunday */
        days: "Domingo_Segunda_Terca_Quarta_Quinta_Sexta_Sábado".split("_"),
        daysShort: "Dom_Seg_Ter_Qua_Qui_Sex_Sab".split("_"),
        months: "Janeiro_Fevereiro_Marco_Abril_Maio_Junho_Julho_Agosto_Setembro_Outubro_Novembro_Dezembro".split(
          "_"
        ),
        monthsShort: "Jan_Fev_Mar_Abr_Mai_Jun_Jul_Ago_Set_Out_Nov_Dez".split(
          "_"
        ),
        firstDayOfWeek: 1,
      },
      moment: require("moment"),
      selectedStatus: null,
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