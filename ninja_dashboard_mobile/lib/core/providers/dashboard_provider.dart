import 'package:flutter/material.dart';
import '../services/api_service.dart';

class DashboardProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  
  Map<String, dynamic> _stats = {};
  bool _isLoading = false;
  String? _error;

  Map<String, dynamic> get stats => _stats;
  bool get isLoading => _isLoading;
  String? get error => _error;

  Future<void> loadDashboardStats() async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      _stats = await _apiService.getDashboardStats();
    } catch (e) {
      _error = e.toString();
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Getters para estadísticas específicas
  int get totalPersonajes => _stats['totalPersonajes'] ?? 0;
  int get totalAldeas => _stats['totalAldeas'] ?? 0;
  int get totalJutsus => _stats['totalJutsus'] ?? 0;
  int get totalMisiones => _stats['totalMisiones'] ?? 0;

  List<dynamic> get topPersonajes => _stats['topPersonajes'] ?? [];
  List<dynamic> get aldeasPorPoblacion => _stats['aldeasPorPoblacion'] ?? [];
  List<dynamic> get jutsusPorTipo => _stats['jutsusPorTipo'] ?? [];
}