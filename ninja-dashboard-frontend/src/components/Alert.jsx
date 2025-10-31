import { useState, useEffect } from 'react';
import { CheckCircle, XCircle, AlertTriangle, Info, X } from 'lucide-react';

const Alert = ({ type = 'info', title, message, onClose, autoClose = true, duration = 5000, actions }) => {
  const [isVisible, setIsVisible] = useState(true);

  useEffect(() => {
    if (autoClose && !actions) {
      const timer = setTimeout(() => {
        setIsVisible(false);
        setTimeout(() => onClose?.(), 300);
      }, duration);
      return () => clearTimeout(timer);
    }
  }, [autoClose, duration, onClose, actions]);

  const handleClose = () => {
    setIsVisible(false);
    setTimeout(() => onClose?.(), 300);
  };

  const getAlertStyles = () => {
    const styles = {
      success: {
        bg: 'bg-gradient-to-r from-green-50 to-emerald-50',
        border: 'border-green-200',
        icon: CheckCircle,
        iconColor: 'text-green-600',
        titleColor: 'text-green-800',
        messageColor: 'text-green-700'
      },
      error: {
        bg: 'bg-gradient-to-r from-red-50 to-rose-50',
        border: 'border-red-200',
        icon: XCircle,
        iconColor: 'text-red-600',
        titleColor: 'text-red-800',
        messageColor: 'text-red-700'
      },
      warning: {
        bg: 'bg-gradient-to-r from-yellow-50 to-amber-50',
        border: 'border-yellow-200',
        icon: AlertTriangle,
        iconColor: 'text-yellow-600',
        titleColor: 'text-yellow-800',
        messageColor: 'text-yellow-700'
      },
      info: {
        bg: 'bg-gradient-to-r from-blue-50 to-indigo-50',
        border: 'border-blue-200',
        icon: Info,
        iconColor: 'text-blue-600',
        titleColor: 'text-blue-800',
        messageColor: 'text-blue-700'
      }
    };
    return styles[type] || styles.info;
  };

  const alertStyle = getAlertStyles();
  const IconComponent = alertStyle.icon;

  return (
    <div className={`fixed top-4 right-4 z-50 transition-all duration-300 transform ${
      isVisible ? 'translate-x-0 opacity-100' : 'translate-x-full opacity-0'
    }`}>
      <div className={`${alertStyle.bg} ${alertStyle.border} border rounded-2xl shadow-xl backdrop-blur-sm p-4 max-w-md`}>
        <div className="flex items-start space-x-3">
          <IconComponent className={`h-6 w-6 ${alertStyle.iconColor} flex-shrink-0 mt-0.5`} />
          <div className="flex-1 min-w-0">
            {title && (
              <h4 className={`text-sm font-semibold ${alertStyle.titleColor} mb-1`}>
                {title}
              </h4>
            )}
            <p className={`text-sm ${alertStyle.messageColor} mb-3`}>
              {message}
            </p>
            
            {/* Botones de acción */}
            {actions && actions.length > 0 && (
              <div className="flex space-x-2 mt-3">
                {actions.map((action, index) => (
                  <button
                    key={index}
                    onClick={() => {
                      action.onClick?.();
                      handleClose();
                    }}
                    className={`px-3 py-1.5 text-xs font-medium rounded-lg transition-colors ${
                      action.variant === 'danger' 
                        ? 'bg-red-600 hover:bg-red-700 text-white' 
                        : action.variant === 'primary'
                        ? 'bg-primary-600 hover:bg-primary-700 text-white'
                        : 'bg-gray-200 hover:bg-gray-300 text-gray-700'
                    }`}
                  >
                    {action.label}
                  </button>
                ))}
              </div>
            )}
          </div>
          {!actions && (
            <button
              onClick={handleClose}
              className={`${alertStyle.iconColor} hover:opacity-70 transition-opacity flex-shrink-0`}
            >
              <X className="h-5 w-5" />
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default Alert;