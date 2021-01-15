import Vue from 'vue'
import axios from 'axios'

const client = axios.create({
	baseURL: 'http://localhost:8000/'
	//baseURL: 'https://api.qyonbank.net/'
	//baseURL: 'http://bank.qyon.com.br:19096/'
});


client.interceptors.response.use(
	res => res,
	err => {

		if (err.response) {
			if (err.response.status === 401) {
				localStorage.setItem("TOKEN", null);
				localStorage.setItem("CODE", null);
				localStorage.setItem("NAME", null);
				window.location.href = "/";

			}
		}

		return Promise.reject(err);


	});



Vue.prototype.$axios = client

