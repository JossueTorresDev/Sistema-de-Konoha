import Alert from './Alert';

const AlertContainer = ({ alerts, onRemoveAlert }) => {
  return (
    <div className="fixed top-4 right-4 z-50 space-y-2">
      {alerts.map((alert) => (
        <Alert
          key={alert.id}
          type={alert.type}
          title={alert.title}
          message={alert.message}
          onClose={() => onRemoveAlert(alert.id)}
          autoClose={alert.autoClose}
          duration={alert.duration}
          actions={alert.actions}
        />
      ))}
    </div>
  );
};

export default AlertContainer;