import Vue from 'vue'

export const getStatus = () => {

    return [
        {
            codigo: 0,
            nome: 'Não solucionada'
        },
        {
            codigo: 1,
            nome: 'Solucionada'
        }
    ]
}


export const getStatusDescription = (statusCode) => {
    if (statusCode == 0) {
        return 'Não solucionada';
    }

    if (statusCode == 1) {
        return 'Solucionada';
    }

  
    return '';

}


export const addNewOccurrence = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.post(`/api/v1/occurrence`,
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





export const getOccurrenceList = async (codigoFilial, dataInicio, dataFim) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/occurrence?codigo_filial=${codigoFilial}&data_inicio=${dataInicio}&data_fim=${dataFim}`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}


export const updateOccurrenceStatus = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.put(`/api/v1/occurrence/${body.codigo}/status/${body.status}`,
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



export const deleteOccurrence = async (occurrenceToExclude) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    
    return await Vue.prototype.$axios.delete(`/api/v1/occurrence/${occurrenceToExclude.codigo}?codigo_filial=${occurrenceToExclude.codigoFilial}`, config).then(response => {
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