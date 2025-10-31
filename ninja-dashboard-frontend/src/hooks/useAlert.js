import { useState, useCallback } from 'react';

export const useAlert = () => {
  const [alerts, setAlerts] = useState([]);

  const showAlert = useCallback((type, title, message, options = {}) => {
    const id = Date.now() + Math.random();
    const alert = {
      id,
      type,
      title,
      message,
      ...options
    };

    setAlerts(prev => [...prev, alert]);

    // Auto remove after duration
    const duration = options.duration || 5000;
    if (options.autoClose !== false) {
      setTimeout(() => {
        removeAlert(id);
      }, duration);
    }

    return id;
  }, []);

  const removeAlert = useCallback((id) => {
    setAlerts(prev => prev.filter(alert => alert.id !== id));
  }, []);

  const showSuccess = useCallback((title, message, options) => {
    return showAlert('success', title, message, options);
  }, [showAlert]);

  const showError = useCallback((title, message, options) => {
    return showAlert('error', title, message, options);
  }, [showAlert]);

  const showWarning = useCallback((title, message, options) => {
    return showAlert('warning', title, message, options);
  }, [showAlert]);

  const showConfirm = useCallback((title, message, onConfirm, onCancel) => {
    const actions = [
      {
        label: 'Cancelar',
        onClick: onCancel,
        variant: 'secondary'
      },
      {
        label: 'Confirmar',
        onClick: onConfirm,
        variant: 'danger'
      }
    ];
    
    return showAlert('warning', title, message, {
      autoClose: false,
      actions
    });
  }, [showAlert]);

  const showInfo = useCallback((title, message, options) => {
    return showAlert('info', title, message, options);
  }, [showAlert]);

  return {
    alerts,
    showAlert,
    removeAlert,
    showSuccess,
    showError,
    showWarning,
    showInfo,
    showConfirm
  };
};