class Jutsu {
  final int id;
  final String nombre;
  final String tipo;
  final String elemento;
  final int nivel;
  final String descripcion;
  final String? creador;
  final List<String> usuarios;

  Jutsu({
    required this.id,
    required this.nombre,
    required this.tipo,
    required this.elemento,
    required this.nivel,
    required this.descripcion,
    this.creador,
    required this.usuarios,
  });

  factory Jutsu.fromJson(Map<String, dynamic> json) {
    return Jutsu(
      id: json['id'] ?? 0,
      nombre: json['nombre'] ?? '',
      tipo: json['tipo'] ?? '',
      elemento: json['elemento'] ?? '',
      nivel: json['nivel'] ?? 1,
      descripcion: json['descripcion'] ?? '',
      creador: json['creador'],
      usuarios: List<String>.from(json['usuarios'] ?? []),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'tipo': tipo,
      'elemento': elemento,
      'nivel': nivel,
      'descripcion': descripcion,
      'creador': creador,
      'usuarios': usuarios,
    };
  }

  // Getter para color del elemento
  String get colorElemento {
    switch (elemento.toLowerCase()) {
      case 'fuego':
        return '#F44336';
      case 'agua':
        return '#2196F3';
      case 'tierra':
        return '#795548';
      case 'aire':
        return '#9E9E9E';
      case 'rayo':
        return '#FFEB3B';
      default:
        return '#9C27B0';
    }
  }

  // Getter para icono del tipo
  String get iconoTipo {
    switch (tipo.toLowerCase()) {
      case 'ninjutsu':
        return '🥷';
      case 'genjutsu':
        return '👁️';
      case 'taijutsu':
        return '👊';
      case 'kinjutsu':
        return '⚠️';
      default:
        return '⚡';
    }
  }

  // Getter para dificultad
  String get dificultad {
    switch (nivel) {
      case 1:
        return 'Básico';
      case 2:
        return 'Intermedio';
      case 3:
        return 'Avanzado';
      case 4:
        return 'Experto';
      case 5:
        return 'Legendario';
      default:
        return 'Desconocido';
    }
  }
}