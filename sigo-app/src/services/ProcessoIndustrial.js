import Vue from 'vue'

export const getStatus = () => {

    return [
        {
            codigo: -1,
            nome: 'Todos'
        },
        {
            codigo: 0,
            nome: 'Não iniciado'
        },
        {
            codigo: 1,
            nome: 'Aguardando'
        },
        {
            codigo: 2,
            nome: 'Em andamento'
        },
        {
            codigo: 4,
            nome: 'Finalizado'
        },
        {
            codigo: 3,
            nome: 'Falha'
        },

        {
            codigo: 5,
            nome: 'Cancelado'
        },
    ]
}


export const getStatusDescription = (statusCode) => {
    if (statusCode == -1) {
        return 'Todos';
    }

    if (statusCode == 0) {
        return 'Não iniciado';
    }

    if (statusCode == 1) {
        return 'Aguardando';
    }

    if (statusCode == 2) {
        return 'Em andamento';
    }

    if (statusCode == 3) {
        return 'Falha';
    }

    if (statusCode == 4) {
        return 'Finalizado';
    }

    if (statusCode == 5) {
        return 'Cancelado'
    }

    return '';

}


export const addNewProcess = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$industryClient.post(`/industry-management`,
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





export const getIndustryManagementList = async (codigoFilial, dataInicioPlanejamento, dataFimPlanejamento) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$industryClient.get(`/industry-management?codigo_filial=${codigoFilial}&data_inicio_planejamento=${dataInicioPlanejamento}&data_fim_planejamento=${dataFimPlanejamento}`, config).then(response => {
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
    return await Vue.prototype.$industryClient.put(`/industry-management/${body.codigo}/status/${body.status}`,
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
    
    return await Vue.prototype.$industryClient.delete(`/industry-management/${processToExclude.codigo}?codigo_filial=${processToExclude.codigoFilial}`, config).then(response => {
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