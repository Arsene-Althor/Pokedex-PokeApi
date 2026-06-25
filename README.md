# Pokédex PokeAPI

Un proyecto educativo que demuestra el desarrollo de aplicaciones Android modernas utilizando **Kotlin**, **Jetpack Compose** y la **PokeAPI**.

## 📱 Descripción del Proyecto

Pokédex PokeAPI es una aplicación Android que permite a los usuarios explorar y descubrir información detallada sobre Pokémon. La aplicación consume datos en tiempo real de la [PokeAPI](https://pokeapi.co/), una API pública gratuita que proporciona información completa sobre Pokémon, sus características, habilidades, tipos y más.

Este proyecto fue desarrollado como estudio personal para practicar y dominar las tecnologías modernas de desarrollo Android.

## 🎯 Características Principales

- **Listado de Pokémon**: Visualiza un catálogo completo de Pokémon con carga paginada
- **Búsqueda y Filtrado**: Encuentra Pokémon por nombre o número de Pokédex
- **Detalles del Pokémon**: Información completa incluyendo:
  - Imagen y nombre oficial
  - Tipo(s) de Pokémon
  - Estadísticas base (HP, Ataque, Defensa, etc.)
  - Habilidades y movimientos
  - Altura y peso
  - Cadena evolutiva
- **Interfaz Responsiva**: Diseño adaptable que funciona en diferentes tamaños de pantalla
- **Modo Offline**: Caché local para mejorar el rendimiento

## 🛠️ Tecnologías Utilizadas

### Lenguaje
- **Kotlin** - Lenguaje de programación moderno y seguro para Android

### Framework de UI
- **Jetpack Compose** - Toolkit declarativo para construir interfaces de usuario nativas en Android
  - Mejora en la experiencia de desarrollo
  - Código más limpio y mantenible
  - Render más eficiente

### Arquitectura y Patrones
- **MVVM** (Model-View-ViewModel) - Separación clara de responsabilidades
- **Repository Pattern** - Abstracción del acceso a datos
- **Coroutines** - Programación asincrónica no bloqueante

### Librerías Clave
- **Retrofit** - Cliente HTTP para consumir APIs REST
- **OkHttp** - Interceptor y caché HTTP
- **Hilt** - Inyección de dependencias
- **Room** - Base de datos local SQLite
- **Coil** - Carga y caché de imágenes
- **Kotlin Flow** - Flujos reactivos para manejo de datos
- **Lifecycle** - Gestión del ciclo de vida de la aplicación

### API Externa
- **[PokeAPI](https://pokeapi.co/)** - API pública con información completa de Pokémon

## 📋 Requisitos Previos

- Android Studio (versión Giraffe o superior)
- JDK 11 o superior
- Android SDK API 24 (Android 7.0) o superior como mínimo
- Kotlin 1.8 o superior

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/Arsene-Althor/Pokedex-PokeApi.git
cd Pokedex-PokeApi
```

### 2. Abrir en Android Studio

1. Abre Android Studio
2. Selecciona `File > Open` y elige la carpeta del proyecto
3. Espera a que Gradle sincronice las dependencias

### 3. Ejecutar la aplicación

1. Conecta un dispositivo Android o inicia un emulador
2. Haz clic en el botón `Run` (▶️) en Android Studio
3. Selecciona el dispositivo/emulador destino

## 📂 Estructura del Proyecto

```
Pokedex-PokeApi/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/pokedex/
│   │   │   │   ├── data/
│   │   │   │   │   ├── api/          # Interfaces Retrofit
│   │   │   │   │   ├── dao/          # Acceso a base de datos
│   │   │   │   │   ├── model/        # Entidades de datos
│   │   │   │   │   └── repository/   # Lógica de repositorio
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/      # Pantallas Compose
│   │   │   │   │   ├── components/   # Componentes reutilizables
│   │   │   │   │   └── viewmodel/    # ViewModels
│   │   │   │   ├── di/               # Módulos de inyección
│   │   │   │   └── utils/            # Utilidades generales
│   │   │   └── res/
│   │   └── test/
│   └── build.gradle.kts
└── README.md
```

## 🔄 Flujo de Datos

```
API (PokeAPI)
    ↓
Retrofit Client
    ↓
Repository (obtiene/cachea datos)
    ↓
ViewModel (prepara datos para UI)
    ↓
Composable Screens (renderiza UI)
```

## 📚 Conceptos Aprendidos

Este proyecto demuestra la aplicación práctica de:

- **Composables**: Funciones para crear UI declarativa
- **State Management**: Gestión del estado en Compose
- **Coroutines**: Operaciones asincrónicas
- **Flow**: Streams reactivos de datos
- **Networking**: Consumo de APIs REST
- **Caching**: Optimización de rendimiento
- **Inyección de Dependencias**: Patrón SOLID
- **Testing**: Pruebas unitarias e instrumentadas

## 🧪 Testing

Para ejecutar las pruebas del proyecto:

```bash
# Pruebas unitarias
./gradlew test

# Pruebas instrumentadas (en dispositivo)
./gradlew connectedAndroidTest
```

## 📄 Licencia

Este proyecto se desarrolló con fines educativos. 

La información de Pokémon proviene de [PokeAPI](https://pokeapi.co/), que se distribuye bajo licencia [CC BY-NC 2.0](https://creativecommons.org/licenses/by-nc/2.0/).

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Para cambios significativos, abre un issue primero para discutir los cambios propuestos.

## 📞 Contacto

- **GitHub**: [@Arsene-Althor](https://github.com/Arsene-Althor)

## 🎓 Recursos Adicionales

- [Documentación de Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin Official Documentation](https://kotlinlang.org/docs/home.html)
- [PokeAPI Documentation](https://pokeapi.co/docs/v2)
- [Android Architecture Components](https://developer.android.com/jetpack)
- [Coroutines Guide](https://kotlinlang.org/docs/coroutines-overview.html)

---

**Última actualización**: Junio 2026

⭐ Si te resulta útil, considera dejar una estrella en el repositorio
