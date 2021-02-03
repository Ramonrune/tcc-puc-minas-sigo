<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0"
        >Consultorias</span
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
        filled
        dense
        v-model="selectedConsultancyCompany"
        :options="consultancyCompanies"
        @input="onFilterChange"
        option-value="codigo"
        option-label="nome"
        emit-value
        map-options
        label="Empresas de consultoria"
        style="width: 300px"
      />

      <q-input
        @input="onFilterChange"
        filled
        v-model="startDate"
        mask="##/##/####"
        label="Data de contratação inicial"
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
        label="Data de contratação final"
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
        color="primary"
        label="Nova consultoria"
        @click="showConsultancyWindow = true"
        :disable="consultancyCompanies.length == 0"
        dense
      />
    </div>
    <br />
    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Setor</th>
          <th class="text-left">Motivo</th>
          <th class="text-left">Data de contratação</th>
          <th class="text-left">Data de início</th>
          <th class="text-left">Data de fim</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="consultancy in consultancyList"
          :key="consultancy.codigo + new Date().getTime()"
        >
          <td class="text-left">{{ consultancy.setor }}</td>
          <td class="text-left">
            <div class="row">
              <div
                style="
                  width: 200px;
                  overflow: hidden;
                  white-space: nowrap;
                  text-overflow: ellipsis;
                "
              >
                {{ consultancy.motivo }}
              </div>
            </div>
          </td>

          <td class="text-left">
            {{
              moment(consultancy.dataContratacao, "YYYY-MM-DD").format(
                "DD/MM/YYYY"
              )
            }}
          </td>
          <td class="text-left">
            {{
              moment(consultancy.dataInicio, "YYYY-MM-DD").format("DD/MM/YYYY")
            }}
          </td>
          <td class="text-left">
            {{ moment(consultancy.dataFim, "YYYY-MM-DD").format("DD/MM/YYYY") }}
          </td>
          <td class="text-left q-gutter-xs">
            <q-btn
              color="teal"
              label="Gerenciar"
              dense
              @click="
                consultancyDetail = consultancy;
                showConsultancyDetail = true;
              "
            />
            <q-btn
              v-if="admin == true"
              color="red"
              label="Excluir"
              @click="
                consultancyToExclude = consultancy;
                showConsultancyRemoveWindow = true;
              "
              dense
            />
          </td>
        </tr>
      </tbody>
    </q-markup-table>

    <q-dialog
      v-model="showConsultancyDetail"
      persistent
      v-if="consultancyDetail != null"
    >
      <q-card style="width: 700px; max-width: 80vw;">
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Detalhes da consultoria</span>
           <q-space />
          <q-btn icon="close" flat round dense v-close-popup @click="showConsultancyDetail = false; consultancyDetail = null;"/>
        </q-card-section>

        <q-card-section>
          <q-tabs
            v-model="tab"
            dense
            class="text-grey"
            active-color="primary"
            indicator-color="primary"
            align="justify"
            narrow-indicator
          >
            <q-tab name="dados" label="Dados" />
            <q-tab name="anexos_enviados" label="Anexos enviados" />
            <q-tab name="resultado" label="Resultado" />
          </q-tabs>

          <q-separator />

          <q-tab-panels v-model="tab" animated>
            <q-tab-panel name="dados" class="row">
              <div class="col-6">
                <div class="text-h6" style="font-size: 16px">Filial</div>
                {{ selectedCompany.nome }}
                <br> <br>
                <div class="text-h6" style="font-size: 16px">
                  Empresa de consultoria
                </div>
                {{ selectedConsultancyCompany.nome }}
  <br> <br>
                <div class="text-h6" style="font-size: 16px">Setor</div>
                {{ consultancyDetail.setor }}
  <br> <br>
                <div class="text-h6" style="font-size: 16px">
                  Data da contratação
                </div>
                {{
                  moment(
                    consultancyDetail.dataContratacao,
                    "YYYY-MM-DD"
                  ).format("DD/MM/YYYY")
                }}
  <br> <br>
                <div class="text-h6" style="font-size: 16px">
                  Data de início
                </div>
                {{
                  moment(consultancyDetail.dataInicio, "YYYY-MM-DD").format(
                    "DD/MM/YYYY"
                  )
                }}
  <br> <br>
                <div class="text-h6" style="font-size: 16px">Data de fim</div>
                {{
                  moment(consultancyDetail.dataFim, "YYYY-MM-DD").format(
                    "DD/MM/YYYY"
                  )
                }}

              </div>
              <div class="col-6">
                <div class="text-h6" style="font-size: 16px">Motivo</div>
                {{ consultancyDetail.motivo }}
              </div>
            </q-tab-panel>

            <q-tab-panel name="anexos_enviados">
              <div class="text-h6">Alarms</div>
              Lorem ipsum dolor sit amet consectetur adipisicing elit.
            </q-tab-panel>

            <q-tab-panel name="resultado">
              <div class="text-h6">Movies</div>
              Lorem ipsum dolor sit amet consectetur adipisicing elit.
            </q-tab-panel>
          </q-tab-panels>
        </q-card-section>
      </q-card></q-dialog
    >

    <q-dialog v-model="showConsultancyWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Nova consultoria</span>
        </q-card-section>

        <q-card-section>
          <div class="row q-col-gutter-xs">
            <q-input
              filled
              v-model="newConsultancy.setor"
              label="Setor"
              class="col-12"
            />

            <q-input
              filled
              mask="##/##/####"
              v-model="newConsultancy.dataContratacao"
              label="Data de contratação"
              class="col-4"
            />

            <q-input
              filled
              mask="##/##/####"
              v-model="newConsultancy.dataInicio"
              label="Data de inicio"
              class="col-4"
            />

            <q-input
              filled
              mask="##/##/####"
              v-model="newConsultancy.dataFim"
              label="Data de fim"
              class="col-4"
            />

            <q-input
              filled
              type="textarea"
              v-model="newConsultancy.motivo"
              label="Motivo"
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

    <q-dialog v-model="showConsultancyRemoveWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Excluir processo</span>
        </q-card-section>

        <q-card-section v-if="consultancyToExclude != null">
          <div>
            Tem certeza que deseja excluir a consultoria
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
          <q-btn label="Sim" color="primary" @click="removeConsultancy" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import {
  addNewConsultancy,
  getConsultancy,
  deleteConsultancy,
} from "../services/Consultoria";

import { getCompaniesConsultancy } from "../services/EmpresasConsultoria";

import { isMyUserAdmin } from "../services/Usuario";

export default {
  name: "Processos",
  methods: {
    async removeConsultancy() {
      let response = await deleteConsultancy(this.consultancyToExclude.codigo);
      this.consultancyToExclude = null;
      this.showConsultancyRemoveWindow = false;

      if (response != null && response.status == 204) {
        await this.onFilterChange();
        this.$q.notify({
          color: "positive",
          message: "Consultoria excluida com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao remover a consultoria, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    resetForm() {
      this.newConsultancy = {
        codigoEmpresaConsultoria: "",
        setor: "",
        motivo: "",
        dataContratacao: "",
        dataInicio: "",
        dataFim: "",
        codigoFilial: "",
      };
    },
    async validate() {
      if (this.newConsultancy.setor.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Setor deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newConsultancy.dataContratacao.length != 10) {
        this.$q.notify({
          color: "negative",
          message: "Data de contratação inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newConsultancy.dataInicio.length != 10) {
        this.$q.notify({
          color: "negative",
          message: "Data de início inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newConsultancy.dataFim.length != 10) {
        this.$q.notify({
          color: "negative",
          message: "Data de fim inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newConsultancy.motivo.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Motivo deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      this.newConsultancy.codigoFilial =
        typeof this.selectedCompany === "object" &&
        this.selectedCompany !== null
          ? this.selectedCompany.codigo
          : this.selectedCompany;

      this.newConsultancy.codigoEmpresaConsultoria =
        typeof this.selectedConsultancyCompany === "object" &&
        this.selectedConsultancyCompany !== null
          ? this.selectedConsultancyCompany.codigo
          : this.selectedConsultancyCompany;

      let body = { ...this.newConsultancy };

      body.dataContratacao = this.moment(
        this.newConsultancy.dataInicio,
        "DD/MM/YYYY"
      ).format("YYYY-MM-DD");

      body.dataInicio = this.moment(
        this.newConsultancy.dataInicio,
        "DD/MM/YYYY"
      ).format("YYYY-MM-DD");

      body.dataFim = this.moment(
        this.newConsultancy.dataFim,
        "DD/MM/YYYY"
      ).format("YYYY-MM-DD");

      console.log(body);
      let response = await addNewConsultancy(body);
      if (response == null || response.status != 201) {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao tentar adicionar uma nova consultoria, por favor tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });

        return;
      }

      this.resetForm();
      await this.onFilterChange();
      this.showConsultancyWindow = false;

      this.$q.notify({
        color: "positive",
        message: "Consultoria adicionada com sucesso!",
        position: "top",
        timeout: 1000,
      });
      return;
    },
    async onFilterChange() {
      this.consultancyList = await getConsultancy(
        typeof this.selectedConsultancyCompany === "object" &&
          this.selectedConsultancyCompany !== null
          ? this.selectedConsultancyCompany.codigo
          : this.selectedConsultancyCompany,
        typeof this.selectedCompany === "object" &&
          this.selectedCompany !== null
          ? this.selectedCompany.codigo
          : this.selectedCompany,
        this.moment(this.startDate, "DD/MM/YYYY").format("YYYY-MM-DD"),
        this.moment(this.endDate, "DD/MM/YYYY").format("YYYY-MM-DD")
      );
    },
  },
  async mounted() {
    this.admin = isMyUserAdmin();
    let userData = JSON.parse(localStorage.getItem("USER_DATA"));
    this.companies = userData.filiais;
    this.selectedCompany = this.companies[0];

    this.consultancyCompanies = await getCompaniesConsultancy();
    this.selectedConsultancyCompany = this.consultancyCompanies[0];

    let date = new Date();
    this.startDate = this.moment(new Date(date.getFullYear(), 0, 1)).format(
      "DD/MM/YYYY"
    );
    this.endDate = this.moment(new Date(date.getFullYear(), 6, 0)).format(
      "DD/MM/YYYY"
    );

    this.consultancyList = await getConsultancy(
      this.selectedConsultancyCompany.codigo,
      this.selectedCompany.codigo,
      this.moment(this.startDate, "DD/MM/YYYY").format("YYYY-MM-DD"),
      this.moment(this.endDate, "DD/MM/YYYY").format("YYYY-MM-DD")
    );
  },
  data() {
    return {
      admin: false,
      newConsultancy: {
        codigoEmpresaConsultoria: "",
        setor: "",
        motivo: "",
        dataContratacao: "",
        dataInicio: "",
        dataFim: "",
        codigoFilial: "",
      },
      tab: "dados",
      consultancyToExclude: null,
      showConsultancyWindow: false,
      showConsultancyRemoveWindow: false,
      consultancyList: [],
      companies: [],
      selectedCompany: null,
      consultancyCompanies: [],
      selectedConsultancyCompany: null,
      startDate: null,
      endDate: null,
      consultancyDetail: null,
      showConsultancyDetail: false,
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
    };
  },
};
</script>

<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>