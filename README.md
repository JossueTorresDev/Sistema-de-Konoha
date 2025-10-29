# 🌀 Base de Datos Ninja — Proyecto Temático de Naruto

**Autor:** Jossue Uchiha  
**Rol:** Analista de Sistemas  
**Tecnología principal:** PostgreSQL (Neon)  
**Propósito:** Demostrar análisis, modelado y desarrollo de base de datos relacional  
**Tema:** Universo Shinobi (Naruto)

---

## 📖 Introducción

El proyecto **“Base de Datos Ninja”** está inspirado en el universo de *Naruto Shippuden* y desarrollado con el objetivo de **mostrar habilidades técnicas y analíticas en el diseño de sistemas de información**.

La idea es trasladar la estructura social del mundo shinobi a un sistema relacional que refleje:
- **Relaciones jerárquicas:** aldeas → clanes → ninjas.  
- **Relaciones de entrenamiento:** ninjas ↔ jutsus.  
- **Relaciones operativas:** ninjas ↔ misiones.  

Este proyecto demuestra cómo una narrativa compleja (el mundo ninja) puede representarse de forma técnica y visual mediante un modelo de base de datos bien estructurado.

---

## 🎯 Objetivos del Proyecto

1. **Aplicar conceptos de modelado entidad–relación** para representar datos del mundo ninja.  
2. **Implementar la base en PostgreSQL** utilizando Neon como entorno en la nube.  
3. **Optimizar consultas** con vistas, funciones y materialized views.  
4. **Proveer base para una API o Dashboard visual** que muestre datos de manera interactiva.  

---

## 🧱 Arquitectura del Proyecto

La base de datos está organizada en distintos módulos o entidades:

| Módulo | Descripción | Ejemplo |
|--------|--------------|----------|
| 🏯 **Aldeas** | Representan los países y su geografía. | Konoha, Amegakure |
| 🩸 **Clanes** | Grupos familiares o linajes especiales. | Uchiha, Uzumaki |
| 🧑‍🎓 **Personajes** | Shinobis con estadísticas, habilidades y afiliaciones. | Naruto, Sasuke, Itachi |
| 💥 **Jutsus** | Técnicas y habilidades aprendidas por los ninjas. | Rasengan, Sharingan |
| 🗺️ **Misiones** | Actividades en las que los ninjas participan. | “Protección del cartero” |
| 📊 **Perfil Shinobi (JSONB)** | Información dinámica para visualización web. | Biografía, logros, habilidades. |

---

## ⚙️ Tecnologías Utilizadas

| Tecnología | Uso |
|-------------|-----|
| **PostgreSQL** | Motor principal de base de datos |
| **pgcrypto** | Generación de UUIDs |
| **citext** | Campos de texto insensibles a mayúsculas/minúsculas |
| **Neon.tech** | Base de datos PostgreSQL en la nube |
| **SQL avanzado (PL/pgSQL)** | Funciones y vistas calculadas |
| **JSONB** | Campos flexibles para front-end dinámico |

---

## 🧩 Diseño Lógico Simplificado

```text
Aldea 1 ─── * Clan 1 ─── * Personaje * ─── * Jutsu
                                   |
                                   * ─── * Misión
