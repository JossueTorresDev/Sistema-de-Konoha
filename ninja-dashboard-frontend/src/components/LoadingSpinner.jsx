const LoadingSpinner = ({ size = 'md', className = '', text = '' }) => {
  const sizeClasses = {
    sm: 'h-4 w-4',
    md: 'h-8 w-8',
    lg: 'h-12 w-12',
    xl: 'h-16 w-16'
  };

  return (
    <div className={`flex flex-col justify-center items-center ${className}`}>
      <div className="relative">
        <div className={`animate-spin rounded-full border-4 border-gray-200 ${sizeClasses[size]}`}></div>
        <div className={`animate-spin rounded-full border-4 border-transparent border-t-primary-600 border-r-primary-600 absolute top-0 left-0 ${sizeClasses[size]}`}></div>
      </div>
      {text && (
        <p className="mt-3 text-sm text-gray-600 font-medium animate-pulse">{text}</p>
      )}
    </div>
  );
};

export default LoadingSpinner;