import 'package:flutter/material.dart';
import '../../core/models/personaje.dart';

class PersonajeCard extends StatelessWidget {
  final Personaje personaje;
  final VoidCallback? onTap;

  const PersonajeCard({
    super.key,
    required this.personaje,
    this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.only(bottom: 12),
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(12),
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Row(
            children: [
              // Avatar del personaje
              CircleAvatar(
                radius: 30,
                backgroundColor: Theme.of(context).primaryColor.withValues(alpha: 0.1),
                child: personaje.imagen != null
                    ? ClipOval(
                        child: Image.network(
                          personaje.imagen!,
                          width: 60,
                          height: 60,
                          fit: BoxFit.cover,
                          errorBuilder: (context, error, stackTrace) {
                            return Icon(
                              Icons.person,
                              size: 30,
                              color: Theme.of(context).primaryColor,
                            );
                          },
                        ),
                      )
                    : Icon(
                        Icons.person,
                        size: 30,
                        color: Theme.of(context).primaryColor,
                      ),
              ),
              const SizedBox(width: 16),

              // Información del personaje
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      personaje.nombre,
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      personaje.alias,
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
                            color: _getRangoColor(personaje.rango),
                            borderRadius: BorderRadius.circular(12),
                          ),
                          child: Text(
                            personaje.rango,
                            style: const TextStyle(
                              color: Colors.white,
                              fontSize: 12,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        const SizedBox(width: 8),
                        Text(
                          personaje.aldea,
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

              // Power level y estrellas
              Column(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: [
                  Text(
                    '${personaje.powerLevel}',
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      color: Colors.red,
                    ),
                  ),
                  const SizedBox(height: 4),
                  Row(
                    mainAxisSize: MainAxisSize.min,
                    children: List.generate(5, (index) {
                      return Icon(
                        index < personaje.estrellasPoder
                            ? Icons.star
                            : Icons.star_border,
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
}