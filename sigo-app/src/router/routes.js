import { isMyUserAdmin, isMyUserCommon, isMyUserAudit } from "../services/Usuario";

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import('layouts/LoginLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Login.vue') },
      { path: '403', component: () => import('pages/Error403.vue') },

    ]
  },

  {
    path: '/sigo',
    component: () => import('layouts/MainLayout.vue'),
    beforeEnter(to, from, next) {
      let token = localStorage.getItem("TOKEN");

      if (token == "null" || token == null && (!isMyUserAdmin() || !isMyUserCommon)) {
        next({name: 'login'});
        window.location.href = "#/";

      }
      else{

        if (isMyUserAudit() && to.path != '/sigo/consultor') {
          next({name: '403'});
          window.location.href = "#/403";
          return;
        }
        
        next();
      }
    },
    children: [
      { path: '', component: () => import('pages/Index.vue') },
      { path: 'normas', component: () => import('pages/NormasTecnicas.vue') },
      { path: 'processos', component: () => import('pages/Processos.vue') },
      { name: 'processo', path: 'processo', component: () => import('pages/ProcessoDetalhes.vue') },
      { path: 'ocorrencias', component: () => import('pages/Ocorrencias.vue') },
      { path: 'empresas-consultoria', component: () => import('pages/EmpresasConsultoria.vue') },
      { path: 'consultoria', component: () => import('pages/Consultoria.vue') },
      { path: 'relatorios', component: () => import('pages/Relatorios.vue') },
      { path: 'filiais', component: () => import('pages/Filiais.vue') },
      { path: 'usuarios', component: () => import('pages/Usuarios.vue') },



    ]
  },

  {
    path: '/sigo/consultor',
    component: () => import('layouts/AuditLayout.vue'),
    beforeEnter(to, from, next) {
      let token = localStorage.getItem("TOKEN");

      if (token == "null" || token == null || !isMyUserAudit()) {
        next({name: 'login'});
        window.location.href = "#/";

      }
      else{
        next();
      }
    },
    children: [
      { path: '', component: () => import('pages/PainelAuditor.vue') },

  

    ]
  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')

  }
]

export default routes
