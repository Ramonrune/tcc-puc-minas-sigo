import Vue from 'vue'


export const login = async (email, password) => {
    const params = new URLSearchParams();
    params.append("username", email);
    params.append("password", password);
    params.append("grant_type", "password");

    const config = {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            Authorization: "Basic c2lnbzpzaWdv",
        },
    };

    console.log('aquiiiiiiiii')
    return await Vue.prototype.$axios.post(`/oauth/token`, params, config).then(response => {
        console.log(response);
        if (response.status == 200) {
            return response;
        }
        return null;
    }).catch(err => {
        if(err.response){
            return err.response;
        }
        return null;
    });

}

