import { TrendingUp, TrendingDown } from 'lucide-react';

const StatCard = ({ title, value, icon: Icon, color = 'primary', change = null, trend = null }) => {
  const colorClasses = {
    primary: 'from-primary-500 to-primary-600',
    secondary: 'from-secondary-500 to-secondary-600',
    green: 'from-green-500 to-green-600',
    red: 'from-red-500 to-red-600',
    yellow: 'from-yellow-500 to-yellow-600',
    purple: 'from-purple-500 to-purple-600',
  };

  const bgClasses = {
    primary: 'from-primary-50 to-primary-100',
    secondary: 'from-secondary-50 to-secondary-100',
    green: 'from-green-50 to-green-100',
    red: 'from-red-50 to-red-100',
    yellow: 'from-yellow-50 to-yellow-100',
    purple: 'from-purple-50 to-purple-100',
  };

  return (
    <div className={`stat-card bg-gradient-to-br ${bgClasses[color]} relative overflow-hidden`}>
      {/* Background pattern */}
      <div className="absolute top-0 right-0 -mt-4 -mr-4 opacity-10">
        <Icon className="h-24 w-24 text-gray-400" />
      </div>
      
      <div className="relative">
        <div className="flex items-start justify-between">
          <div className="flex-1">
            <p className="text-sm font-semibold text-gray-600 uppercase tracking-wide mb-2">{title}</p>
            <p className="text-3xl font-bold text-gray-900 mb-1">{value}</p>
            
            {change && (
              <div className="flex items-center space-x-1">
                {change.positive ? (
                  <TrendingUp className="h-4 w-4 text-green-500" />
                ) : (
                  <TrendingDown className="h-4 w-4 text-red-500" />
                )}
                <span className={`text-sm font-medium ${change.positive ? 'text-green-600' : 'text-red-600'}`}>
                  {change.positive ? '+' : ''}{change.value}%
                </span>
                <span className="text-xs text-gray-500">vs último mes</span>
              </div>
            )}
          </div>
          
          <div className={`p-4 rounded-2xl bg-gradient-to-br ${colorClasses[color]} shadow-lg`}>
            <Icon className="h-8 w-8 text-white" />
          </div>
        </div>
        
        {trend && (
          <div className="mt-4 pt-4 border-t border-white/50">
            <div className="flex items-center justify-between text-xs text-gray-600">
              <span>Tendencia</span>
              <span className="font-medium">{trend}</span>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default StatCard;