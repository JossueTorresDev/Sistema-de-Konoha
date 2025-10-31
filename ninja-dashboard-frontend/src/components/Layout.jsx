import { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { 
  Home, 
  Users, 
  MapPin, 
  Zap, 
  Menu, 
  X,
  Sword,
  Bell,
  Settings,
  User
} from 'lucide-react';

const Layout = ({ children }) => {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const location = useLocation();

  const navigation = [
    { name: 'Dashboard', href: '/', icon: Home },
    { name: 'Personajes', href: '/personajes', icon: Users },
    { name: 'Aldeas', href: '/aldeas', icon: MapPin },
    { name: 'Jutsus', href: '/jutsus', icon: Zap },
  ];

  const isActive = (href) => {
    return location.pathname === href;
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 via-white to-gray-100">
      {/* Sidebar para móvil */}
      <div className={`fixed inset-0 z-50 lg:hidden ${sidebarOpen ? 'block' : 'hidden'}`}>
        <div className="fixed inset-0 bg-black/50 backdrop-blur-sm" onClick={() => setSidebarOpen(false)} />
        <div className="fixed inset-y-0 left-0 flex w-72 flex-col glass-effect shadow-2xl">
          <div className="flex h-20 items-center justify-between px-6 border-b border-white/20">
            <div className="flex items-center space-x-3">
              <div className="p-2 bg-gradient-to-br from-primary-500 to-primary-600 rounded-xl shadow-lg">
                <Sword className="h-6 w-6 text-white" />
              </div>
              <div>
                <span className="text-xl font-bold gradient-text">Ninja Dashboard</span>
                <p className="text-xs text-gray-500">Admin Panel</p>
              </div>
            </div>
            <button
              onClick={() => setSidebarOpen(false)}
              className="p-2 rounded-lg hover:bg-white/20 transition-colors"
            >
              <X className="h-5 w-5 text-gray-600" />
            </button>
          </div>
          <nav className="flex-1 px-4 py-6 space-y-2">
            {navigation.map((item) => {
              const Icon = item.icon;
              return (
                <Link
                  key={item.name}
                  to={item.href}
                  onClick={() => setSidebarOpen(false)}
                  className={`sidebar-item ${
                    isActive(item.href) ? 'sidebar-item-active' : 'sidebar-item-inactive'
                  }`}
                >
                  <Icon className="h-5 w-5" />
                  <span>{item.name}</span>
                </Link>
              );
            })}
          </nav>
        </div>
      </div>

      {/* Sidebar para desktop */}
      <div className="hidden lg:fixed lg:inset-y-0 lg:flex lg:w-72 lg:flex-col">
        <div className="flex flex-col flex-grow glass-effect border-r border-white/20 shadow-xl">
          <div className="flex h-20 items-center px-6 border-b border-white/20">
            <div className="flex items-center space-x-3">
              <div className="p-2 bg-gradient-to-br from-primary-500 to-primary-600 rounded-xl shadow-lg">
                <Sword className="h-6 w-6 text-white" />
              </div>
              <div>
                <span className="text-xl font-bold gradient-text">Ninja Dashboard</span>
                <p className="text-xs text-gray-500">Admin Panel</p>
              </div>
            </div>
          </div>
          <nav className="flex-1 px-4 py-6 space-y-2">
            {navigation.map((item) => {
              const Icon = item.icon;
              return (
                <Link
                  key={item.name}
                  to={item.href}
                  className={`sidebar-item ${
                    isActive(item.href) ? 'sidebar-item-active' : 'sidebar-item-inactive'
                  }`}
                >
                  <Icon className="h-5 w-5" />
                  <span>{item.name}</span>
                </Link>
              );
            })}
          </nav>
          
          {/* User section */}
          <div className="p-4 border-t border-white/20">
            <div className="flex items-center space-x-3 p-3 rounded-xl hover:bg-white/10 transition-colors cursor-pointer">
              <div className="h-8 w-8 bg-gradient-to-br from-secondary-400 to-secondary-600 rounded-full flex items-center justify-center">
                <User className="h-4 w-4 text-white" />
              </div>
              <div className="flex-1">
                <p className="text-sm font-medium text-gray-900">Admin User</p>
                <p className="text-xs text-gray-500">admin@ninja.com</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Contenido principal */}
      <div className="lg:pl-72">
        {/* Header móvil */}
        <div className="sticky top-0 z-40 flex h-16 items-center justify-between gap-x-4 glass-effect border-b border-white/20 px-4 shadow-lg lg:hidden">
          <div className="flex items-center space-x-3">
            <button
              onClick={() => setSidebarOpen(true)}
              className="p-2 rounded-lg hover:bg-white/20 transition-colors"
            >
              <Menu className="h-5 w-5 text-gray-700" />
            </button>
            <div className="flex items-center space-x-2">
              <div className="p-1.5 bg-gradient-to-br from-primary-500 to-primary-600 rounded-lg">
                <Sword className="h-4 w-4 text-white" />
              </div>
              <span className="text-lg font-semibold gradient-text">Ninja Dashboard</span>
            </div>
          </div>
          
          <div className="flex items-center space-x-2">
            <button className="p-2 rounded-lg hover:bg-white/20 transition-colors">
              <Bell className="h-5 w-5 text-gray-600" />
            </button>
            <button className="p-2 rounded-lg hover:bg-white/20 transition-colors">
              <Settings className="h-5 w-5 text-gray-600" />
            </button>
          </div>
        </div>

        {/* Header desktop */}
        <div className="hidden lg:block sticky top-0 z-30 glass-effect border-b border-white/20 shadow-sm">
          <div className="flex h-16 items-center justify-between px-8">
            <div>
              <h1 className="text-2xl font-bold text-gray-900">
                {navigation.find(item => isActive(item.href))?.name || 'Dashboard'}
              </h1>
            </div>
            
            <div className="flex items-center space-x-4">
              <button className="p-2 rounded-xl hover:bg-white/20 transition-colors relative">
                <Bell className="h-5 w-5 text-gray-600" />
                <span className="absolute -top-1 -right-1 h-3 w-3 bg-red-500 rounded-full"></span>
              </button>
              <button className="p-2 rounded-xl hover:bg-white/20 transition-colors">
                <Settings className="h-5 w-5 text-gray-600" />
              </button>
              <div className="h-8 w-8 bg-gradient-to-br from-secondary-400 to-secondary-600 rounded-full flex items-center justify-center cursor-pointer hover:scale-105 transition-transform">
                <User className="h-4 w-4 text-white" />
              </div>
            </div>
          </div>
        </div>

        {/* Contenido */}
        <main className="p-8">
          <div className="mx-auto max-w-7xl">
            {children}
          </div>
        </main>
      </div>
    </div>
  );
};

export default Layout;