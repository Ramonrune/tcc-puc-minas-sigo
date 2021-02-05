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


export const addNewUser = async (body) => {
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


export const editUser = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.put(`/api/v1/users/${body.codigo}`,
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



export const getUsers = async () => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/users`, config).then(response => {
            if (response.status == 200) {
                return response.data;
            }
            return [];
        }).catch(err => {
            return [];
        });

}


export const userExists = async (email) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };

    email = btoa(email);

    console.log(`/api/v1/users/exists/${email}`);
    return await Vue.prototype.$axios.get(`/api/v1/users/exists/${email}`, config).then(response => {
            if (response.status == 200) {
                return false;
            }
            return true;
        }).catch(err => {
            return true;
        });

}


export const deleteUser = async (id) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.delete(`/api/v1/users/${id}`, config).then(response => {
            if (response.status == 204) {
                return response;
            }
            return null;
        }).catch(err => {
            return null;
        });

}



export const isMyUserAdmin = () => {
    let userData = JSON.parse(localStorage.getItem("USER_DATA"));
    let hasAdmin =
      userData.permissoes.filter((e) => e.descricao == "ROLE_ADMIN").length > 0
        ? true
        : false;

    return hasAdmin;
}

export const isMyUserCommon = () => {
    let userData = JSON.parse(localStorage.getItem("USER_DATA"));
    let hasAdmin =
      userData.permissoes.filter((e) => e.descricao == "ROLE_COMMON_USER").length > 0
        ? true
        : false;

    return hasAdmin;
}


export const isMyUserAudit = () => {
    let userData = JSON.parse(localStorage.getItem("USER_DATA"));
    let hasAdmin =
      userData.permissoes.filter((e) => e.descricao == "ROLE_AUDIT_USER").length > 0
        ? true
        : false;

    return hasAdmin;
}