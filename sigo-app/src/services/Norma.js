import Vue from 'vue'

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


export const uploadStandard = async (file) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
            'Content-Type': 'multipart/form-data'
        },
    };

    let bodyFormData = new FormData();
    bodyFormData.append('file', file);

    return await Vue.prototype.$axios.post(`/api/v1/standards/upload`,
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




export const getStandards = async () => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/standards`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
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