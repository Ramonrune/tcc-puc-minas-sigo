import Vue from 'vue'

export const addNewAttachment = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$consultancyClient.post(`/attachment`,
        body,
        config).then(response => {

            if (response.status == 201) {
                return response;
            }
            return null;
        }).catch(err => {
            return null;
        });

}


export const uploadAttachment = async (file, codigo, codigoConsultoria) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
            'Content-Type': 'multipart/form-data'
        },
    };

    let bodyFormData = new FormData();
    bodyFormData.append('file', file);

    return await Vue.prototype.$consultancyClient.post(`/attachment/upload/${codigo}?codigo_consultoria=${codigoConsultoria}`,
        bodyFormData,
        config).then(response => {

            if (response.status == 200) {
                return response;
            }
            return null;
        }).catch(err => {
            return null;
        });

}




export const getAttachments = async (codigoConsultoria) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$consultancyClient.get(`/attachment?codigoConsultoria=${codigoConsultoria}`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}



export const getAttachmentPdf = async (attachment) => {
    const config = {
        responseType: 'blob',
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$consultancyClient.get(`/attachment/${attachment.codigo}?codigo_consultoria=${attachment.codigoConsultoria}`, config).then(response => {
        const downloadUrl = window.URL.createObjectURL(new Blob([response.data]));

        const link = document.createElement('a');

        link.href = downloadUrl;

        let type = '.txt';

        if(attachment.tipo ==  'application/vnd.openxmlformats-officedocument.presentationml.presentation'){
            type = '.pptx';
        }

        if(attachment.tipo == 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'){
            type = '.docx';

        }

        if(attachment.tipo == 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'){
            type = '.xlsx';

        }

        if(attachment.tipo == 'application/pdf'){
            type = '.pdf';

        }

        link.setAttribute('download', attachment.titulo + type); //any other extension

        document.body.appendChild(link);

        link.click();

        link.remove();
    }).catch(err => {
        console.log(err);
    });

}





export const deleteAttachment = async (id, codigoConsultoria) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$consultancyClient.delete(`/attachment/${id}?codigo_consultoria=${codigoConsultoria}`, config).then(response => {
        if (response.status == 204) {
            return response;
        }
        return null;
    }).catch(err => {

        if (err.response) {
            return err.response;
        }
        return null;
    });

}