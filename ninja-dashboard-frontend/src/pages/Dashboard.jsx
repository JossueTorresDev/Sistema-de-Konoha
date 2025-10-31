import { Users, MapPin, Zap, TrendingUp, Crown, Award, Target, Activity } from 'lucide-react';
import { useDashboard } from '../hooks/useDashboard';
import { useStats } from '../hooks/useStats';
import StatCard from '../components/StatCard';
import LoadingSpinner from '../components/LoadingSpinner';

const Dashboard = () => {
  const { stats: dashboardStats, loading: dashboardLoading, error: dashboardError } = useDashboard();
  const { stats: calculatedStats, loading: statsLoading, error: statsError } = useStats();
  
  const loading = dashboardLoading || statsLoading;
  const error = dashboardError || statsError;
  const stats = dashboardStats;

  if (loading) {
    return (
      <div className="flex justify-center items-center h-96">
        <div className="text-center">
          <LoadingSpinner size="xl" />
          <p className="mt-4 text-gray-600 font-medium">Cargando estadísticas...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="text-center py-16">
        <div className="p-8 bg-red-50 rounded-2xl border border-red-200 max-w-md mx-auto">
          <div className="text-red-600 text-lg font-semibold mb-2">Error al cargar las estadísticas</div>
          <p className="text-red-500">{error}</p>
        </div>
      </div>
    );
  }

  const getRankIcon = (index) => {
    switch (index) {
      case 0: return <Crown className="h-4 w-4" />;
      case 1: return <Award className="h-4 w-4" />;
      case 2: return <Target className="h-4 w-4" />;
      default: return <span className="text-xs font-bold">{index + 1}</span>;
    }
  };

  const getRankColor = (index) => {
    switch (index) {
      case 0: return 'from-yellow-400 to-yellow-600';
      case 1: return 'from-gray-300 to-gray-500';
      case 2: return 'from-orange-400 to-orange-600';
      default: return 'from-gray-200 to-gray-400';
    }
  };

  return (
    <div className="space-y-8">
      {/* Welcome Section */}
      <div className="bg-gradient-to-r from-primary-600 via-primary-700 to-secondary-600 rounded-3xl p-8 text-white shadow-2xl">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-4xl font-bold mb-2">¡Bienvenido al Dashboard Ninja!</h1>
            <p className="text-primary-100 text-lg">Gestiona el mundo ninja desde aquí</p>
          </div>
          <div className="hidden md:block">
            <Activity className="h-16 w-16 text-white/30" />
          </div>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatCard
          title="Total Personajes"
          value={stats?.totalPersonajes || 0}
          icon={Users}
          color="primary"
          change={{ positive: true, value: 12 }}
          trend="↗ Creciendo"
        />
        <StatCard
          title="Total Aldeas"
          value={stats?.totalAldeas || 0}
          icon={MapPin}
          color="secondary"
          change={{ positive: true, value: 5 }}
          trend="→ Estable"
        />
        <StatCard
          title="Total Jutsus"
          value={stats?.totalJutsus || 0}
          icon={Zap}
          color="yellow"
          change={{ positive: true, value: 8 }}
          trend="↗ Aumentando"
        />
        <StatCard
          title="Power Level Promedio"
          value={calculatedStats?.promedioPowerLevel || 0}
          icon={TrendingUp}
          color="green"
          change={{ positive: false, value: 2 }}
          trend="↘ Bajando"
        />
      </div>

      {/* Main Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Top Personajes */}
        <div className="lg:col-span-2">
          <div className="card-hover">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-2xl font-bold text-gray-900">🏆 Top Personajes</h2>
              <span className="badge-primary">Power Level</span>
            </div>
            
            {stats?.topPersonajes && stats.topPersonajes.length > 0 ? (
              <div className="space-y-4">
                {stats.topPersonajes.map((personaje, index) => (
                  <div key={personaje.id} className="group">
                    <div className="flex items-center space-x-4 p-4 rounded-2xl bg-gradient-to-r from-gray-50 to-transparent hover:from-primary-50 hover:to-primary-25 transition-all duration-300 border border-gray-100 hover:border-primary-200">
                      <div className={`w-12 h-12 rounded-2xl bg-gradient-to-br ${getRankColor(index)} flex items-center justify-center text-white shadow-lg`}>
                        {getRankIcon(index)}
                      </div>
                      
                      <div className="flex-1">
                        <div className="flex items-center space-x-2 mb-1">
                          <h3 className="font-bold text-gray-900 text-lg">{personaje.nombre}</h3>
                          {personaje.alias && (
                            <span className="text-sm text-gray-500 italic">"{personaje.alias}"</span>
                          )}
                        </div>
                        <div className="flex items-center space-x-4 text-sm text-gray-600">
                          <span className="flex items-center space-x-1">
                            <MapPin className="h-3 w-3" />
                            <span>{personaje.aldeaNombre}</span>
                          </span>
                          <span className="badge-secondary">{personaje.rango}</span>
                        </div>
                      </div>
                      
                      <div className="text-right">
                        <div className="text-2xl font-bold text-primary-600">
                          {personaje.powerLevel?.toFixed(1)}
                        </div>
                        <div className="text-xs text-gray-500 uppercase tracking-wide">Power Level</div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="text-center py-12">
                <Users className="h-16 w-16 text-gray-300 mx-auto mb-4" />
                <p className="text-gray-500">No hay datos disponibles</p>
              </div>
            )}
          </div>
        </div>

        {/* Aldeas Stats */}
        <div className="space-y-8">
          <div className="card-hover">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-bold text-gray-900">🏘️ Aldeas</h2>
              <span className="badge-secondary">Población</span>
            </div>
            
            {stats?.aldeasStats && stats.aldeasStats.length > 0 ? (
              <div className="space-y-3">
                {stats.aldeasStats.slice(0, 5).map((aldea, index) => (
                  <div key={aldea.id} className="flex items-center justify-between p-3 rounded-xl bg-gradient-to-r from-gray-50 to-transparent hover:from-secondary-50 hover:to-secondary-25 transition-all duration-200 border border-gray-100 hover:border-secondary-200">
                    <div className="flex items-center space-x-3">
                      <div className="w-8 h-8 bg-gradient-to-br from-secondary-400 to-secondary-600 rounded-lg flex items-center justify-center text-white text-xs font-bold">
                        {index + 1}
                      </div>
                      <div>
                        <p className="font-semibold text-gray-900">{aldea.nombre}</p>
                        <p className="text-xs text-gray-500">{aldea.region}</p>
                      </div>
                    </div>
                    <div className="text-right">
                      <p className="font-bold text-secondary-600 text-lg">{aldea.personajeCount}</p>
                      <p className="text-xs text-gray-500">ninjas</p>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="text-center py-8">
                <MapPin className="h-12 w-12 text-gray-300 mx-auto mb-3" />
                <p className="text-gray-500 text-sm">No hay datos disponibles</p>
              </div>
            )}
          </div>
        </div>
      </div>

      {/* Jutsus Populares */}
      <div className="card-hover">
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-2xl font-bold text-gray-900">⚡ Jutsus Más Populares</h2>
          <span className="badge-warning">Trending</span>
        </div>
        
        {stats?.jutsusPopulares && stats.jutsusPopulares.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {stats.jutsusPopulares.map((jutsu, index) => (
              <div key={jutsu.id} className="group">
                <div className="p-6 rounded-2xl bg-gradient-to-br from-yellow-50 to-orange-50 border border-yellow-200 hover:border-yellow-300 transition-all duration-300 hover:shadow-lg transform hover:-translate-y-1">
                  <div className="flex items-start justify-between mb-4">
                    <div className="p-2 bg-gradient-to-br from-yellow-400 to-orange-500 rounded-xl shadow-lg">
                      <Zap className="h-5 w-5 text-white" />
                    </div>
                    <div className="flex items-center space-x-1">
                      {Array.from({ length: jutsu.nivel }, (_, i) => (
                        <div key={i} className="w-1.5 h-1.5 bg-yellow-400 rounded-full"></div>
                      ))}
                    </div>
                  </div>
                  
                  <h3 className="font-bold text-gray-900 text-lg mb-2">{jutsu.nombre}</h3>
                  <p className="text-sm text-gray-600 mb-4">{jutsu.tipo}</p>
                  
                  <div className="flex items-center justify-between">
                    <span className="text-xs text-gray-500 uppercase tracking-wide">Usuarios</span>
                    <span className="font-bold text-yellow-600 text-xl">{jutsu.usuarioCount}</span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <div className="text-center py-12">
            <Zap className="h-16 w-16 text-gray-300 mx-auto mb-4" />
            <p className="text-gray-500">No hay datos disponibles</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default Dashboard;