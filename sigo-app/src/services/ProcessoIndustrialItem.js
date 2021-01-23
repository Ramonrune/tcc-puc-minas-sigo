import Vue from 'vue'


export const addNewProcess = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.post(`/api/v1/industry-management`,
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




export const getIndustryManagementItemsList = async (codigo) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/industry-management-items/${codigo}`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}


export const getIndustryManagementList = async (codigoFilial, dataInicioPlanejamento, dataFimPlanejamento) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/industry-management?codigo_filial=${codigoFilial}&data_inicio_planejamento=${dataInicioPlanejamento}&data_fim_planejamento=${dataFimPlanejamento}`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}


export const updateProcessStatus = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.put(`/api/v1/industry-management/${body.codigo}/status/${body.status}`,
        body,
        config).then(response => {

            if (response.status == 200) {
                return response;
            }
            return null;
        }).catch(err => {
            return null;
        });

}



export const deleteProcess = async (processToExclude) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    
    return await Vue.prototype.$axios.delete(`/api/v1/industry-management/${processToExclude.codigo}?codigo_filial=${processToExclude.codigoFilial}`, config).then(response => {
        if (response == undefined) {
            return  { status: 409 };
        }
        if (response.status == 204) {
            return response;
        }
        return null;
    }).catch(err => {
        console.log(err);
        if (err.response) {
            return err.response;
        }
        return null;
    });

}