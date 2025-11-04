import 'package:flutter/material.dart';

class PersonajeDetailScreen extends StatelessWidget {
  final int personajeId;

  const PersonajeDetailScreen({
    super.key,
    required this.personajeId,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Detalle del Personaje'),
        backgroundColor: Theme.of(context).primaryColor,
        foregroundColor: Colors.white,
      ),
      body: const Center(
        child: Text(
          'Pantalla de detalle del personaje\n(Por implementar)',
          textAlign: TextAlign.center,
          style: TextStyle(fontSize: 16),
        ),
      ),
    );
  }
}