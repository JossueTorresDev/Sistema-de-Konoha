import { useState, useEffect } from 'react';
import { Plus, Search, Filter, Edit, Trash2, Zap, Star, Eye } from 'lucide-react';
import { jutsusAPI } from '../services/api';
import { useAlert } from '../hooks/useAlert';
import LoadingSpinner from '../components/LoadingSpinner';
import AlertContainer from '../components/AlertContainer';
import Modal from '../components/Modal';
import JutsuForm from '../components/JutsuForm';

const Jutsus = () => {
  const [jutsus, setJutsus] = useState([]);
  const [tipos, setTipos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterTipo, setFilterTipo] = useState('');
  const [selectedJutsu, setSelectedJutsu] = useState(null);
  const [showDetailsModal, setShowDetailsModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const { alerts, removeAlert, showSuccess, showError, showConfirm, showInfo } = useAlert();

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [jutsusResponse, tiposResponse] = await Promise.all([
        jutsusAPI.getStats(),
        jutsusAPI.getTipos()
      ]);
      setJutsus(jutsusResponse.data);
      setTipos(tiposResponse.data);
      setError(null);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id, nombre) => {
    showConfirm(
      'Confirmar eliminación',
      `¿Estás seguro de que quieres eliminar el jutsu ${nombre}? Esta acción no se puede deshacer.`,
      async () => {
        try {
          await jutsusAPI.delete(id);
          await fetchData();
          showSuccess('¡Eliminado!', `El jutsu ${nombre} ha sido eliminado correctamente.`);
        } catch (error) {
          showError('Error', 'No se pudo eliminar el jutsu. Inténtalo de nuevo.');
        }
      },
      () => {
        // No hacer nada al cancelar
      }
    );
  };

  const handleViewDetails = (jutsu) => {
    setSelectedJutsu(jutsu);
    setShowDetailsModal(true);
  };

  const handleEdit = (jutsu) => {
    setSelectedJutsu(jutsu);
    setShowEditModal(true);
  };

  const handleCreate = () => {
    setShowCreateModal(true);
  };

  const handleSubmitCreate = async (formData) => {
    try {
      await jutsusAPI.create(formData);
      await fetchData();
      setShowCreateModal(false);
      showSuccess('¡Creado!', 'El jutsu ha sido creado correctamente.');
    } catch (error) {
      showError('Error', 'No se pudo crear el jutsu. Inténtalo de nuevo.');
    }
  };

  const handleSubmitEdit = async (formData) => {
    try {
      await jutsusAPI.update(selectedJutsu.id, formData);
      await fetchData();
      setShowEditModal(false);
      setSelectedJutsu(null);
      showSuccess('¡Actualizado!', 'El jutsu ha sido actualizado correctamente.');
    } catch (error) {
      showError('Error', 'No se pudo actualizar el jutsu. Inténtalo de nuevo.');
    }
  };

  const closeModal = () => {
    setShowDetailsModal(false);
    setShowEditModal(false);
    setShowCreateModal(false);
    setSelectedJutsu(null);
  };

  const filteredJutsus = jutsus.filter(jutsu => {
    const matchesSearch = jutsu.nombre.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         (jutsu.descripcion && jutsu.descripcion.toLowerCase().includes(searchTerm.toLowerCase()));
    const matchesTipo = !filterTipo || jutsu.tipo === filterTipo;
    return matchesSearch && matchesTipo;
  });

  const getTipoColor = (tipo) => {
    const colors = {
      'Ninjutsu': 'bg-blue-100 text-blue-800',
      'Genjutsu': 'bg-purple-100 text-purple-800',
      'Taijutsu': 'bg-green-100 text-green-800',
      'Kekkei Genkai': 'bg-red-100 text-red-800',
      'Fuinjutsu': 'bg-yellow-100 text-yellow-800',
    };
    return colors[tipo] || 'bg-gray-100 text-gray-800';
  };

  const getNivelStars = (nivel) => {
    return Array.from({ length: 10 }, (_, i) => (
      <Star
        key={i}
        className={`h-3 w-3 ${i < nivel ? 'text-yellow-400 fill-current' : 'text-gray-300'}`}
      />
    ));
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <LoadingSpinner size="lg" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="text-center py-12">
        <div className="text-red-600 mb-4">Error al cargar los jutsus</div>
        <p className="text-gray-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Jutsus</h1>
          <p className="text-gray-600 mt-2">Gestiona las técnicas ninja</p>
        </div>
        <button 
          onClick={handleCreate}
          className="btn-primary mt-4 sm:mt-0 inline-flex items-center"
        >
          <Plus className="h-4 w-4 mr-2" />
          Nuevo Jutsu
        </button>
      </div>

      {/* Filtros */}
      <div className="card">
        <div className="flex flex-col sm:flex-row gap-4">
          <div className="flex-1">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
              <input
                type="text"
                placeholder="Buscar por nombre o descripción..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="input-field pl-10"
              />
            </div>
          </div>
          <div className="sm:w-48">
            <select
              value={filterTipo}
              onChange={(e) => setFilterTipo(e.target.value)}
              className="input-field"
            >
              <option value="">Todos los tipos</option>
              {tipos.map(tipo => (
                <option key={tipo} value={tipo}>{tipo}</option>
              ))}
            </select>
          </div>
        </div>
      </div>

      {/* Grid de Jutsus */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredJutsus.map((jutsu) => (
          <div key={jutsu.id} className="card hover:shadow-lg transition-shadow">
            <div className="flex items-start justify-between mb-4">
              <div className="flex items-center space-x-3">
                <div className="p-2 bg-yellow-100 rounded-lg">
                  <Zap className="h-6 w-6 text-yellow-600" />
                </div>
                <div className="flex-1">
                  <h3 className="text-lg font-semibold text-gray-900">{jutsu.nombre}</h3>
                  <span className={`inline-block px-2 py-1 text-xs font-medium rounded-full ${getTipoColor(jutsu.tipo)}`}>
                    {jutsu.tipo}
                  </span>
                </div>
              </div>
              <div className="flex items-center space-x-2">
                <button 
                  onClick={() => handleViewDetails(jutsu)}
                  className="p-1 text-secondary-600 hover:text-secondary-900 transition-colors"
                  title="Ver detalles"
                >
                  <Eye className="h-4 w-4" />
                </button>
                <button 
                  onClick={() => handleEdit(jutsu)}
                  className="p-1 text-primary-600 hover:text-primary-900 transition-colors"
                  title="Editar"
                >
                  <Edit className="h-4 w-4" />
                </button>
                <button 
                  onClick={() => handleDelete(jutsu.id, jutsu.nombre)}
                  className="p-1 text-red-600 hover:text-red-900 transition-colors"
                  title="Eliminar"
                >
                  <Trash2 className="h-4 w-4" />
                </button>
              </div>
            </div>

            {/* Nivel */}
            <div className="mb-3">
              <div className="flex items-center justify-between mb-1">
                <span className="text-sm font-medium text-gray-700">Nivel</span>
                <span className="text-sm font-bold text-primary-600">{jutsu.nivel}/10</span>
              </div>
              <div className="flex space-x-1">
                {getNivelStars(jutsu.nivel)}
              </div>
            </div>

            {jutsu.descripcion && (
              <p className="text-gray-600 text-sm mb-4 line-clamp-3">
                {jutsu.descripcion}
              </p>
            )}

            <div className="flex items-center justify-between pt-4 border-t border-gray-200">
              <div className="text-sm text-gray-600">
                <span className="font-medium">{jutsu.usuarioCount || 0}</span> usuarios
              </div>
              <button 
                onClick={() => handleViewDetails(jutsu)}
                className="text-yellow-600 hover:text-yellow-800 text-sm font-medium transition-colors"
              >
                Ver detalles
              </button>
            </div>
          </div>
        ))}
      </div>

      {filteredJutsus.length === 0 && (
        <div className="text-center py-12">
          <Zap className="mx-auto h-12 w-12 text-gray-400 mb-4" />
          <p className="text-gray-500">No se encontraron jutsus</p>
        </div>
      )}

      {/* Stats */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div className="card text-center">
          <div className="text-2xl font-bold text-yellow-600">{jutsus.length}</div>
          <div className="text-sm text-gray-500">Total Jutsus</div>
        </div>
        <div className="card text-center">
          <div className="text-2xl font-bold text-primary-600">{tipos.length}</div>
          <div className="text-sm text-gray-500">Tipos Diferentes</div>
        </div>
        <div className="card text-center">
          <div className="text-2xl font-bold text-green-600">
            {jutsus.length > 0 ? 
              (jutsus.reduce((sum, jutsu) => sum + (jutsu.nivel || 0), 0) / jutsus.length).toFixed(1) : 
              '0.0'
            }
          </div>
          <div className="text-sm text-gray-500">Nivel Promedio</div>
        </div>
        <div className="card text-center">
          <div className="text-2xl font-bold text-secondary-600">
            {jutsus.reduce((sum, jutsu) => sum + (jutsu.usuarioCount || 0), 0)}
          </div>
          <div className="text-sm text-gray-500">Total Usuarios</div>
        </div>
      </div>

      {/* Modal de Detalles */}
      <Modal
        isOpen={showDetailsModal}
        onClose={closeModal}
        title={`Detalles del Jutsu: ${selectedJutsu?.nombre || ''}`}
        size="md"
      >
        {selectedJutsu && (
          <div className="space-y-6">
            {/* Información Principal */}
            <div className="card">
              <div className="flex items-center space-x-4 mb-4">
                <div className="p-3 bg-yellow-100 rounded-xl">
                  <Zap className="h-8 w-8 text-yellow-600" />
                </div>
                <div>
                  <h3 className="text-xl font-bold text-gray-900">{selectedJutsu.nombre}</h3>
                  <span className={`inline-block px-3 py-1 text-sm font-medium rounded-full ${getTipoColor(selectedJutsu.tipo)}`}>
                    {selectedJutsu.tipo}
                  </span>
                </div>
              </div>

              {/* Nivel */}
              <div className="mb-4">
                <div className="flex items-center justify-between mb-2">
                  <span className="text-sm font-medium text-gray-700">Nivel de Dificultad</span>
                  <span className="text-lg font-bold text-primary-600">{selectedJutsu.nivel}/10</span>
                </div>
                <div className="flex space-x-1 mb-2">
                  {getNivelStars(selectedJutsu.nivel)}
                </div>
                <div className="w-full bg-gray-200 rounded-full h-2">
                  <div 
                    className="bg-gradient-to-r from-yellow-400 to-yellow-600 h-2 rounded-full transition-all duration-500"
                    style={{ width: `${(selectedJutsu.nivel / 10) * 100}%` }}
                  ></div>
                </div>
              </div>

              {/* Descripción */}
              {selectedJutsu.descripcion && (
                <div className="mb-4">
                  <h4 className="text-sm font-medium text-gray-700 mb-2">Descripción</h4>
                  <p className="text-gray-600 leading-relaxed">{selectedJutsu.descripcion}</p>
                </div>
              )}

              {/* Estadísticas */}
              <div className="grid grid-cols-2 gap-4 pt-4 border-t border-gray-200">
                <div className="text-center p-3 bg-blue-50 rounded-xl">
                  <div className="text-2xl font-bold text-blue-600">{selectedJutsu.usuarioCount || 0}</div>
                  <div className="text-sm text-blue-700 font-medium">Usuarios</div>
                </div>
                <div className="text-center p-3 bg-purple-50 rounded-xl">
                  <div className="text-2xl font-bold text-purple-600">{selectedJutsu.nivel}</div>
                  <div className="text-sm text-purple-700 font-medium">Nivel</div>
                </div>
              </div>
            </div>

            {/* Información Adicional */}
            <div className="card">
              <h4 className="text-lg font-semibold text-gray-900 mb-4">Información Técnica</h4>
              <div className="space-y-3">
                <div className="flex justify-between">
                  <span className="text-sm font-medium text-gray-500">Tipo de Jutsu:</span>
                  <span className="text-gray-900 font-semibold">{selectedJutsu.tipo}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-sm font-medium text-gray-500">Dificultad:</span>
                  <span className="text-gray-900 font-semibold">
                    {selectedJutsu.nivel <= 3 ? 'Básico' : 
                     selectedJutsu.nivel <= 6 ? 'Intermedio' : 
                     selectedJutsu.nivel <= 8 ? 'Avanzado' : 'Maestro'}
                  </span>
                </div>
                <div className="flex justify-between">
                  <span className="text-sm font-medium text-gray-500">Popularidad:</span>
                  <span className="text-gray-900 font-semibold">
                    {(selectedJutsu.usuarioCount || 0) > 10 ? 'Alta' : 
                     (selectedJutsu.usuarioCount || 0) > 5 ? 'Media' : 'Baja'}
                  </span>
                </div>
              </div>
            </div>
          </div>
        )}
      </Modal>

      {/* Modal de Crear */}
      <Modal
        isOpen={showCreateModal}
        onClose={closeModal}
        title="Crear Nuevo Jutsu"
        size="md"
      >
        <JutsuForm
          onSubmit={handleSubmitCreate}
          onCancel={closeModal}
        />
      </Modal>

      {/* Modal de Editar */}
      <Modal
        isOpen={showEditModal}
        onClose={closeModal}
        title="Editar Jutsu"
        size="md"
      >
        <JutsuForm
          jutsu={selectedJutsu}
          onSubmit={handleSubmitEdit}
          onCancel={closeModal}
        />
      </Modal>

      {/* Contenedor de Alertas */}
      <AlertContainer alerts={alerts} onRemoveAlert={removeAlert} />
    </div>
  );
};

export default Jutsus;