import 'package:provider/provider.dart';
import 'dashboard_provider.dart';
import 'personajes_provider.dart';

class AppProviders {
  static List<ChangeNotifierProvider> get providers => [
    ChangeNotifierProvider(create: (_) => DashboardProvider()),
    ChangeNotifierProvider(create: (_) => PersonajesProvider()),
  ];
}