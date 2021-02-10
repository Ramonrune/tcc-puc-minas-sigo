import Vue from 'vue'


export const addNewProcessItem = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$industryClient.post(`/industry-management-items`,
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


export const updateItemProcessHour = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$industryClient.put(`/industry-management-items/${body.codigo}/hour/${body.hour}`,
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





export const updateItemProcessStatus = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$industryClient.put(`/industry-management-items/${body.codigo}/status/${body.status}`,
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






export const getIndustryManagementItemsList = async (codigo) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$industryClient.get(`/industry-management-items/${codigo}`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}


export const getLogistics = async (codigo) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$industryClient.get(`/logistics/${codigo}`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return null;
    }).catch(err => {
        return null;
    });

}

export const deleteProcessItem = async (processToExclude) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    
    return await Vue.prototype.$industryClient.delete(`/industry-management-items/${processToExclude.codigo}`, config).then(response => {
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