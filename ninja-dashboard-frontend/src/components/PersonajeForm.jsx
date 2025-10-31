import { useState, useEffect } from 'react';
import { User, MapPin, Award, Zap } from 'lucide-react';

const PersonajeForm = ({ personaje, onSubmit, onCancel, aldeas = [] }) => {
  const [formData, setFormData] = useState({
    nombre: '',
    alias: '',
    rango: '',
    aldeaId: '',
    chakra: 50,
    inteligencia: 50,
    fuerza: 50,
    velocidad: 50
  });

  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (personaje) {
      setFormData({
        nombre: personaje.nombre || '',
        alias: personaje.alias || '',
        rango: personaje.rango || '',
        aldeaId: personaje.aldeaId || '',
        chakra: personaje.chakra || 50,
        inteligencia: personaje.inteligencia || 50,
        fuerza: personaje.fuerza || 50,
        velocidad: personaje.velocidad || 50
      });
    }
  }, [personaje]);

  const rangos = [
    'Genin',
    'Chunin', 
    'Jonin',
    'ANBU',
    'Kage',
    'Sannin',
    'Jinchuriki'
  ];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name.includes('chakra') || name.includes('inteligencia') || 
              name.includes('fuerza') || name.includes('velocidad') 
              ? parseInt(value) || 0 
              : value
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
    
    if (formData.chakra < 1 || formData.chakra > 100) {
      newErrors.chakra = 'El chakra debe estar entre 1 y 100';
    }
    
    if (formData.inteligencia < 1 || formData.inteligencia > 100) {
      newErrors.inteligencia = 'La inteligencia debe estar entre 1 y 100';
    }
    
    if (formData.fuerza < 1 || formData.fuerza > 100) {
      newErrors.fuerza = 'La fuerza debe estar entre 1 y 100';
    }
    
    if (formData.velocidad < 1 || formData.velocidad > 100) {
      newErrors.velocidad = 'La velocidad debe estar entre 1 y 100';
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

  const powerLevel = (formData.chakra + formData.inteligencia + formData.fuerza + formData.velocidad) / 4;

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      {/* Información Personal */}
      <div className="card">
        <h4 className="text-lg font-semibold text-gray-900 mb-4 flex items-center">
          <User className="h-5 w-5 mr-2 text-primary-600" />
          Información Personal
        </h4>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Nombre *
            </label>
            <input
              type="text"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              className={`input-field ${errors.nombre ? 'border-red-500' : ''}`}
              placeholder="Ej: Naruto Uzumaki"
            />
            {errors.nombre && (
              <p className="text-red-500 text-sm mt-1">{errors.nombre}</p>
            )}
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Alias
            </label>
            <input
              type="text"
              name="alias"
              value={formData.alias}
              onChange={handleChange}
              className="input-field"
              placeholder="Ej: El Ninja Número Uno"
            />
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Rango
            </label>
            <select
              name="rango"
              value={formData.rango}
              onChange={handleChange}
              className="input-field"
            >
              <option value="">Seleccionar rango</option>
              {rangos.map(rango => (
                <option key={rango} value={rango}>{rango}</option>
              ))}
            </select>
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Aldea
            </label>
            <select
              name="aldeaId"
              value={formData.aldeaId}
              onChange={handleChange}
              className="input-field"
            >
              <option value="">Seleccionar aldea</option>
              {aldeas.map(aldea => (
                <option key={aldea.id} value={aldea.id}>{aldea.nombre}</option>
              ))}
            </select>
          </div>
        </div>
      </div>

      {/* Estadísticas */}
      <div className="card">
        <h4 className="text-lg font-semibold text-gray-900 mb-4 flex items-center">
          <Zap className="h-5 w-5 mr-2 text-yellow-600" />
          Estadísticas de Combate
        </h4>
        
        <div className="space-y-4">
          {/* Chakra */}
          <div>
            <div className="flex justify-between items-center mb-2">
              <label className="text-sm font-medium text-gray-700">Chakra</label>
              <span className="text-blue-600 font-bold">{formData.chakra}</span>
            </div>
            <input
              type="range"
              name="chakra"
              min="1"
              max="100"
              value={formData.chakra}
              onChange={handleChange}
              className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer slider-blue"
            />
            {errors.chakra && (
              <p className="text-red-500 text-sm mt-1">{errors.chakra}</p>
            )}
          </div>

          {/* Inteligencia */}
          <div>
            <div className="flex justify-between items-center mb-2">
              <label className="text-sm font-medium text-gray-700">Inteligencia</label>
              <span className="text-purple-600 font-bold">{formData.inteligencia}</span>
            </div>
            <input
              type="range"
              name="inteligencia"
              min="1"
              max="100"
              value={formData.inteligencia}
              onChange={handleChange}
              className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer slider-purple"
            />
            {errors.inteligencia && (
              <p className="text-red-500 text-sm mt-1">{errors.inteligencia}</p>
            )}
          </div>

          {/* Fuerza */}
          <div>
            <div className="flex justify-between items-center mb-2">
              <label className="text-sm font-medium text-gray-700">Fuerza</label>
              <span className="text-red-600 font-bold">{formData.fuerza}</span>
            </div>
            <input
              type="range"
              name="fuerza"
              min="1"
              max="100"
              value={formData.fuerza}
              onChange={handleChange}
              className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer slider-red"
            />
            {errors.fuerza && (
              <p className="text-red-500 text-sm mt-1">{errors.fuerza}</p>
            )}
          </div>

          {/* Velocidad */}
          <div>
            <div className="flex justify-between items-center mb-2">
              <label className="text-sm font-medium text-gray-700">Velocidad</label>
              <span className="text-green-600 font-bold">{formData.velocidad}</span>
            </div>
            <input
              type="range"
              name="velocidad"
              min="1"
              max="100"
              value={formData.velocidad}
              onChange={handleChange}
              className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer slider-green"
            />
            {errors.velocidad && (
              <p className="text-red-500 text-sm mt-1">{errors.velocidad}</p>
            )}
          </div>
        </div>

        {/* Power Level Preview */}
        <div className="mt-6 p-4 bg-gradient-to-r from-primary-50 to-secondary-50 rounded-xl">
          <div className="flex items-center justify-between">
            <span className="text-sm font-medium text-gray-700">Power Level Calculado:</span>
            <span className="text-2xl font-bold text-primary-600">{powerLevel.toFixed(1)}</span>
          </div>
          <div className="w-full bg-gray-200 rounded-full h-2 mt-2">
            <div 
              className="bg-gradient-to-r from-primary-500 to-primary-600 h-2 rounded-full transition-all duration-500"
              style={{ width: `${Math.min(powerLevel, 100)}%` }}
            ></div>
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
          {personaje ? 'Actualizar Personaje' : 'Crear Personaje'}
        </button>
      </div>
    </form>
  );
};

export default PersonajeForm;