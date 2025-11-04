class Personaje {
  final int id;
  final String nombre;
  final String alias;
  final String rango;
  final String aldea;
  final int powerLevel;
  final String descripcion;
  final String? imagen;
  final List<String> habilidades;

  Personaje({
    required this.id,
    required this.nombre,
    required this.alias,
    required this.rango,
    required this.aldea,
    required this.powerLevel,
    required this.descripcion,
    this.imagen,
    required this.habilidades,
  });

  factory Personaje.fromJson(Map<String, dynamic> json) {
    return Personaje(
      id: json['id'] ?? 0,
      nombre: json['nombre'] ?? '',
      alias: json['alias'] ?? '',
      rango: json['rango'] ?? '',
      aldea: json['aldea'] ?? '',
      powerLevel: json['powerLevel'] ?? 0,
      descripcion: json['descripcion'] ?? '',
      imagen: json['imagen'],
      habilidades: List<String>.from(json['habilidades'] ?? []),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'alias': alias,
      'rango': rango,
      'aldea': aldea,
      'powerLevel': powerLevel,
      'descripcion': descripcion,
      'imagen': imagen,
      'habilidades': habilidades,
    };
  }

  // Getter para color del rango
  String get rangoColor {
    switch (rango.toLowerCase()) {
      case 'genin':
        return '#4CAF50';
      case 'chunin':
        return '#FF9800';
      case 'jonin':
        return '#2196F3';
      case 'kage':
        return '#9C27B0';
      case 'sannin':
        return '#F44336';
      default:
        return '#757575';
    }
  }

  // Getter para nivel de poder en estrellas
  int get estrellasPoder {
    if (powerLevel >= 9000) return 5;
    if (powerLevel >= 7000) return 4;
    if (powerLevel >= 5000) return 3;
    if (powerLevel >= 3000) return 2;
    return 1;
  }
}