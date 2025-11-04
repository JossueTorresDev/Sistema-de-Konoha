import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:go_router/go_router.dart';
import '../../core/providers/personajes_provider.dart';
import '../widgets/loading_widget.dart';
import '../widgets/error_widget.dart';
import '../widgets/personaje_card.dart';

class PersonajesScreen extends StatefulWidget {
  const PersonajesScreen({super.key});

  @override
  State<PersonajesScreen> createState() => _PersonajesScreenState();
}

class _PersonajesScreenState extends State<PersonajesScreen> {
  final TextEditingController _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      context.read<PersonajesProvider>().loadPersonajes();
    });
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('👥 Personajes'),
        backgroundColor: Theme.of(context).primaryColor,
        foregroundColor: Colors.white,
      ),
      body: Consumer<PersonajesProvider>(
        builder: (context, provider, child) {
          if (provider.isLoading) {
            return const LoadingWidget();
          }

          if (provider.error != null) {
            return ErrorWidgetCustom(
              message: provider.error!,
              onRetry: () => provider.loadPersonajes(),
            );
          }

          return Column(
            children: [
              // Barra de búsqueda y filtros
              Container(
                padding: const EdgeInsets.all(16),
                child: Column(
                  children: [
                    TextField(
                      controller: _searchController,
                      decoration: const InputDecoration(
                        hintText: 'Buscar personajes...',
                        prefixIcon: Icon(Icons.search),
                        border: OutlineInputBorder(),
                      ),
                      onChanged: (value) {
                        provider.setBusqueda(value);
                      },
                    ),
                    const SizedBox(height: 12),
                    SingleChildScrollView(
                      scrollDirection: Axis.horizontal,
                      child: Row(
                        children: [
                          _buildFilterChip('Todos', '', provider),
                          const SizedBox(width: 8),
                          _buildFilterChip('Genin', 'genin', provider),
                          const SizedBox(width: 8),
                          _buildFilterChip('Chunin', 'chunin', provider),
                          const SizedBox(width: 8),
                          _buildFilterChip('Jonin', 'jonin', provider),
                          const SizedBox(width: 8),
                          _buildFilterChip('Kage', 'kage', provider),
                          const SizedBox(width: 8),
                          _buildFilterChip('Sannin', 'sannin', provider),
                        ],
                      ),
                    ),
                  ],
                ),
              ),

              // Lista de personajes
              Expanded(
                child: RefreshIndicator(
                  onRefresh: () => provider.loadPersonajes(),
                  child: provider.personajes.isEmpty
                      ? const Center(
                          child: Text(
                            'No se encontraron personajes',
                            style: TextStyle(fontSize: 16),
                          ),
                        )
                      : ListView.builder(
                          padding: const EdgeInsets.symmetric(horizontal: 16),
                          itemCount: provider.personajes.length,
                          itemBuilder: (context, index) {
                            final personaje = provider.personajes[index];
                            return PersonajeCard(
                              personaje: personaje,
                              onTap: () {
                                context.push('/personajes/${personaje.id}');
                              },
                            );
                          },
                        ),
                ),
              ),
            ],
          );
        },
      ),
    );
  }

  Widget _buildFilterChip(String label, String value, PersonajesProvider provider) {
    final isSelected = provider.filtroRango == value;
    return FilterChip(
      label: Text(label),
      selected: isSelected,
      onSelected: (selected) {
        provider.setFiltroRango(selected ? value : '');
      },
      selectedColor: Theme.of(context).primaryColor.withValues(alpha: 0.2),
      checkmarkColor: Theme.of(context).primaryColor,
    );
  }
}