import Vue from 'vue'

export const getPermissoes = async () => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/permissions`, config).then(response => {
            if (response.status == 200) {
                return response.data;
            }
            return [];
        }).catch(err => {
            return [];
        });

}
