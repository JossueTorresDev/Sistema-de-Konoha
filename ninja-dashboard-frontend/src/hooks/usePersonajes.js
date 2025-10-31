import { useState, useEffect } from 'react';
import { personajesAPI } from '../services/api';

export const usePersonajes = () => {
  const [personajes, setPersonajes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchPersonajes = async () => {
    try {
      setLoading(true);
      const response = await personajesAPI.getAll();
      setPersonajes(response.data);
      setError(null);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPersonajes();
  }, []);

  const createPersonaje = async (personaje) => {
    try {
      const response = await personajesAPI.create(personaje);
      setPersonajes(prev => [...prev, response.data]);
      return response.data;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  };

  const updatePersonaje = async (id, personaje) => {
    try {
      const response = await personajesAPI.update(id, personaje);
      setPersonajes(prev => prev.map(p => p.id === id ? response.data : p));
      return response.data;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  };

  const deletePersonaje = async (id) => {
    try {
      await personajesAPI.delete(id);
      setPersonajes(prev => prev.filter(p => p.id !== id));
    } catch (err) {
      setError(err.message);
      throw err;
    }
  };

  return {
    personajes,
    loading,
    error,
    fetchPersonajes,
    createPersonaje,
    updatePersonaje,
    deletePersonaje,
  };
};

export const useTopPersonajes = (limite = 5) => {
  const [topPersonajes, setTopPersonajes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchTopPersonajes = async () => {
      try {
        setLoading(true);
        const response = await personajesAPI.getTop(limite);
        setTopPersonajes(response.data);
        setError(null);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchTopPersonajes();
  }, [limite]);

  return { topPersonajes, loading, error };
};