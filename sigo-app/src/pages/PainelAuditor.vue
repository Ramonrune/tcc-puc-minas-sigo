<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0"
        >Painel de consultoria</span
      >
    </q-toolbar-title>

    <br />

    <div class="row q-gutter-sm">
      <q-card style="width: 700px; max-width: 80vw">
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Detalhes da consultoria</span>
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
            <q-tab name="anexos_recebidos" label="Anexos recebidos" />
            <q-tab name="anexos_enviados" label="Anexos enviados" />
          </q-tabs>

          <q-separator />

          <q-tab-panels v-model="tab" animated v-if="consultancy != null">
            <q-tab-panel name="dados" class="row">
              <div class="col-6">
                <div class="text-h6" style="font-size: 16px">Setor</div>
                {{ consultancy.setor }}
                <br />
                <br />
                <div class="text-h6" style="font-size: 16px">
                  Data da contratação
                </div>
                {{
                  moment(consultancy.dataContratacao, "YYYY-MM-DD").format(
                    "DD/MM/YYYY"
                  )
                }}
                <br />
                <br />
                <div class="text-h6" style="font-size: 16px">
                  Data de início
                </div>
                {{
                  moment(consultancy.dataInicio, "YYYY-MM-DD").format(
                    "DD/MM/YYYY"
                  )
                }}
                <br />
                <br />
                <div class="text-h6" style="font-size: 16px">Data de fim</div>
                {{
                  moment(consultancy.dataFim, "YYYY-MM-DD").format("DD/MM/YYYY")
                }}
              </div>
              <div class="col-6">
                <div class="text-h6" style="font-size: 16px">Motivo</div>
                {{ consultancy.motivo }}
              </div>
            </q-tab-panel>

            <q-tab-panel name="anexos_recebidos">
              <div class="row">
                <q-card
                  v-for="attachment in attachments.filter((e) => e.origem == 1)"
                  :key="attachment.codigo + new Date().getTime()"
                  class="col-3"
                >
                  <br />
                  <div class="col">
                    <img
                      src="~assets/powerpoint.png"
                      v-if="
                        attachment.tipo ==
                        'application/vnd.openxmlformats-officedocument.presentationml.presentation'
                      "
                      style="width: 100px; margin-left: 20px"
                    />
                    <img
                      src="~assets/word.png"
                      v-if="
                        attachment.tipo ==
                        'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
                      "
                      style="width: 100px; margin-left: 20px"
                    />
                    <img
                      src="~assets/excel.png"
                      v-if="
                        attachment.tipo ==
                        'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                      "
                      style="width: 100px; margin-left: 20px"
                    />
                    <img
                      src="~assets/pdf.png"
                      v-if="attachment.tipo == 'application/pdf'"
                      style="width: 100px; margin-left: 20px"
                    />
                  </div>
                  <div style="position: absolute; top: 0; right: 0">
                    <q-btn color="grey-7" round flat icon="more_vert">
                      <q-menu cover auto-close>
                        <q-list>
                          <q-item
                            clickable
                            @click="downloadAttachment(attachment)"
                          >
                            <q-item-section>Download</q-item-section>
                          </q-item>
                          <q-item
                            clickable
                            @click="removeAttachment(attachment)"
                          >
                            <q-item-section>Excluir</q-item-section>
                          </q-item>
                        </q-list>
                      </q-menu>
                    </q-btn>
                  </div>

                  <q-card-section>
                    <div style="font-size: 14px">
                      {{ attachment.titulo }}
                    </div>
                    <div style="font-size: 12px">
                      {{
                        moment(attachment.data, "YYYY-MM-DD").format(
                          "DD/MM/YYYY"
                        )
                      }}
                    </div>
                  </q-card-section>
                </q-card>
              </div>
            </q-tab-panel>

            <q-tab-panel name="anexos_enviados">
              <div class="text-h6">Anexos enviados</div>
              <div class="row q-col-gutter-sm">
                <q-input
                  style="position: relative; bottom: 10px"
                  filled
                  v-model="newAttachment.titulo"
                  label="Nome anexo"
                  class="col-6"
                />
                <q-file
                  filled
                  bottom-slots
                  v-model="file"
                  label="Arquivo"
                  counter
                  class="col-6"
                  :filter="checkFileType"
                  @rejected="onRejected"
                >
                  <template v-slot:prepend>
                    <q-icon name="cloud_upload" />
                  </template>

                  <template v-slot:hint>Arquivo</template>
                </q-file>
              </div>
              <q-btn
                label="Adicionar anexo"
                color="primary"
                style="height: 40px"
                @click="validateAttachment"
                :loading="loadingAttachment"
              />
              <br /><br />

              <div class="row">
                <q-card
                  v-for="attachment in attachments.filter((e) => e.origem == 2)"
                  :key="attachment.codigo + new Date().getTime()"
                  class="col-3"
                >
                  <br />
                  <div class="col">
                    <img
                      src="~assets/powerpoint.png"
                      v-if="
                        attachment.tipo ==
                        'application/vnd.openxmlformats-officedocument.presentationml.presentation'
                      "
                      style="width: 100px; margin-left: 20px"
                    />
                    <img
                      src="~assets/word.png"
                      v-if="
                        attachment.tipo ==
                        'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
                      "
                      style="width: 100px; margin-left: 20px"
                    />
                    <img
                      src="~assets/excel.png"
                      v-if="
                        attachment.tipo ==
                        'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                      "
                      style="width: 100px; margin-left: 20px"
                    />
                    <img
                      src="~assets/pdf.png"
                      v-if="attachment.tipo == 'application/pdf'"
                      style="width: 100px; margin-left: 20px"
                    />
                  </div>
                  <div style="position: absolute; top: 0; right: 0">
                    <q-btn color="grey-7" round flat icon="more_vert">
                      <q-menu cover auto-close>
                        <q-list>
                          <q-item
                            clickable
                            @click="downloadAttachment(attachment)"
                          >
                            <q-item-section>Download</q-item-section>
                          </q-item>
                          <q-item
                            clickable
                            @click="removeAttachment(attachment)"
                          >
                            <q-item-section>Excluir</q-item-section>
                          </q-item>
                        </q-list>
                      </q-menu>
                    </q-btn>
                  </div>

                  <q-card-section>
                    <div style="font-size: 14px">
                      {{ attachment.titulo }}
                    </div>
                    <div style="font-size: 12px">
                      {{
                        moment(attachment.data, "YYYY-MM-DD").format(
                          "DD/MM/YYYY"
                        )
                      }}
                    </div>
                  </q-card-section>
                </q-card>
              </div>
            </q-tab-panel>
          </q-tab-panels>
        </q-card-section>
      </q-card>
    </div>
  </div>
</template>

<script>
import {
  addNewAttachment,
  uploadAttachment,
  getAttachments,
  getAttachmentPdf,
  deleteAttachment,
} from "../services/Anexo";

import { getOneConsultancy } from "../services/Consultoria";

import { getOneCompanyConsultancy } from "../services/EmpresasConsultoria";

import { isMyUserAdmin } from "../services/Usuario";

export default {
  name: "PainelAuditor",
  methods: {
    checkFileType(files) {
      return files.filter(
        (file) =>
          file.type === "application/pdf" ||
          file.type ==
            "application/vnd.openxmlformats-officedocument.presentationml.presentation" ||
          file.type ==
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document" ||
          file.type ==
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    },
    onRejected(rejectedEntries) {
      // Notify plugin needs to be installed
      // https://quasar.dev/quasar-plugins/notify#Installation
      this.$q.notify({
        color: "negative",
        message: `Tipo do arquivo deve ser PDF, WORD, EXCEL ou POWER POINT!`,
        position: "top",
        timeout: 1000,
      });
    },
    async getAttachments(codigo) {
      this.attachments = [];
      this.attachments = await getAttachments(codigo);
    },
    async downloadAttachment(attachment) {
      getAttachmentPdf(attachment);
    },
    async removeAttachment(attachment) {
      let response = await deleteAttachment(
        attachment.codigo,
        attachment.codigoConsultoria
      );

      if (response != null && response.status == 204) {
        this.attachments = [];
        this.attachments = await getAttachments(attachment.codigoConsultoria);
        this.$q.notify({
          color: "positive",
          message: "Anexo excluido com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao remover o anexo, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },

    resetFormAttachment() {
      this.newAttachment = {
        titulo: "",
        origem: 2,
        codigoConsultoria: "",
      };

      this.file = null;
    },

    async validateAttachment() {
      if (this.newAttachment.titulo.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Nome do anexo deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.file == null) {
        this.$q.notify({
          color: "negative",
          message: "Selecione um arquivo!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      let body = { ...this.newAttachment };

      body.tipo = this.file.type;
      body.codigoConsultoria =  JSON.parse(localStorage.getItem("USER_DATA")).codigoConsultoria;

      console.log(body);

      this.loadingAttachment = true;
      let response = await addNewAttachment(body);
      console.log(response);

      if (response == null || response.status != 201) {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao tentar adicionar um novo anexo, por favor tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });

        this.loadingAttachment = false;

        return;
      }

      let responseUpload = await uploadAttachment(
        this.file,
        response.data.codigo,
        JSON.parse(localStorage.getItem("USER_DATA")).codigoConsultoria
      );
      console.log(responseUpload);

      if (responseUpload == null || responseUpload.status != 200) {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao tentar adicionar um novo anexo, por favor tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
        this.loadingAttachment = false;

        return;
      }

      this.attachments = [];
      this.attachments = await getAttachments(JSON.parse(localStorage.getItem("USER_DATA")).codigoConsultoria);

      this.resetFormAttachment();

      this.loadingAttachment = false;

      this.$q.notify({
        color: "positive",
        message: "Anexo adicionado com sucesso!",
        position: "top",
        timeout: 1000,
      });
      return;
    },
  },
  async mounted() {
    this.admin = isMyUserAdmin();
    let userData = JSON.parse(localStorage.getItem("USER_DATA"));
    let { codigoConsultoria } = userData;

    this.attachments = await getAttachments(codigoConsultoria);
    this.consultancy = await getOneConsultancy(codigoConsultoria);
  },
  data() {
    return {
      attachments: [],
      consultancy: null,
      loadingAttachment: false,
      newAttachment: {
        titulo: "",
        origem: 2,
        codigoConsultoria: "",
      },
      file: null,
      tab: "dados",
      consultancyDetail: null,
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