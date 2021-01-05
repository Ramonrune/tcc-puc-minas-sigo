
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') },
      { path: 'normas', component: () => import('pages/NormasTecnicas.vue') },
      { path: 'processos', component: () => import('pages/Processos.vue') },
      { path: 'ocorrencias', component: () => import('pages/Ocorrencias.vue') },
      { path: 'consultorias', component: () => import('pages/Consultoria.vue') },
      { path: 'relatorios', component: () => import('pages/Relatorios.vue') },
      { path: 'filiais', component: () => import('pages/Filiais.vue') },
      { path: 'usuarios', component: () => import('pages/Usuarios.vue') },


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
