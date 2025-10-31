import { useState, useEffect } from 'react';
import { Zap, Star } from 'lucide-react';

const JutsuForm = ({ jutsu, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    nombre: '',
    tipo: '',
    nivel: 1,
    descripcion: ''
  });

  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (jutsu) {
      setFormData({
        nombre: jutsu.nombre || '',
        tipo: jutsu.tipo || '',
        nivel: jutsu.nivel || 1,
        descripcion: jutsu.descripcion || ''
      });
    }
  }, [jutsu]);

  const tipos = [
    'Ninjutsu',
    'Genjutsu', 
    'Taijutsu',
    'Kekkei Genkai',
    'Fuinjutsu',
    'Senjutsu',
    'Dojutsu'
  ];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'nivel' ? parseInt(value) || 1 : value
    }));
    
    // Limpiar error del campo cuando el usuario empiece a escribir
    if (errors[name]) {
      setErrors(prev => ({
        ...prev,
        [name]: ''
      }));
    }
  };

  const validateForm = () => {
    const newErrors = {};
    
    if (!formData.nombre.trim()) {
      newErrors.nombre = 'El nombre es requerido';
    }
    
    if (!formData.tipo) {
      newErrors.tipo = 'El tipo es requerido';
    }
    
    if (formData.nivel < 1 || formData.nivel > 10) {
      newErrors.nivel = 'El nivel debe estar entre 1 y 10';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      onSubmit(formData);
    }
  };

  const getTipoColor = (tipo) => {
    const colors = {
      'Ninjutsu': 'bg-blue-100 text-blue-800',
      'Genjutsu': 'bg-purple-100 text-purple-800',
      'Taijutsu': 'bg-green-100 text-green-800',
      'Kekkei Genkai': 'bg-red-100 text-red-800',
      'Fuinjutsu': 'bg-yellow-100 text-yellow-800',
      'Senjutsu': 'bg-indigo-100 text-indigo-800',
      'Dojutsu': 'bg-pink-100 text-pink-800'
    };
    return colors[tipo] || 'bg-gray-100 text-gray-800';
  };

  const getNivelStars = (nivel) => {
    return Array.from({ length: 10 }, (_, i) => (
      <Star
        key={i}
        className={`h-4 w-4 ${i < nivel ? 'text-yellow-400 fill-current' : 'text-gray-300'}`}
      />
    ));
  };

  const getDificultadText = (nivel) => {
    if (nivel <= 3) return 'Básico';
    if (nivel <= 6) return 'Intermedio';
    if (nivel <= 8) return 'Avanzado';
    return 'Maestro';
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      {/* Información Principal */}
      <div className="card">
        <h4 className="text-lg font-semibold text-gray-900 mb-4 flex items-center">
          <Zap className="h-5 w-5 mr-2 text-yellow-600" />
          Información del Jutsu
        </h4>
        
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Nombre del Jutsu *
            </label>
            <input
              type="text"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              className={`input-field ${errors.nombre ? 'border-red-500' : ''}`}
              placeholder="Ej: Rasengan"
            />
            {errors.nombre && (
              <p className="text-red-500 text-sm mt-1">{errors.nombre}</p>
            )}
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Tipo de Jutsu *
            </label>
            <select
              name="tipo"
              value={formData.tipo}
              onChange={handleChange}
              className={`input-field ${errors.tipo ? 'border-red-500' : ''}`}
            >
              <option value="">Seleccionar tipo</option>
              {tipos.map(tipo => (
                <option key={tipo} value={tipo}>{tipo}</option>
              ))}
            </select>
            {errors.tipo && (
              <p className="text-red-500 text-sm mt-1">{errors.tipo}</p>
            )}
            
            {formData.tipo && (
              <div className="mt-2">
                <span className={`inline-block px-3 py-1 text-sm font-medium rounded-full ${getTipoColor(formData.tipo)}`}>
                  {formData.tipo}
                </span>
              </div>
            )}
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Descripción
            </label>
            <textarea
              name="descripcion"
              value={formData.descripcion}
              onChange={handleChange}
              rows={4}
              className="input-field resize-none"
              placeholder="Describe las características y efectos del jutsu..."
            />
          </div>
        </div>
      </div>

      {/* Nivel de Dificultad */}
      <div className="card">
        <h4 className="text-lg font-semibold text-gray-900 mb-4 flex items-center">
          <Star className="h-5 w-5 mr-2 text-yellow-600" />
          Nivel de Dificultad
        </h4>
        
        <div className="space-y-4">
          <div>
            <div className="flex justify-between items-center mb-2">
              <label className="text-sm font-medium text-gray-700">Nivel</label>
              <div className="flex items-center space-x-2">
                <span className="text-2xl font-bold text-yellow-600">{formData.nivel}</span>
                <span className="text-sm text-gray-500">/ 10</span>
              </div>
            </div>
            
            <input
              type="range"
              name="nivel"
              min="1"
              max="10"
              value={formData.nivel}
              onChange={handleChange}
              className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer"
            />
            
            {errors.nivel && (
              <p className="text-red-500 text-sm mt-1">{errors.nivel}</p>
            )}
          </div>

          {/* Visualización de estrellas */}
          <div className="flex items-center justify-center space-x-1 py-2">
            {getNivelStars(formData.nivel)}
          </div>

          {/* Indicador de dificultad */}
          <div className="text-center">
            <span className={`inline-block px-4 py-2 rounded-full text-sm font-semibold ${
              formData.nivel <= 3 ? 'bg-green-100 text-green-800' :
              formData.nivel <= 6 ? 'bg-yellow-100 text-yellow-800' :
              formData.nivel <= 8 ? 'bg-orange-100 text-orange-800' :
              'bg-red-100 text-red-800'
            }`}>
              {getDificultadText(formData.nivel)}
            </span>
          </div>

          {/* Barra de progreso */}
          <div className="w-full bg-gray-200 rounded-full h-3">
            <div 
              className="bg-gradient-to-r from-yellow-400 to-yellow-600 h-3 rounded-full transition-all duration-500"
              style={{ width: `${(formData.nivel / 10) * 100}%` }}
            ></div>
          </div>
        </div>
      </div>

      {/* Preview */}
      <div className="card bg-gradient-to-r from-yellow-50 to-orange-50">
        <h4 className="text-lg font-semibold text-gray-900 mb-4">Vista Previa</h4>
        <div className="space-y-2">
          <div className="flex items-center justify-between">
            <span className="text-sm text-gray-600">Nombre:</span>
            <span className="font-semibold">{formData.nombre || 'Sin nombre'}</span>
          </div>
          <div className="flex items-center justify-between">
            <span className="text-sm text-gray-600">Tipo:</span>
            <span className="font-semibold">{formData.tipo || 'Sin tipo'}</span>
          </div>
          <div className="flex items-center justify-between">
            <span className="text-sm text-gray-600">Dificultad:</span>
            <span className="font-semibold">{getDificultadText(formData.nivel)}</span>
          </div>
        </div>
      </div>

      {/* Botones */}
      <div className="flex justify-end space-x-4 pt-6 border-t border-gray-200">
        <button
          type="button"
          onClick={onCancel}
          className="btn-outline"
        >
          Cancelar
        </button>
        <button
          type="submit"
          className="btn-primary"
        >
          {jutsu ? 'Actualizar Jutsu' : 'Crear Jutsu'}
        </button>
      </div>
    </form>
  );
};

export default JutsuForm;