import { useState, useEffect } from 'react';
import { personajesAPI, aldeasAPI, jutsusAPI } from '../services/api';

export const useStats = () => {
  const [stats, setStats] = useState({
    totalPersonajes: 0,
    totalAldeas: 0,
    totalJutsus: 0,
    promedioPowerLevel: 0
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const calculatePowerLevel = (personaje) => {
    const { chakra = 0, inteligencia = 0, fuerza = 0, velocidad = 0 } = personaje;
    return (chakra + inteligencia + fuerza + velocidad) / 4;
  };

  const fetchStats = async () => {
    try {
      setLoading(true);
      
      // Obtener datos de todas las APIs
      const [personajesResponse, aldeasResponse, jutsusResponse] = await Promise.all([
        personajesAPI.getAll(),
        aldeasAPI.getStats(),
        jutsusAPI.getStats()
      ]);

      const personajes = personajesResponse.data;
      const aldeas = aldeasResponse.data;
      const jutsus = jutsusResponse.data;

      // Calcular power levels para todos los personajes
      const personajesConPowerLevel = personajes.map(personaje => ({
        ...personaje,
        powerLevel: calculatePowerLevel(personaje)
      }));

      // Calcular promedio de power level
      const promedioPowerLevel = personajesConPowerLevel.length > 0 
        ? personajesConPowerLevel.reduce((sum, p) => sum + p.powerLevel, 0) / personajesConPowerLevel.length
        : 0;

      setStats({
        totalPersonajes: personajes.length,
        totalAldeas: aldeas.length,
        totalJutsus: jutsus.length,
        promedioPowerLevel: parseFloat(promedioPowerLevel.toFixed(1))
      });

      setError(null);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchStats();
  }, []);

  return { stats, loading, error, refreshStats: fetchStats };
};