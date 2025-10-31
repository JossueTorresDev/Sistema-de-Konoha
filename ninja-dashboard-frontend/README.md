# Ninja Dashboard Frontend

Frontend de React + Tailwind CSS para el dashboard de ninjas.

## 🚀 Tecnologías

- **React 19** - Framework de UI
- **Vite** - Build tool y dev server
- **Tailwind CSS** - Framework de CSS
- **React Router** - Navegación
- **Axios** - Cliente HTTP
- **Lucide React** - Iconos

## 📦 Instalación

```bash
npm install
```

## 🛠️ Desarrollo

```bash
npm run dev
```

La aplicación estará disponible en `http://localhost:5173`

## 🏗️ Build

```bash
npm run build
```

## 📁 Estructura del Proyecto

```
src/
├── components/          # Componentes reutilizables
│   ├── Layout.jsx      # Layout principal con sidebar
│   ├── LoadingSpinner.jsx
│   └── StatCard.jsx    # Tarjeta de estadísticas
├── pages/              # Páginas principales
│   ├── Dashboard.jsx   # Dashboard principal
│   ├── Personajes.jsx  # Gestión de personajes
│   ├── Aldeas.jsx      # Gestión de aldeas
│   └── Jutsus.jsx      # Gestión de jutsus
├── services/           # Servicios API
│   └── api.js          # Cliente API con Axios
├── hooks/              # Custom hooks
│   ├── useDashboard.js
│   └── usePersonajes.js
└── utils/              # Utilidades
```

## 🎨 Características

- **Responsive Design** - Funciona en móvil y desktop
- **Dashboard Interactivo** - Estadísticas en tiempo real
- **CRUD Completo** - Crear, leer, actualizar y eliminar
- **Búsqueda y Filtros** - Filtrado avanzado de datos
- **Loading States** - Estados de carga elegantes
- **Error Handling** - Manejo de errores robusto

## 🔗 API Backend

El frontend se conecta al backend Spring Boot en `http://localhost:5000/api`

### Endpoints principales:
- `/api/dashboard/stats` - Estadísticas del dashboard
- `/api/personajes` - CRUD de personajes
- `/api/aldeas` - CRUD de aldeas
- `/api/jutsus` - CRUD de jutsus

## 🎯 Funcionalidades

### Dashboard
- Estadísticas generales
- Top personajes por power level
- Aldeas por población
- Jutsus más populares

### Personajes
- Lista completa con filtros
- Búsqueda por nombre/alias
- Filtro por rango
- Visualización de stats y power level

### Aldeas
- Grid de aldeas con información
- Contador de ninjas por aldea
- Gestión CRUD completa

### Jutsus
- Catálogo de técnicas ninja
- Filtros por tipo
- Sistema de niveles con estrellas
- Estadísticas de uso