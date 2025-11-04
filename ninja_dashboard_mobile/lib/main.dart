import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() {
  runApp(const ShinobiDashboardApp());
}

class ShinobiDashboardApp extends StatelessWidget {
  const ShinobiDashboardApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Shinobi Dashboard',
      theme: ThemeData(
        primarySwatch: Colors.indigo,
        primaryColor: const Color(0xFF1A237E),
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: const MainScreen(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class ApiService {
  // IP de tu computadora para que funcione en el celular
  static const String baseUrl = 'http://192.168.0.101:5000/api';

  static Future<Map<String, dynamic>> getDashboardStats() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/dashboard/stats'));
      if (response.statusCode == 200) {
        return json.decode(response.body);
      } else {
        throw Exception('Error ${response.statusCode}');
      }
    } catch (e) {
      print('Error getting dashboard stats: $e');
      return {
        'totalPersonajes': 0,
        'totalAldeas': 0,
        'totalJutsus': 0,
        'totalMisiones': 0,
        'topPersonajes': [],
      };
    }
  }

  static Future<List<dynamic>> getPersonajes() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/personajes'));
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        return data is List ? data : (data['data'] ?? []);
      } else {
        throw Exception('Error ${response.statusCode}');
      }
    } catch (e) {
      print('Error getting personajes: $e');
      return [];
    }
  }

  static Future<List<dynamic>> getAldeas() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/aldeas'));
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        return data is List ? data : (data['data'] ?? []);
      } else {
        throw Exception('Error ${response.statusCode}');
      }
    } catch (e) {
      print('Error getting aldeas: $e');
      return [];
    }
  }

  static Future<List<dynamic>> getJutsus() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/jutsus'));
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        return data is List ? data : (data['data'] ?? []);
      } else {
        throw Exception('Error ${response.statusCode}');
      }
    } catch (e) {
      print('Error getting jutsus: $e');
      return [];
    }
  }
}

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int _currentIndex = 0;

  final List<Widget> _screens = [
    const DashboardScreen(),
    const PersonajesScreen(),
    const AldeasScreen(),
    const JutsusScreen(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _screens[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        currentIndex: _currentIndex,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        selectedItemColor: const Color(0xFF1A237E),
        unselectedItemColor: Colors.grey,
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.dashboard),
            label: 'Dashboard',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: 'Personajes',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.location_city),
            label: 'Aldeas',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.auto_fix_high),
            label: 'Jutsus',
          ),
        ],
      ),
    );
  }
}

class DashboardScreen extends StatefulWidget {
  const DashboardScreen({super.key});

  @override
  State<DashboardScreen> createState() => _DashboardScreenState();
}

class _DashboardScreenState extends State<DashboardScreen> {
  Map<String, dynamic> _stats = {};
  bool _isLoading = true;
  String? _error;

  @override
  void initState() {
    super.initState();
    _loadDashboardStats();
  }

  Future<void> _loadDashboardStats() async {
    setState(() {
      _isLoading = true;
      _error = null;
    });

    try {
      final stats = await ApiService.getDashboardStats();
      setState(() {
        _stats = stats;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _error = e.toString();
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('🥷 Shinobi Dashboard'),
        backgroundColor: const Color(0xFF1A237E),
        foregroundColor: Colors.white,
      ),
      body: RefreshIndicator(
        onRefresh: _loadDashboardStats,
        child: SingleChildScrollView(
          physics: const AlwaysScrollableScrollPhysics(),
          padding: const EdgeInsets.all(16),
          child: _isLoading
              ? const Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      CircularProgressIndicator(),
                      SizedBox(height: 16),
                      Text('Cargando estadísticas...'),
                    ],
                  ),
                )
              : _error != null
                  ? Center(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          const Icon(Icons.error, size: 64, color: Colors.red),
                          const SizedBox(height: 16),
                          Text('Error: $_error'),
                          const SizedBox(height: 16),
                          ElevatedButton(
                            onPressed: _loadDashboardStats,
                            child: const Text('Reintentar'),
                          ),
                        ],
                      ),
                    )
                  : Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text(
                          'Estadísticas Generales',
                          style: TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 16),
                        GridView.count(
                          shrinkWrap: true,
                          physics: const NeverScrollableScrollPhysics(),
                          crossAxisCount: 2,
                          crossAxisSpacing: 16,
                          mainAxisSpacing: 16,
                          childAspectRatio: 1.2,
                          children: [
                            StatCard(
                              title: 'Personajes',
                              value: '${_stats['totalPersonajes'] ?? 0}',
                              icon: Icons.person,
                              color: Colors.blue,
                            ),
                            StatCard(
                              title: 'Aldeas',
                              value: '${_stats['totalAldeas'] ?? 0}',
                              icon: Icons.location_city,
                              color: Colors.green,
                            ),
                            StatCard(
                              title: 'Jutsus',
                              value: '${_stats['totalJutsus'] ?? 0}',
                              icon: Icons.auto_fix_high,
                              color: Colors.orange,
                            ),
                            StatCard(
                              title: 'Misiones',
                              value: '${_stats['totalMisiones'] ?? 0}',
                              icon: Icons.assignment,
                              color: Colors.purple,
                            ),
                          ],
                        ),
                        const SizedBox(height: 32),
                        if (_stats['topPersonajes'] != null && (_stats['topPersonajes'] as List).isNotEmpty) ...[
                          const Text(
                            'Top Personajes por Poder',
                            style: TextStyle(
                              fontSize: 20,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: 16),
                          SizedBox(
                            height: 200,
                            child: ListView.builder(
                              scrollDirection: Axis.horizontal,
                              itemCount: (_stats['topPersonajes'] as List).length,
                              itemBuilder: (context, index) {
                                final personaje = (_stats['topPersonajes'] as List)[index];
                                return Container(
                                  width: 160,
                                  margin: const EdgeInsets.only(right: 16),
                                  child: Card(
                                    child: Padding(
                                      padding: const EdgeInsets.all(12),
                                      child: Column(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          Text(
                                            personaje['nombre'] ?? '',
                                            style: const TextStyle(
                                              fontWeight: FontWeight.bold,
                                              fontSize: 16,
                                            ),
                                            maxLines: 1,
                                            overflow: TextOverflow.ellipsis,
                                          ),
                                          const SizedBox(height: 4),
                                          Text(
                                            personaje['alias'] ?? '',
                                            style: TextStyle(
                                              color: Colors.grey[600],
                                              fontSize: 12,
                                            ),
                                            maxLines: 1,
                                            overflow: TextOverflow.ellipsis,
                                          ),
                                          const SizedBox(height: 8),
                                          Container(
                                            padding: const EdgeInsets.symmetric(
                                              horizontal: 8,
                                              vertical: 4,
                                            ),
                                            decoration: BoxDecoration(
                                              color: const Color(0xFF1A237E),
                                              borderRadius: BorderRadius.circular(12),
                                            ),
                                            child: Text(
                                              personaje['rango'] ?? '',
                                              style: const TextStyle(
                                                color: Colors.white,
                                                fontSize: 10,
                                              ),
                                            ),
                                          ),
                                          const Spacer(),
                                          Text(
                                            'Poder: ${personaje['powerLevel'] ?? 0}',
                                            style: const TextStyle(
                                              fontWeight: FontWeight.bold,
                                              color: Colors.red,
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                                  ),
                                );
                              },
                            ),
                          ),
                        ],
                      ],
                    ),
        ),
      ),
    );
  }
}

class PersonajesScreen extends StatefulWidget {
  const PersonajesScreen({super.key});

  @override
  State<PersonajesScreen> createState() => _PersonajesScreenState();
}

class _PersonajesScreenState extends State<PersonajesScreen> {
  List<dynamic> _personajes = [];
  List<dynamic> _personajesFiltrados = [];
  bool _isLoading = true;
  String? _error;
  String _busqueda = '';

  @override
  void initState() {
    super.initState();
    _loadPersonajes();
  }

  Future<void> _loadPersonajes() async {
    setState(() {
      _isLoading = true;
      _error = null;
    });

    try {
      final personajes = await ApiService.getPersonajes();
      setState(() {
        _personajes = personajes;
        _personajesFiltrados = personajes;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _error = e.toString();
        _isLoading = false;
      });
    }
  }

  void _filtrarPersonajes(String busqueda) {
    setState(() {
      _busqueda = busqueda;
      if (busqueda.isEmpty) {
        _personajesFiltrados = _personajes;
      } else {
        _personajesFiltrados = _personajes.where((personaje) {
          final nombre = (personaje['nombre'] ?? '').toString().toLowerCase();
          final alias = (personaje['alias'] ?? '').toString().toLowerCase();
          return nombre.contains(busqueda.toLowerCase()) ||
                 alias.contains(busqueda.toLowerCase());
        }).toList();
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('👥 Personajes'),
        backgroundColor: const Color(0xFF1A237E),
        foregroundColor: Colors.white,
      ),
      body: Column(
        children: [
          Container(
            padding: const EdgeInsets.all(16),
            child: TextField(
              decoration: const InputDecoration(
                hintText: 'Buscar personajes...',
                prefixIcon: Icon(Icons.search),
                border: OutlineInputBorder(),
              ),
              onChanged: _filtrarPersonajes,
            ),
          ),
          Expanded(
            child: _isLoading
                ? const Center(child: CircularProgressIndicator())
                : _error != null
                    ? Center(
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            const Icon(Icons.error, size: 64, color: Colors.red),
                            const SizedBox(height: 16),
                            Text('Error: $_error'),
                            const SizedBox(height: 16),
                            ElevatedButton(
                              onPressed: _loadPersonajes,
                              child: const Text('Reintentar'),
                            ),
                          ],
                        ),
                      )
                    : RefreshIndicator(
                        onRefresh: _loadPersonajes,
                        child: _personajesFiltrados.isEmpty
                            ? const Center(
                                child: Text('No se encontraron personajes'),
                              )
                            : ListView.builder(
                                padding: const EdgeInsets.symmetric(horizontal: 16),
                                itemCount: _personajesFiltrados.length,
                                itemBuilder: (context, index) {
                                  final personaje = _personajesFiltrados[index];
                                  return Card(
                                    margin: const EdgeInsets.only(bottom: 12),
                                    child: Padding(
                                      padding: const EdgeInsets.all(16),
                                      child: Row(
                                        children: [
                                          CircleAvatar(
                                            radius: 30,
                                            backgroundColor: const Color(0xFF1A237E).withValues(alpha: 0.1),
                                            child: const Icon(
                                              Icons.person,
                                              size: 30,
                                              color: Color(0xFF1A237E),
                                            ),
                                          ),
                                          const SizedBox(width: 16),
                                          Expanded(
                                            child: Column(
                                              crossAxisAlignment: CrossAxisAlignment.start,
                                              children: [
                                                Text(
                                                  personaje['nombre'] ?? '',
                                                  style: const TextStyle(
                                                    fontSize: 18,
                                                    fontWeight: FontWeight.bold,
                                                  ),
                                                ),
                                                const SizedBox(height: 4),
                                                Text(
                                                  personaje['alias'] ?? '',
                                                  style: TextStyle(
                                                    fontSize: 14,
                                                    color: Colors.grey[600],
                                                  ),
                                                ),
                                                const SizedBox(height: 8),
                                                Row(
                                                  children: [
                                                    Container(
                                                      padding: const EdgeInsets.symmetric(
                                                        horizontal: 8,
                                                        vertical: 4,
                                                      ),
                                                      decoration: BoxDecoration(
                                                        color: _getRangoColor(personaje['rango'] ?? ''),
                                                        borderRadius: BorderRadius.circular(12),
                                                      ),
                                                      child: Text(
                                                        personaje['rango'] ?? '',
                                                        style: const TextStyle(
                                                          color: Colors.white,
                                                          fontSize: 12,
                                                          fontWeight: FontWeight.bold,
                                                        ),
                                                      ),
                                                    ),
                                                    const SizedBox(width: 8),
                                                    Text(
                                                      personaje['aldea'] ?? '',
                                                      style: TextStyle(
                                                        fontSize: 12,
                                                        color: Colors.grey[600],
                                                      ),
                                                    ),
                                                  ],
                                                ),
                                              ],
                                            ),
                                          ),
                                          Column(
                                            crossAxisAlignment: CrossAxisAlignment.end,
                                            children: [
                                              Text(
                                                '${personaje['powerLevel'] ?? 0}',
                                                style: const TextStyle(
                                                  fontSize: 16,
                                                  fontWeight: FontWeight.bold,
                                                  color: Colors.red,
                                                ),
                                              ),
                                              const SizedBox(height: 4),
                                              Row(
                                                mainAxisSize: MainAxisSize.min,
                                                children: List.generate(5, (starIndex) {
                                                  final estrellas = _getEstrellas(personaje['powerLevel'] ?? 0);
                                                  return Icon(
                                                    starIndex < estrellas ? Icons.star : Icons.star_border,
                                                    size: 16,
                                                    color: Colors.amber,
                                                  );
                                                }),
                                              ),
                                            ],
                                          ),
                                        ],
                                      ),
                                    ),
                                  );
                                },
                              ),
                      ),
          ),
        ],
      ),
    );
  }

  Color _getRangoColor(String rango) {
    switch (rango.toLowerCase()) {
      case 'genin':
        return Colors.green;
      case 'chunin':
        return Colors.orange;
      case 'jonin':
        return Colors.blue;
      case 'kage':
        return Colors.purple;
      case 'sannin':
        return Colors.red;
      default:
        return Colors.grey;
    }
  }

  int _getEstrellas(dynamic poder) {
    final powerLevel = poder is int ? poder : int.tryParse(poder.toString()) ?? 0;
    if (powerLevel >= 9000) return 5;
    if (powerLevel >= 7000) return 4;
    if (powerLevel >= 5000) return 3;
    if (powerLevel >= 3000) return 2;
    return 1;
  }
}

class AldeasScreen extends StatefulWidget {
  const AldeasScreen({super.key});

  @override
  State<AldeasScreen> createState() => _AldeasScreenState();
}

class _AldeasScreenState extends State<AldeasScreen> {
  List<dynamic> _aldeas = [];
  bool _isLoading = true;
  String? _error;

  @override
  void initState() {
    super.initState();
    _loadAldeas();
  }

  Future<void> _loadAldeas() async {
    setState(() {
      _isLoading = true;
      _error = null;
    });

    try {
      final aldeas = await ApiService.getAldeas();
      setState(() {
        _aldeas = aldeas;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _error = e.toString();
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('🏘️ Aldeas'),
        backgroundColor: const Color(0xFF1A237E),
        foregroundColor: Colors.white,
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _error != null
              ? Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      const Icon(Icons.error, size: 64, color: Colors.red),
                      const SizedBox(height: 16),
                      Text('Error: $_error'),
                      const SizedBox(height: 16),
                      ElevatedButton(
                        onPressed: _loadAldeas,
                        child: const Text('Reintentar'),
                      ),
                    ],
                  ),
                )
              : RefreshIndicator(
                  onRefresh: _loadAldeas,
                  child: _aldeas.isEmpty
                      ? const Center(
                          child: Text('No se encontraron aldeas'),
                        )
                      : GridView.builder(
                          padding: const EdgeInsets.all(16),
                          gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                            crossAxisCount: 1,
                            childAspectRatio: 2.5,
                            crossAxisSpacing: 16,
                            mainAxisSpacing: 16,
                          ),
                          itemCount: _aldeas.length,
                          itemBuilder: (context, index) {
                            final aldea = _aldeas[index];
                            return Card(
                              child: Padding(
                                padding: const EdgeInsets.all(16),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Row(
                                      children: [
                                        const Icon(Icons.location_city, size: 32, color: Color(0xFF1A237E)),
                                        const SizedBox(width: 12),
                                        Expanded(
                                          child: Column(
                                            crossAxisAlignment: CrossAxisAlignment.start,
                                            children: [
                                              Text(
                                                aldea['nombre'] ?? '',
                                                style: const TextStyle(
                                                  fontSize: 18,
                                                  fontWeight: FontWeight.bold,
                                                ),
                                              ),
                                              Text(
                                                aldea['pais'] ?? '',
                                                style: TextStyle(
                                                  fontSize: 14,
                                                  color: Colors.grey[600],
                                                ),
                                              ),
                                            ],
                                          ),
                                        ),
                                      ],
                                    ),
                                    const SizedBox(height: 12),
                                    Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                      children: [
                                        Column(
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: [
                                            const Text('Kage:', style: TextStyle(fontWeight: FontWeight.bold)),
                                            Text(aldea['kage'] ?? ''),
                                          ],
                                        ),
                                        Column(
                                          crossAxisAlignment: CrossAxisAlignment.end,
                                          children: [
                                            const Text('Población:', style: TextStyle(fontWeight: FontWeight.bold)),
                                            Text('${aldea['poblacion'] ?? 0} ninjas'),
                                          ],
                                        ),
                                      ],
                                    ),
                                  ],
                                ),
                              ),
                            );
                          },
                        ),
                ),
    );
  }
}

class JutsusScreen extends StatefulWidget {
  const JutsusScreen({super.key});

  @override
  State<JutsusScreen> createState() => _JutsusScreenState();
}

class _JutsusScreenState extends State<JutsusScreen> {
  List<dynamic> _jutsus = [];
  bool _isLoading = true;
  String? _error;

  @override
  void initState() {
    super.initState();
    _loadJutsus();
  }

  Future<void> _loadJutsus() async {
    setState(() {
      _isLoading = true;
      _error = null;
    });

    try {
      final jutsus = await ApiService.getJutsus();
      setState(() {
        _jutsus = jutsus;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _error = e.toString();
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('⚡ Jutsus'),
        backgroundColor: const Color(0xFF1A237E),
        foregroundColor: Colors.white,
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _error != null
              ? Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      const Icon(Icons.error, size: 64, color: Colors.red),
                      const SizedBox(height: 16),
                      Text('Error: $_error'),
                      const SizedBox(height: 16),
                      ElevatedButton(
                        onPressed: _loadJutsus,
                        child: const Text('Reintentar'),
                      ),
                    ],
                  ),
                )
              : RefreshIndicator(
                  onRefresh: _loadJutsus,
                  child: _jutsus.isEmpty
                      ? const Center(
                          child: Text('No se encontraron jutsus'),
                        )
                      : ListView.builder(
                          padding: const EdgeInsets.all(16),
                          itemCount: _jutsus.length,
                          itemBuilder: (context, index) {
                            final jutsu = _jutsus[index];
                            return Card(
                              margin: const EdgeInsets.only(bottom: 12),
                              child: Padding(
                                padding: const EdgeInsets.all(16),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Row(
                                      children: [
                                        Text(
                                          _getIconoTipo(jutsu['tipo'] ?? ''),
                                          style: const TextStyle(fontSize: 24),
                                        ),
                                        const SizedBox(width: 12),
                                        Expanded(
                                          child: Column(
                                            crossAxisAlignment: CrossAxisAlignment.start,
                                            children: [
                                              Text(
                                                jutsu['nombre'] ?? '',
                                                style: const TextStyle(
                                                  fontSize: 18,
                                                  fontWeight: FontWeight.bold,
                                                ),
                                              ),
                                              Text(
                                                '${jutsu['tipo'] ?? ''} - ${jutsu['elemento'] ?? ''}',
                                                style: TextStyle(
                                                  fontSize: 14,
                                                  color: Colors.grey[600],
                                                ),
                                              ),
                                            ],
                                          ),
                                        ),
                                        Row(
                                          children: List.generate(5, (starIndex) {
                                            final nivel = jutsu['nivel'] ?? 1;
                                            return Icon(
                                              starIndex < nivel ? Icons.star : Icons.star_border,
                                              size: 16,
                                              color: Colors.amber,
                                            );
                                          }),
                                        ),
                                      ],
                                    ),
                                    const SizedBox(height: 8),
                                    if (jutsu['creador'] != null)
                                      Text(
                                        'Creador: ${jutsu['creador']}',
                                        style: const TextStyle(
                                          fontSize: 12,
                                          fontStyle: FontStyle.italic,
                                        ),
                                      ),
                                    if (jutsu['descripcion'] != null)
                                      Padding(
                                        padding: const EdgeInsets.only(top: 8),
                                        child: Text(
                                          jutsu['descripcion'],
                                          style: const TextStyle(fontSize: 12),
                                          maxLines: 2,
                                          overflow: TextOverflow.ellipsis,
                                        ),
                                      ),
                                  ],
                                ),
                              ),
                            );
                          },
                        ),
                ),
    );
  }

  String _getIconoTipo(String tipo) {
    switch (tipo.toLowerCase()) {
      case 'ninjutsu':
        return '🥷';
      case 'genjutsu':
        return '👁️';
      case 'taijutsu':
        return '👊';
      case 'dojutsu':
        return '👀';
      case 'kinjutsu':
        return '⚠️';
      default:
        return '⚡';
    }
  }
}

class StatCard extends StatelessWidget {
  final String title;
  final String value;
  final IconData icon;
  final Color color;

  const StatCard({
    super.key,
    required this.title,
    required this.value,
    required this.icon,
    required this.color,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 4,
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              icon,
              size: 32,
              color: color,
            ),
            const SizedBox(height: 8),
            Text(
              value,
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: color,
              ),
            ),
            const SizedBox(height: 4),
            Text(
              title,
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey[600],
              ),
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }
}