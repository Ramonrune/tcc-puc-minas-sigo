import Vue from 'vue'

export const addNewConsultancy = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$consultancyClient.post(`/consultancy`,
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


export const getConsultancy = async (codigoEmpresaConsultoria, codigoFilial, dataInicio, dataFim) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$consultancyClient.get(`/consultancy?codigo_empresa_consultoria=${codigoEmpresaConsultoria}&codigo_filial=${codigoFilial}&data_inicio=${dataInicio}&data_fim=${dataFim}`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}



export const getAllConsultancies = async () => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$consultancyClient.get(`/consultancy/all`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}


export const getOneConsultancy = async (codigo) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$consultancyClient.get(`/consultancy/${codigo}`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}




export const deleteConsultancy = async (id) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$consultancyClient.delete(`/consultancy/${id}`, config).then(response => {
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