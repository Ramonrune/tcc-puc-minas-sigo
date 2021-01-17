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


export const addNewStandard = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.post(`/api/v1/standards`,
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
    return await Vue.prototype.$axios.get(`/api/v1/industry-management?codigo_filial=${codigoFilial}&data_inicio_planejamento=${dataInicioPlanejamento}&data_fim_planejamento=${dataFimPlanejamento}`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}



export const getStandardPdf = async (standard) => {
    const config = {
        responseType: 'blob',
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/standards/pdf/${standard.codigo}`, config).then(response => {
        const downloadUrl = window.URL.createObjectURL(new Blob([response.data]));

        const link = document.createElement('a');

        link.href = downloadUrl;

        link.setAttribute('download', standard.titulo + ".pdf"); //any other extension

        document.body.appendChild(link);

        link.click();

        link.remove();
    }).catch(err => {
        console.log(err);
    });

}





export const deleteStandard = async (id) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.delete(`/api/v1/standards/${id}`, config).then(response => {
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