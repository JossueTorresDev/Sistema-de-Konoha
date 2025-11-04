import 'package:flutter/material.dart';
import '../models/personaje.dart';
import '../services/api_service.dart';

class PersonajesProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  
  List<Personaje> _personajes = [];
  List<Personaje> _personajesFiltrados = [];
  bool _isLoading = false;
  String? _error;
  String _filtroRango = '';
  String _busqueda = '';

  List<Personaje> get personajes => _personajesFiltrados;
  bool get isLoading => _isLoading;
  String? get error => _error;
  String get filtroRango => _filtroRango;
  String get busqueda => _busqueda;

  Future<void> loadPersonajes() async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final data = await _apiService.getPersonajes();
      _personajes = data.map((json) => Personaje.fromJson(json)).toList();
      _aplicarFiltros();
    } catch (e) {
      _error = e.toString();
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  void setBusqueda(String busqueda) {
    _busqueda = busqueda;
    _aplicarFiltros();
    notifyListeners();
  }

  void setFiltroRango(String rango) {
    _filtroRango = rango;
    _aplicarFiltros();
    notifyListeners();
  }

  void _aplicarFiltros() {
    _personajesFiltrados = _personajes.where((personaje) {
      final cumpleBusqueda = _busqueda.isEmpty ||
          personaje.nombre.toLowerCase().contains(_busqueda.toLowerCase()) ||
          personaje.alias.toLowerCase().contains(_busqueda.toLowerCase());
      
      final cumpleRango = _filtroRango.isEmpty ||
          personaje.rango.toLowerCase() == _filtroRango.toLowerCase();
      
      return cumpleBusqueda && cumpleRango;
    }).toList();

    // Ordenar por power level descendente
    _personajesFiltrados.sort((a, b) => b.powerLevel.compareTo(a.powerLevel));
  }

  Future<void> crearPersonaje(Personaje personaje) async {
    try {
      await _apiService.post('/personajes', personaje.toJson());
      await loadPersonajes(); // Recargar lista
    } catch (e) {
      _error = e.toString();
      notifyListeners();
    }
  }

  Future<void> actualizarPersonaje(Personaje personaje) async {
    try {
      await _apiService.put('/personajes/${personaje.id}', personaje.toJson());
      await loadPersonajes(); // Recargar lista
    } catch (e) {
      _error = e.toString();
      notifyListeners();
    }
  }

  Future<void> eliminarPersonaje(int id) async {
    try {
      await _apiService.delete('/personajes/$id');
      await loadPersonajes(); // Recargar lista
    } catch (e) {
      _error = e.toString();
      notifyListeners();
    }
  }
}