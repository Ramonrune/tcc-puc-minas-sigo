
import Vue from 'vue'

export const addNewCompany = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.post(`/api/v1/companies`,
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


export const editCompany = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.put(`/api/v1/companies/${body.codigo}`,
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



export const getCompanies = async () => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/companies`, config).then(response => {
            if (response.status == 200) {
                return response.data;
            }
            return [];
        }).catch(err => {
            return [];
        });

}


export const companyExists = async (cnpj) => {
    cnpj = cnpj.match(/\d/g).join("");

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/companies/exists/${cnpj}`, config).then(response => {
            if (response.status == 200) {
                return false;
            }
            return true;
        }).catch(err => {
            return true;
        });

}


export const deleteCompany = async (id) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.delete(`/api/v1/companies/${id}`, config).then(response => {
            if (response.status == 204) {
                return response;
            }
            return null;
        }).catch(err => {
            return null;
        });

}