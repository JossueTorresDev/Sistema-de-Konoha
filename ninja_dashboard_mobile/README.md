# 📱 Ninja Dashboard Mobile

Aplicación móvil Flutter para el **Shinobi Dashboard** - Gestión del mundo ninja en tu dispositivo móvil.

## 🚀 Características

- **Dashboard interactivo** con estadísticas en tiempo real
- **Gestión de personajes** con filtros y búsqueda
- **Catálogo de aldeas** y sus características
- **Base de datos de jutsus** con clasificaciones
- **Diseño responsivo** optimizado para móviles
- **Tema ninja** inspirado en el universo de Naruto
- **Integración con API REST** del backend Spring Boot

## 🛠️ Tecnologías

- **Flutter 3.10+** - Framework multiplataforma
- **Dart 3.0+** - Lenguaje de programación
- **Provider** - Gestión de estado
- **Go Router** - Navegación declarativa
- **HTTP/Dio** - Cliente para API REST
- **Material Design 3** - Sistema de diseño

## 📦 Instalación

### Prerrequisitos
- Flutter SDK 3.10 o superior
- Dart SDK 3.0 o superior
- Android Studio / VS Code
- Dispositivo Android/iOS o emulador

### Pasos de instalación

1. **Clonar el repositorio**
```bash
git clone <tu-repositorio>
cd ninja_dashboard_mobile
```

2. **Instalar dependencias**
```bash
flutter pub get
```

3. **Verificar configuración**
```bash
flutter doctor
```

4. **Ejecutar la aplicación**
```bash
flutter run
```

## 🏗️ Arquitectura

```
lib/
├── core/                    # Núcleo de la aplicación
│   ├── models/             # Modelos de datos
│   │   ├── personaje.dart
│   │   ├── aldea.dart
│   │   └── jutsu.dart
│   ├── providers/          # Gestión de estado
│   │   ├── dashboard_provider.dart
│   │   ├── personajes_provider.dart
│   │   └── app_providers.dart
│   ├── services/           # Servicios API
│   │   └── api_service.dart
│   ├── router/             # Configuración de rutas
│   │   └── app_router.dart
│   └── theme/              # Tema y estilos
│       └── app_theme.dart
├── presentation/           # Capa de presentación
│   ├── screens/           # Pantallas principales
│   │   ├── main_screen.dart
│   │   ├── dashboard_screen.dart
│   │   ├── personajes_screen.dart
│   │   ├── aldeas_screen.dart
│   │   └── jutsus_screen.dart
│   └── widgets/           # Componentes reutilizables
│       ├── stat_card.dart
│       ├── personaje_card.dart
│       ├── loading_widget.dart
│       └── error_widget.dart
└── main.dart              # Punto de entrada
```

## 🎨 Funcionalidades

### 📊 Dashboard
- Estadísticas generales del mundo ninja
- Top personajes por nivel de poder
- Distribución de aldeas por población
- Jutsus más populares por tipo

### 👥 Personajes
- Lista completa con información detallada
- Búsqueda por nombre y alias
- Filtros por rango ninja (Genin, Chunin, Jonin, Kage, Sannin)
- Visualización de power level con estrellas
- Navegación a pantalla de detalle

### 🏘️ Aldeas
- Grid de aldeas con información básica
- Contador de ninjas por aldea
- Detalles de especialidades y características

### ⚡ Jutsus
- Catálogo completo de técnicas ninja
- Filtros por tipo (Ninjutsu, Genjutsu, Taijutsu)
- Clasificación por elemento y nivel
- Sistema de estrellas para dificultad

## 🔗 Integración con Backend

La aplicación se conecta al backend Spring Boot a través de:

- **Base URL**: `http://localhost:5000/api`
- **Endpoints principales**:
  - `/dashboard/stats` - Estadísticas del dashboard
  - `/personajes` - CRUD de personajes
  - `/aldeas` - CRUD de aldeas
  - `/jutsus` - CRUD de jutsus

## 🎯 Estado del Proyecto

### ✅ Implementado
- Estructura base de la aplicación
- Navegación con bottom navigation
- Dashboard con estadísticas
- Lista de personajes con filtros
- Integración con API REST
- Gestión de estado con Provider
- Tema ninja personalizado

### 🚧 En desarrollo
- Pantalla de detalle de personajes
- Gestión completa de aldeas
- Catálogo de jutsus
- Funcionalidades CRUD completas
- Caché local de datos
- Modo offline

### 🔮 Próximas características
- Autenticación de usuarios
- Favoritos y listas personalizadas
- Notificaciones push
- Modo oscuro avanzado
- Animaciones y transiciones
- Soporte para tablets

## 🚀 Comandos útiles

```bash
# Ejecutar en modo debug
flutter run

# Ejecutar en modo release
flutter run --release

# Generar APK
flutter build apk

# Generar App Bundle
flutter build appbundle

# Ejecutar tests
flutter test

# Analizar código
flutter analyze

# Formatear código
dart format .
```

## 📱 Compatibilidad

- **Android**: API 21+ (Android 5.0)
- **iOS**: iOS 11.0+
- **Resoluciones**: Optimizado para móviles y tablets

## 🎨 Paleta de colores

- **Primario**: `#1A237E` (Azul ninja)
- **Secundario**: `#FF6B35` (Naranja fuego)
- **Acento**: `#4CAF50` (Verde naturaleza)
- **Fondo oscuro**: `#0F172A`
- **Superficie**: `#1E293B`

---

**Desarrollado por:** Jossue Uchiha  
**Proyecto:** Shinobi Dashboard Mobile  
**Tecnología:** Flutter + Dart