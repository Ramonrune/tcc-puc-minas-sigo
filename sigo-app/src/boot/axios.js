import Vue from 'vue'
import axios from 'axios'

const client = axios.create({
	baseURL: 'http://localhost:8000/'
	//baseURL: 'https://api.qyonbank.net/'
	//baseURL: 'http://bank.qyon.com.br:19096/'
});



Vue.prototype.$axios = client

