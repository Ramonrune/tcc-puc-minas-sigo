import Vue from 'vue'

export const addNewStandard = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.post(`/api/v1/users`,
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
