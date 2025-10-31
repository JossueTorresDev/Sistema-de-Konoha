import axios from 'axios';

const API_BASE_URL = 'http://localhost:5000/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para manejo de errores
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

// Dashboard API
export const dashboardAPI = {
  getStats: () => api.get('/dashboard/stats'),
  getResumen: () => api.get('/dashboard/resumen'),
};

// Personajes API
export const personajesAPI = {
  getAll: () => api.get('/personajes'),
  getById: (id) => api.get(`/personajes/${id}`),
  getTop: (limite) => api.get(`/personajes/top/${limite}`),
  getByAldea: (aldeaId) => api.get(`/personajes/aldea/${aldeaId}`),
  getByRango: (rango) => api.get(`/personajes/rango/${rango}`),
  getRanking: (page = 0, size = 10) => api.get(`/personajes/ranking?page=${page}&size=${size}`),
  create: (personaje) => api.post('/personajes', personaje),
  update: (id, personaje) => api.put(`/personajes/${id}`, personaje),
  delete: (id) => api.delete(`/personajes/${id}`),
  getCount: () => api.get('/personajes/count'),
};

// Aldeas API
export const aldeasAPI = {
  getAll: () => api.get('/aldeas'),
  getById: (id) => api.get(`/aldeas/${id}`),
  getStats: () => api.get('/aldeas/stats'),
  create: (aldea) => api.post('/aldeas', aldea),
  update: (id, aldea) => api.put(`/aldeas/${id}`, aldea),
  delete: (id) => api.delete(`/aldeas/${id}`),
  getCount: () => api.get('/aldeas/count'),
};

// Jutsus API
export const jutsusAPI = {
  getAll: () => api.get('/jutsus'),
  getById: (id) => api.get(`/jutsus/${id}`),
  getByTipo: (tipo) => api.get(`/jutsus/tipo/${tipo}`),
  getStats: () => api.get('/jutsus/stats'),
  getTipos: () => api.get('/jutsus/tipos'),
  create: (jutsu) => api.post('/jutsus', jutsu),
  update: (id, jutsu) => api.put(`/jutsus/${id}`, jutsu),
  delete: (id) => api.delete(`/jutsus/${id}`),
  getCount: () => api.get('/jutsus/count'),
};

export default api;