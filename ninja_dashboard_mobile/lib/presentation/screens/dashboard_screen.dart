import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../core/providers/dashboard_provider.dart';
import '../widgets/stat_card.dart';
import '../widgets/loading_widget.dart';
import '../widgets/error_widget.dart';

class DashboardScreen extends StatefulWidget {
  const DashboardScreen({super.key});

  @override
  State<DashboardScreen> createState() => _DashboardScreenState();
}

class _DashboardScreenState extends State<DashboardScreen> {
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      context.read<DashboardProvider>().loadDashboardStats();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('🥷 Shinobi Dashboard'),
        backgroundColor: Theme.of(context).primaryColor,
        foregroundColor: Colors.white,
      ),
      body: Consumer<DashboardProvider>(
        builder: (context, provider, child) {
          if (provider.isLoading) {
            return const LoadingWidget();
          }

          if (provider.error != null) {
            return ErrorWidgetCustom(
              message: provider.error!,
              onRetry: () => provider.loadDashboardStats(),
            );
          }

          return RefreshIndicator(
            onRefresh: () => provider.loadDashboardStats(),
            child: SingleChildScrollView(
              physics: const AlwaysScrollableScrollPhysics(),
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // Estadísticas principales
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
                        value: provider.totalPersonajes.toString(),
                        icon: Icons.person,
                        color: Colors.blue,
                      ),
                      StatCard(
                        title: 'Aldeas',
                        value: provider.totalAldeas.toString(),
                        icon: Icons.location_city,
                        color: Colors.green,
                      ),
                      StatCard(
                        title: 'Jutsus',
                        value: provider.totalJutsus.toString(),
                        icon: Icons.auto_fix_high,
                        color: Colors.orange,
                      ),
                      StatCard(
                        title: 'Misiones',
                        value: provider.totalMisiones.toString(),
                        icon: Icons.assignment,
                        color: Colors.purple,
                      ),
                    ],
                  ),
                  const SizedBox(height: 32),

                  // Top Personajes
                  if (provider.topPersonajes.isNotEmpty) ...[
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
                        itemCount: provider.topPersonajes.length,
                        itemBuilder: (context, index) {
                          final personaje = provider.topPersonajes[index];
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
                                        color: Theme.of(context).primaryColor,
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
                                      'Poder: ${personaje['powerLevel']}',
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
          );
        },
      ),
    );
  }
}