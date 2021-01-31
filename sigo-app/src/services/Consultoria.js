import Vue from 'vue'

export const addNewConsultancy = async (body) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.post(`/api/v1/consultancy`,
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


export const uploadConsultancy = async (file, codigo) => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
            'Content-Type': 'multipart/form-data'
        },
    };

    let bodyFormData = new FormData();
    bodyFormData.append('file', file);

    return await Vue.prototype.$axios.post(`/api/v1/consultancy/upload/${codigo}`,
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




export const getConsultancy = async () => {
    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/consultancy`, config).then(response => {
        if (response.status == 200) {
            return response.data;
        }
        return [];
    }).catch(err => {
        return [];
    });

}



export const getConsultancyPdf = async (consultancy) => {
    const config = {
        responseType: 'blob',
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.get(`/api/v1/consultancy/pdf/${consultancy.codigo}`, config).then(response => {
        const downloadUrl = window.URL.createObjectURL(new Blob([response.data]));

        const link = document.createElement('a');

        link.href = downloadUrl;

        link.setAttribute('download', consultancy.nome + ".pdf"); //any other extension

        document.body.appendChild(link);

        link.click();

        link.remove();
    }).catch(err => {
        console.log(err);
    });

}





export const deleteConsultancy = async (id) => {

    const config = {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
        },
    };
    return await Vue.prototype.$axios.delete(`/api/v1/consultancy/${id}`, config).then(response => {
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