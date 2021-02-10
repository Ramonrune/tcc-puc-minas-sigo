
import Vue from 'vue'

export const addNewCompany = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$identityClient.post(`/companies`,
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
    return await Vue.prototype.$identityClient.put(`/companies/${body.codigo}`,
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
    return await Vue.prototype.$identityClient.get(`/companies`, config).then(response => {
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
    return await Vue.prototype.$identityClient.get(`/companies/exists/${cnpj}`, config).then(response => {
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
    return await Vue.prototype.$identityClient.delete(`/companies/${id}`, config).then(response => {
        if (response == undefined) {
            return  { status: 409 };
        }
        if (response.status == 204) {
            return response;
        }
        return null;
    }).catch(err => {
        console.log('aquiii')
        console.log(err);
        if (err.response) {
            return err.response;
        }
        return null;
    });

}