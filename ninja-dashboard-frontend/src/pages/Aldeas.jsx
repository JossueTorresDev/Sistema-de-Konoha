import { useState, useEffect } from 'react';
import { Plus, Search, Edit, Trash2, Users, MapPin, Eye, Award, TrendingUp } from 'lucide-react';
import { aldeasAPI } from '../services/api';
import { useAlert } from '../hooks/useAlert';
import LoadingSpinner from '../components/LoadingSpinner';
import AlertContainer from '../components/AlertContainer';
import Modal from '../components/Modal';

const Aldeas = () => {
  const [aldeas, setAldeas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedAldea, setSelectedAldea] = useState(null);
  const [showDetailsModal, setShowDetailsModal] = useState(false);
  const { alerts, removeAlert, showSuccess, showError, showConfirm, showInfo } = useAlert();

  useEffect(() => {
    fetchAldeas();
  }, []);

  const fetchAldeas = async () => {
    try {
      setLoading(true);
      const response = await aldeasAPI.getStats();
      setAldeas(response.data);
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
      `¿Estás seguro de que quieres eliminar la aldea ${nombre}? Esta acción no se puede deshacer.`,
      async () => {
        try {
          await aldeasAPI.delete(id);
          await fetchAldeas();
          showSuccess('¡Eliminado!', `La aldea ${nombre} ha sido eliminada correctamente.`);
        } catch (error) {
          showError('Error', 'No se pudo eliminar la aldea. Inténtalo de nuevo.');
        }
      },
      () => {
        // No hacer nada al cancelar
      }
    );
  };

  const handleViewDetails = (aldea) => {
    setSelectedAldea(aldea);
    setShowDetailsModal(true);
  };

  const handleEdit = (aldea) => {
    showInfo('Función en desarrollo', 'La funcionalidad de edición estará disponible próximamente.');
  };

  const closeModal = () => {
    setShowDetailsModal(false);
    setSelectedAldea(null);
  };

  const filteredAldeas = aldeas.filter(aldea =>
    aldea.nombre.toLowerCase().includes(searchTerm.toLowerCase()) ||
    (aldea.region && aldea.region.toLowerCase().includes(searchTerm.toLowerCase()))
  );

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
        <div className="text-red-600 mb-4">Error al cargar las aldeas</div>
        <p className="text-gray-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Aldeas</h1>
          <p className="text-gray-600 mt-2">Gestiona las aldeas ninja</p>
        </div>
        <button className="btn-primary mt-4 sm:mt-0 inline-flex items-center">
          <Plus className="h-4 w-4 mr-2" />
          Nueva Aldea
        </button>
      </div>

      {/* Filtros */}
      <div className="card">
        <div className="relative">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
          <input
            type="text"
            placeholder="Buscar por nombre o región..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="input-field pl-10"
          />
        </div>
      </div>

      {/* Grid de Aldeas */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredAldeas.map((aldea) => (
          <div key={aldea.id} className="card hover:shadow-lg transition-shadow">
            <div className="flex items-start justify-between mb-4">
              <div className="flex items-center space-x-3">
                <div className="p-2 bg-secondary-100 rounded-lg">
                  <MapPin className="h-6 w-6 text-secondary-600" />
                </div>
                <div>
                  <h3 className="text-lg font-semibold text-gray-900">{aldea.nombre}</h3>
                  <p className="text-sm text-gray-500">{aldea.region || 'Región desconocida'}</p>
                </div>
              </div>
              <div className="flex items-center space-x-2">
                <button 
                  onClick={() => handleViewDetails(aldea)}
                  className="p-1 text-secondary-600 hover:text-secondary-900 transition-colors"
                  title="Ver detalles"
                >
                  <Eye className="h-4 w-4" />
                </button>
                <button 
                  onClick={() => handleEdit(aldea)}
                  className="p-1 text-primary-600 hover:text-primary-900 transition-colors"
                  title="Editar"
                >
                  <Edit className="h-4 w-4" />
                </button>
                <button 
                  onClick={() => handleDelete(aldea.id, aldea.nombre)}
                  className="p-1 text-red-600 hover:text-red-900 transition-colors"
                  title="Eliminar"
                >
                  <Trash2 className="h-4 w-4" />
                </button>
              </div>
            </div>

            {aldea.descripcion && (
              <p className="text-gray-600 text-sm mb-4 line-clamp-3">
                {aldea.descripcion}
              </p>
            )}

            <div className="flex items-center justify-between pt-4 border-t border-gray-200">
              <div className="flex items-center space-x-2">
                <Users className="h-4 w-4 text-gray-400" />
                <span className="text-sm text-gray-600">
                  {aldea.personajeCount || 0} ninjas
                </span>
              </div>
              <button 
                onClick={() => handleViewDetails(aldea)}
                className="text-secondary-600 hover:text-secondary-800 text-sm font-medium transition-colors"
              >
                Ver detalles
              </button>
            </div>
          </div>
        ))}
      </div>

      {filteredAldeas.length === 0 && (
        <div className="text-center py-12">
          <MapPin className="mx-auto h-12 w-12 text-gray-400 mb-4" />
          <p className="text-gray-500">No se encontraron aldeas</p>
        </div>
      )}

      {/* Stats */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="card text-center">
          <div className="text-2xl font-bold text-secondary-600">{aldeas.length}</div>
          <div className="text-sm text-gray-500">Total Aldeas</div>
        </div>
        <div className="card text-center">
          <div className="text-2xl font-bold text-primary-600">
            {aldeas.reduce((sum, aldea) => sum + (aldea.personajeCount || 0), 0)}
          </div>
          <div className="text-sm text-gray-500">Total Ninjas</div>
        </div>
        <div className="card text-center">
          <div className="text-2xl font-bold text-green-600">
            {aldeas.length > 0 ? 
              (aldeas.reduce((sum, aldea) => sum + (aldea.personajeCount || 0), 0) / aldeas.length).toFixed(1) : 
              '0.0'
            }
          </div>
          <div className="text-sm text-gray-500">Promedio por Aldea</div>
        </div>
      </div>

      {/* Modal de Detalles */}
      <Modal
        isOpen={showDetailsModal}
        onClose={closeModal}
        title={`Detalles de ${selectedAldea?.nombre || ''}`}
        size="md"
      >
        {selectedAldea && (
          <div className="space-y-6">
            {/* Información Principal */}
            <div className="card">
              <div className="flex items-center space-x-4 mb-4">
                <div className="p-3 bg-secondary-100 rounded-xl">
                  <MapPin className="h-8 w-8 text-secondary-600" />
                </div>
                <div>
                  <h3 className="text-xl font-bold text-gray-900">{selectedAldea.nombre}</h3>
                  <p className="text-gray-500">{selectedAldea.region || 'Región desconocida'}</p>
                </div>
              </div>

              {/* Descripción */}
              {selectedAldea.descripcion && (
                <div className="mb-4">
                  <h4 className="text-sm font-medium text-gray-700 mb-2">Descripción</h4>
                  <p className="text-gray-600 leading-relaxed">{selectedAldea.descripcion}</p>
                </div>
              )}

              {/* Estadísticas */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4 pt-4 border-t border-gray-200">
                <div className="text-center p-4 bg-blue-50 rounded-xl">
                  <Users className="h-8 w-8 text-blue-600 mx-auto mb-2" />
                  <div className="text-2xl font-bold text-blue-600">{selectedAldea.personajeCount || 0}</div>
                  <div className="text-sm text-blue-700 font-medium">Ninjas Activos</div>
                </div>
                <div className="text-center p-4 bg-green-50 rounded-xl">
                  <Award className="h-8 w-8 text-green-600 mx-auto mb-2" />
                  <div className="text-2xl font-bold text-green-600">
                    {selectedAldea.personajeCount > 20 ? 'Grande' : 
                     selectedAldea.personajeCount > 10 ? 'Mediana' : 'Pequeña'}
                  </div>
                  <div className="text-sm text-green-700 font-medium">Tamaño</div>
                </div>
              </div>
            </div>

            {/* Información Adicional */}
            <div className="card">
              <h4 className="text-lg font-semibold text-gray-900 mb-4">Información Adicional</h4>
              <div className="space-y-3">
                <div className="flex justify-between">
                  <span className="text-sm font-medium text-gray-500">Región:</span>
                  <span className="text-gray-900 font-semibold">{selectedAldea.region || 'No especificada'}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-sm font-medium text-gray-500">Población Ninja:</span>
                  <span className="text-gray-900 font-semibold">{selectedAldea.personajeCount || 0} ninjas</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-sm font-medium text-gray-500">Estado:</span>
                  <span className="text-green-600 font-semibold">Activa</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-sm font-medium text-gray-500">Nivel de Actividad:</span>
                  <span className="text-gray-900 font-semibold">
                    {(selectedAldea.personajeCount || 0) > 15 ? 'Alto' : 
                     (selectedAldea.personajeCount || 0) > 5 ? 'Medio' : 'Bajo'}
                  </span>
                </div>
              </div>
            </div>

            {/* Ranking */}
            <div className="card">
              <h4 className="text-lg font-semibold text-gray-900 mb-4">Ranking y Prestigio</h4>
              <div className="flex items-center justify-center space-x-4">
                <div className="text-center">
                  <TrendingUp className="h-8 w-8 text-primary-600 mx-auto mb-2" />
                  <div className="text-lg font-bold text-primary-600">
                    #{Math.floor(Math.random() * 10) + 1}
                  </div>
                  <div className="text-sm text-gray-500">Ranking General</div>
                </div>
                <div className="text-center">
                  <Award className="h-8 w-8 text-yellow-600 mx-auto mb-2" />
                  <div className="text-lg font-bold text-yellow-600">
                    {Math.floor(Math.random() * 5) + 1}★
                  </div>
                  <div className="text-sm text-gray-500">Prestigio</div>
                </div>
              </div>
            </div>
          </div>
        )}
      </Modal>

      {/* Contenedor de Alertas */}
      <AlertContainer alerts={alerts} onRemoveAlert={removeAlert} />
    </div>
  );
};

export default Aldeas;