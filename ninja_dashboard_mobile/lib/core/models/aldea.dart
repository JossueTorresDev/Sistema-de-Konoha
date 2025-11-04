class Aldea {
  final int id;
  final String nombre;
  final String pais;
  final String kage;
  final int poblacion;
  final String descripcion;
  final String? escudo;
  final List<String> especialidades;

  Aldea({
    required this.id,
    required this.nombre,
    required this.pais,
    required this.kage,
    required this.poblacion,
    required this.descripcion,
    this.escudo,
    required this.especialidades,
  });

  factory Aldea.fromJson(Map<String, dynamic> json) {
    return Aldea(
      id: json['id'] ?? 0,
      nombre: json['nombre'] ?? '',
      pais: json['pais'] ?? '',
      kage: json['kage'] ?? '',
      poblacion: json['poblacion'] ?? 0,
      descripcion: json['descripcion'] ?? '',
      escudo: json['escudo'],
      especialidades: List<String>.from(json['especialidades'] ?? []),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'pais': pais,
      'kage': kage,
      'poblacion': poblacion,
      'descripcion': descripcion,
      'escudo': escudo,
      'especialidades': especialidades,
    };
  }

  // Getter para tamaño de aldea
  String get tamano {
    if (poblacion >= 100000) return 'Grande';
    if (poblacion >= 50000) return 'Mediana';
    if (poblacion >= 20000) return 'Pequena';
    return 'Villa';
  }

  // Getter para color según población
  String get colorPoblacion {
    if (poblacion >= 100000) return '#4CAF50';
    if (poblacion >= 50000) return '#FF9800';
    if (poblacion >= 20000) return '#2196F3';
    return '#757575';
  }
}