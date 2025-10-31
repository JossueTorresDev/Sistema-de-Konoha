import { useState, useEffect } from 'react';
import { MapPin, Users } from 'lucide-react';

const AldeaForm = ({ aldea, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    nombre: '',
    region: '',
    descripcion: ''
  });

  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (aldea) {
      setFormData({
        nombre: aldea.nombre || '',
        region: aldea.region || '',
        descripcion: aldea.descripcion || ''
      });
    }
  }, [aldea]);

  const regiones = [
    'País del Fuego',
    'País del Viento',
    'País del Agua',
    'País de la Tierra',
    'País del Rayo',
    'País del Hierro',
    'País de la Lluvia',
    'País del Sonido',
    'País de la Hierba',
    'País de la Cascada'
  ];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
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
    
    if (!formData.region.trim()) {
      newErrors.region = 'La región es requerida';
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

  const getRegionColor = (region) => {
    const colors = {
      'País del Fuego': 'bg-red-100 text-red-800',
      'País del Viento': 'bg-yellow-100 text-yellow-800',
      'País del Agua': 'bg-blue-100 text-blue-800',
      'País de la Tierra': 'bg-green-100 text-green-800',
      'País del Rayo': 'bg-purple-100 text-purple-800',
      'País del Hierro': 'bg-gray-100 text-gray-800',
      'País de la Lluvia': 'bg-indigo-100 text-indigo-800',
      'País del Sonido': 'bg-pink-100 text-pink-800',
      'País de la Hierba': 'bg-emerald-100 text-emerald-800',
      'País de la Cascada': 'bg-cyan-100 text-cyan-800'
    };
    return colors[region] || 'bg-gray-100 text-gray-800';
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      {/* Información Principal */}
      <div className="card">
        <h4 className="text-lg font-semibold text-gray-900 mb-4 flex items-center">
          <MapPin className="h-5 w-5 mr-2 text-secondary-600" />
          Información de la Aldea
        </h4>
        
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Nombre de la Aldea *
            </label>
            <input
              type="text"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              className={`input-field ${errors.nombre ? 'border-red-500' : ''}`}
              placeholder="Ej: Aldea Oculta de la Hoja"
            />
            {errors.nombre && (
              <p className="text-red-500 text-sm mt-1">{errors.nombre}</p>
            )}
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Región *
            </label>
            <select
              name="region"
              value={formData.region}
              onChange={handleChange}
              className={`input-field ${errors.region ? 'border-red-500' : ''}`}
            >
              <option value="">Seleccionar región</option>
              {regiones.map(region => (
                <option key={region} value={region}>{region}</option>
              ))}
            </select>
            {errors.region && (
              <p className="text-red-500 text-sm mt-1">{errors.region}</p>
            )}
            
            {formData.region && (
              <div className="mt-2">
                <span className={`inline-block px-3 py-1 text-sm font-medium rounded-full ${getRegionColor(formData.region)}`}>
                  {formData.region}
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
              rows={5}
              className="input-field resize-none"
              placeholder="Describe la historia, características y cultura de la aldea..."
            />
          </div>
        </div>
      </div>

      {/* Información Adicional */}
      <div className="card bg-gradient-to-r from-secondary-50 to-primary-50">
        <h4 className="text-lg font-semibold text-gray-900 mb-4 flex items-center">
          <Users className="h-5 w-5 mr-2 text-primary-600" />
          Características
        </h4>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="text-center p-4 bg-white rounded-xl shadow-sm">
            <MapPin className="h-8 w-8 text-secondary-600 mx-auto mb-2" />
            <div className="text-sm text-gray-600">Ubicación</div>
            <div className="font-semibold text-gray-900">
              {formData.region || 'Sin región'}
            </div>
          </div>
          
          <div className="text-center p-4 bg-white rounded-xl shadow-sm">
            <Users className="h-8 w-8 text-primary-600 mx-auto mb-2" />
            <div className="text-sm text-gray-600">Estado</div>
            <div className="font-semibold text-green-600">Nueva Aldea</div>
          </div>
        </div>
      </div>

      {/* Preview */}
      <div className="card bg-gradient-to-r from-blue-50 to-indigo-50">
        <h4 className="text-lg font-semibold text-gray-900 mb-4">Vista Previa</h4>
        <div className="space-y-3">
          <div className="flex items-start space-x-3">
            <div className="p-2 bg-secondary-100 rounded-lg">
              <MapPin className="h-6 w-6 text-secondary-600" />
            </div>
            <div className="flex-1">
              <h5 className="font-bold text-gray-900 text-lg">
                {formData.nombre || 'Nombre de la aldea'}
              </h5>
              <p className="text-gray-500 text-sm">
                {formData.region || 'Región no especificada'}
              </p>
              {formData.descripcion && (
                <p className="text-gray-600 text-sm mt-2 line-clamp-3">
                  {formData.descripcion}
                </p>
              )}
            </div>
          </div>
          
          <div className="flex items-center justify-between pt-3 border-t border-gray-200">
            <div className="flex items-center space-x-2">
              <Users className="h-4 w-4 text-gray-400" />
              <span className="text-sm text-gray-600">0 ninjas</span>
            </div>
            <span className="text-sm text-secondary-600 font-medium">Nueva aldea</span>
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
          {aldea ? 'Actualizar Aldea' : 'Crear Aldea'}
        </button>
      </div>
    </form>
  );
};

export default AldeaForm;