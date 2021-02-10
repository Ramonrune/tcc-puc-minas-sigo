import Vue from 'vue'
import axios from 'axios'

const identityClient = axios.create({
	baseURL: 'http://localhost:8001/'
});

const consultancyClient = axios.create({
	baseURL: 'http://localhost:8004/'
});

const standardClient = axios.create({
	baseURL: 'http://localhost:8002/'
});


const industryClient = axios.create({
	baseURL: 'http://localhost:8003/'
});



identityClient.interceptors.response.use(
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


consultancyClient.interceptors.response.use(
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



standardClient.interceptors.response.use(
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



	industryClient.interceptors.response.use(
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
	


Vue.prototype.$identityClient = identityClient
Vue.prototype.$consultancyClient = consultancyClient
Vue.prototype.$standardClient = standardClient
Vue.prototype.$industryClient = industryClient



