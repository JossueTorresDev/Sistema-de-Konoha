import { useState, useEffect } from 'react';
import { Plus, Search, Filter, Edit, Trash2, Eye, Users, Award, TrendingUp, MapPin, Star, Zap } from 'lucide-react';
import { usePersonajes } from '../hooks/usePersonajes';
import { useAlert } from '../hooks/useAlert';
import { aldeasAPI } from '../services/api';
import LoadingSpinner from '../components/LoadingSpinner';
import StatCard from '../components/StatCard';
import AlertContainer from '../components/AlertContainer';
import Modal from '../components/Modal';
import PersonajeForm from '../components/PersonajeForm';

const Personajes = () => {
  const { personajes, loading, error, deletePersonaje, createPersonaje, updatePersonaje } = usePersonajes();
  const { alerts, removeAlert, showSuccess, showError, showConfirm, showInfo } = useAlert();
  const [searchTerm, setSearchTerm] = useState('');
  const [filterRango, setFilterRango] = useState('');
  const [selectedPersonaje, setSelectedPersonaje] = useState(null);
  const [showDetailsModal, setShowDetailsModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [aldeas, setAldeas] = useState([]);

  useEffect(() => {
    fetchAldeas();
  }, []);

  const fetchAldeas = async () => {
    try {
      const response = await aldeasAPI.getStats();
      setAldeas(response.data);
    } catch (error) {
      console.error('Error al cargar aldeas:', error);
    }
  };

  const filteredPersonajes = personajes.filter(personaje => {
    const matchesSearch = personaje.nombre.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         (personaje.alias && personaje.alias.toLowerCase().includes(searchTerm.toLowerCase()));
    const matchesRango = !filterRango || personaje.rango === filterRango;
    return matchesSearch && matchesRango;
  });

  const rangos = [...new Set(personajes.map(p => p.rango).filter(Boolean))];

  const handleDelete = async (id, nombre) => {
    showConfirm(
      'Confirmar eliminación',
      `¿Estás seguro de que quieres eliminar a ${nombre}? Esta acción no se puede deshacer.`,
      async () => {
        try {
          await deletePersonaje(id);
          showSuccess('¡Eliminado!', `${nombre} ha sido eliminado correctamente.`);
        } catch (error) {
          showError('Error', 'No se pudo eliminar el personaje. Inténtalo de nuevo.');
        }
      },
      () => {
        // No hacer nada al cancelar
      }
    );
  };

  const handleViewDetails = (personaje) => {
    setSelectedPersonaje(personaje);
    setShowDetailsModal(true);
  };

  const handleEdit = (personaje) => {
    setSelectedPersonaje(personaje);
    setShowEditModal(true);
  };

  const handleCreate = () => {
    setShowCreateModal(true);
  };

  const handleSubmitCreate = async (formData) => {
    try {
      await createPersonaje(formData);
      setShowCreateModal(false);
      showSuccess('¡Creado!', 'El personaje ha sido creado correctamente.');
    } catch (error) {
      showError('Error', 'No se pudo crear el personaje. Inténtalo de nuevo.');
    }
  };

  const handleSubmitEdit = async (formData) => {
    try {
      await updatePersonaje(selectedPersonaje.id, formData);
      setShowEditModal(false);
      setSelectedPersonaje(null);
      showSuccess('¡Actualizado!', 'El personaje ha sido actualizado correctamente.');
    } catch (error) {
      showError('Error', 'No se pudo actualizar el personaje. Inténtalo de nuevo.');
    }
  };

  const closeModals = () => {
    setShowDetailsModal(false);
    setShowEditModal(false);
    setShowCreateModal(false);
    setSelectedPersonaje(null);
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
        <div className="text-red-600 mb-4">Error al cargar los personajes</div>
        <p className="text-gray-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between mb-8">
        <div>
          <h1 className="text-4xl font-bold gradient-text mb-2">Personajes Ninja</h1>
          <p className="text-gray-600 text-lg">Gestiona los guerreros del mundo shinobi</p>
        </div>
        <button 
          onClick={handleCreate}
          className="btn-primary mt-6 sm:mt-0 inline-flex items-center shadow-xl"
        >
          <Plus className="h-5 w-5 mr-2" />
          Nuevo Personaje
        </button>
      </div>

      {/* Filtros */}
      <div className="card-hover mb-8">
        <div className="flex flex-col sm:flex-row gap-6">
          <div className="flex-1">
            <label className="block text-sm font-semibold text-gray-700 mb-2">Buscar Ninja</label>
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
              <input
                type="text"
                placeholder="Buscar por nombre o alias..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="input-field pl-12 text-lg"
              />
            </div>
          </div>
          <div className="sm:w-64">
            <label className="block text-sm font-semibold text-gray-700 mb-2">Filtrar por Rango</label>
            <div className="relative">
              <Filter className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
              <select
                value={filterRango}
                onChange={(e) => setFilterRango(e.target.value)}
                className="input-field pl-12 text-lg"
              >
                <option value="">Todos los rangos</option>
                {rangos.map(rango => (
                  <option key={rango} value={rango}>{rango}</option>
                ))}
              </select>
            </div>
          </div>
        </div>
      </div>

      {/* Lista de Personajes */}
      <div className="card-hover mb-8">
        <div className="overflow-x-auto">
          <table className="min-w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="table-header">Ninja</th>
                <th className="table-header">Rango</th>
                <th className="table-header">Aldea</th>
                <th className="table-header">Power Level</th>
                <th className="table-header">Estadísticas</th>
                <th className="table-header text-right">Acciones</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100">
              {filteredPersonajes.map((personaje) => (
                <tr key={personaje.id} className="table-row">
                  <td className="px-6 py-6">
                    <div className="flex items-center space-x-4">
                      <div className="relative">
                        <div className="h-12 w-12 rounded-2xl bg-gradient-to-br from-primary-400 to-primary-600 flex items-center justify-center shadow-lg">
                          <span className="text-white font-bold text-lg">
                            {personaje.nombre.charAt(0)}
                          </span>
                        </div>
                        <div className="absolute -bottom-1 -right-1 h-4 w-4 bg-green-400 rounded-full border-2 border-white"></div>
                      </div>
                      <div>
                        <div className="font-bold text-gray-900 text-lg">
                          {personaje.nombre}
                        </div>
                        {personaje.alias && (
                          <div className="text-gray-500 italic">
                            "{personaje.alias}"
                          </div>
                        )}
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-6">
                    <span className="badge-secondary text-sm font-semibold">
                      {personaje.rango || 'Sin rango'}
                    </span>
                  </td>
                  <td className="px-6 py-6">
                    <div className="flex items-center space-x-2">
                      <MapPin className="h-4 w-4 text-gray-400" />
                      <span className="text-gray-900 font-medium">
                        {personaje.aldeaNombre || 'Sin aldea'}
                      </span>
                    </div>
                  </td>
                  <td className="px-6 py-6">
                    <div className="flex items-center space-x-3">
                      <div className="text-2xl font-bold text-primary-600">
                        {personaje.powerLevel?.toFixed(1) || '0.0'}
                      </div>
                      <div className="flex-1 bg-gray-200 rounded-full h-2">
                        <div 
                          className="bg-gradient-to-r from-primary-500 to-primary-600 h-2 rounded-full transition-all duration-500"
                          style={{ width: `${Math.min((personaje.powerLevel || 0) / 100 * 100, 100)}%` }}
                        ></div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-6">
                    <div className="grid grid-cols-2 gap-2 text-sm">
                      <div className="flex items-center space-x-1">
                        <span className="text-blue-600 font-medium">C:</span>
                        <span className="font-semibold">{personaje.chakra}</span>
                      </div>
                      <div className="flex items-center space-x-1">
                        <span className="text-purple-600 font-medium">I:</span>
                        <span className="font-semibold">{personaje.inteligencia}</span>
                      </div>
                      <div className="flex items-center space-x-1">
                        <span className="text-red-600 font-medium">F:</span>
                        <span className="font-semibold">{personaje.fuerza}</span>
                      </div>
                      <div className="flex items-center space-x-1">
                        <span className="text-green-600 font-medium">V:</span>
                        <span className="font-semibold">{personaje.velocidad}</span>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-6">
                    <div className="flex items-center justify-end space-x-2">
                      <button 
                        onClick={() => handleViewDetails(personaje)}
                        className="p-2 rounded-xl hover:bg-secondary-50 text-secondary-600 hover:text-secondary-700 transition-colors"
                        title="Ver detalles"
                      >
                        <Eye className="h-5 w-5" />
                      </button>
                      <button 
                        onClick={() => handleEdit(personaje)}
                        className="p-2 rounded-xl hover:bg-primary-50 text-primary-600 hover:text-primary-700 transition-colors"
                        title="Editar"
                      >
                        <Edit className="h-5 w-5" />
                      </button>
                      <button 
                        onClick={() => handleDelete(personaje.id, personaje.nombre)}
                        className="p-2 rounded-xl hover:bg-red-50 text-red-600 hover:text-red-700 transition-colors"
                        title="Eliminar"
                      >
                        <Trash2 className="h-5 w-5" />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          
          {filteredPersonajes.length === 0 && (
            <div className="text-center py-16">
              <Users className="h-16 w-16 text-gray-300 mx-auto mb-4" />
              <h3 className="text-lg font-semibold text-gray-900 mb-2">No se encontraron ninjas</h3>
              <p className="text-gray-500">Intenta ajustar los filtros de búsqueda</p>
            </div>
          )}
        </div>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <StatCard
          title="Total Ninjas"
          value={personajes.length}
          icon={Users}
          color="primary"
          trend="↗ Activos"
        />
        <StatCard
          title="Rangos Únicos"
          value={rangos.length}
          icon={Award}
          color="secondary"
          trend="→ Diversos"
        />
        <StatCard
          title="Power Level Promedio"
          value={personajes.length > 0 ? 
            (personajes.reduce((sum, p) => sum + (p.powerLevel || 0), 0) / personajes.length).toFixed(1) : 
            '0.0'
          }
          icon={TrendingUp}
          color="green"
          trend="↗ Mejorando"
        />
      </div>

      {/* Modal de Detalles */}
      <Modal
        isOpen={showDetailsModal}
        onClose={closeModals}
        title={`Detalles de ${selectedPersonaje?.nombre || ''}`}
        size="lg"
      >
        {selectedPersonaje && (
          <div className="space-y-6">
            {/* Información Principal */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="card">
                <h4 className="text-lg font-semibold text-gray-900 mb-4">Información Personal</h4>
                <div className="space-y-3">
                  <div>
                    <span className="text-sm font-medium text-gray-500">Nombre:</span>
                    <p className="text-gray-900 font-semibold">{selectedPersonaje.nombre}</p>
                  </div>
                  {selectedPersonaje.alias && (
                    <div>
                      <span className="text-sm font-medium text-gray-500">Alias:</span>
                      <p className="text-gray-900 italic">"{selectedPersonaje.alias}"</p>
                    </div>
                  )}
                  <div>
                    <span className="text-sm font-medium text-gray-500">Rango:</span>
                    <span className="badge-secondary ml-2">{selectedPersonaje.rango || 'Sin rango'}</span>
                  </div>
                  <div>
                    <span className="text-sm font-medium text-gray-500">Aldea:</span>
                    <p className="text-gray-900 flex items-center">
                      <MapPin className="h-4 w-4 mr-1" />
                      {selectedPersonaje.aldeaNombre || 'Sin aldea'}
                    </p>
                  </div>
                </div>
              </div>

              <div className="card">
                <h4 className="text-lg font-semibold text-gray-900 mb-4">Power Level</h4>
                <div className="text-center">
                  <div className="text-4xl font-bold text-primary-600 mb-2">
                    {selectedPersonaje.powerLevel?.toFixed(1) || '0.0'}
                  </div>
                  <div className="w-full bg-gray-200 rounded-full h-4 mb-4">
                    <div 
                      className="bg-gradient-to-r from-primary-500 to-primary-600 h-4 rounded-full transition-all duration-500"
                      style={{ width: `${Math.min((selectedPersonaje.powerLevel || 0) / 100 * 100, 100)}%` }}
                    ></div>
                  </div>
                  <p className="text-sm text-gray-500">Nivel de poder general</p>
                </div>
              </div>
            </div>

            {/* Estadísticas */}
            <div className="card">
              <h4 className="text-lg font-semibold text-gray-900 mb-4">Estadísticas de Combate</h4>
              <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                <div className="text-center p-4 bg-blue-50 rounded-xl">
                  <div className="text-2xl font-bold text-blue-600">{selectedPersonaje.chakra}</div>
                  <div className="text-sm text-blue-700 font-medium">Chakra</div>
                </div>
                <div className="text-center p-4 bg-purple-50 rounded-xl">
                  <div className="text-2xl font-bold text-purple-600">{selectedPersonaje.inteligencia}</div>
                  <div className="text-sm text-purple-700 font-medium">Inteligencia</div>
                </div>
                <div className="text-center p-4 bg-red-50 rounded-xl">
                  <div className="text-2xl font-bold text-red-600">{selectedPersonaje.fuerza}</div>
                  <div className="text-sm text-red-700 font-medium">Fuerza</div>
                </div>
                <div className="text-center p-4 bg-green-50 rounded-xl">
                  <div className="text-2xl font-bold text-green-600">{selectedPersonaje.velocidad}</div>
                  <div className="text-sm text-green-700 font-medium">Velocidad</div>
                </div>
              </div>
            </div>

            {/* Jutsus */}
            {selectedPersonaje.jutsus && selectedPersonaje.jutsus.length > 0 && (
              <div className="card">
                <h4 className="text-lg font-semibold text-gray-900 mb-4">Jutsus Conocidos</h4>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                  {selectedPersonaje.jutsus.map((jutsu, index) => (
                    <div key={index} className="flex items-center space-x-3 p-3 bg-yellow-50 rounded-xl">
                      <Zap className="h-5 w-5 text-yellow-600" />
                      <div>
                        <div className="font-medium text-gray-900">{jutsu.nombre}</div>
                        <div className="text-sm text-gray-500">{jutsu.tipo}</div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            )}
          </div>
        )}
      </Modal>

      {/* Modal de Crear */}
      <Modal
        isOpen={showCreateModal}
        onClose={closeModals}
        title="Crear Nuevo Personaje"
        size="lg"
      >
        <PersonajeForm
          onSubmit={handleSubmitCreate}
          onCancel={closeModals}
          aldeas={aldeas}
        />
      </Modal>

      {/* Modal de Editar */}
      <Modal
        isOpen={showEditModal}
        onClose={closeModals}
        title="Editar Personaje"
        size="lg"
      >
        <PersonajeForm
          personaje={selectedPersonaje}
          onSubmit={handleSubmitEdit}
          onCancel={closeModals}
          aldeas={aldeas}
        />
      </Modal>

      {/* Contenedor de Alertas */}
      <AlertContainer alerts={alerts} onRemoveAlert={removeAlert} />
    </div>
  );
};

export default Personajes;