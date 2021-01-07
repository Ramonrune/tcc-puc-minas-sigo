import Vue from 'vue'

export const getUserInfo = async (email, password) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };

    return await Vue.prototype.$axios.get(`/api/v1/users/info`, config).then(response => {
        if (response.status == 200) {
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