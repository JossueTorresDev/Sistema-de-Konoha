import 'package:go_router/go_router.dart';
import '../../presentation/screens/dashboard_screen.dart';
import '../../presentation/screens/personajes_screen.dart';
import '../../presentation/screens/personaje_detail_screen.dart';
import '../../presentation/screens/aldeas_screen.dart';
import '../../presentation/screens/jutsus_screen.dart';
import '../../presentation/screens/main_screen.dart';

class AppRouter {
  static final GoRouter router = GoRouter(
    initialLocation: '/',
    routes: [
      GoRoute(
        path: '/',
        builder: (context, state) => const MainScreen(),
      ),
      GoRoute(
        path: '/dashboard',
        builder: (context, state) => const DashboardScreen(),
      ),
      GoRoute(
        path: '/personajes',
        builder: (context, state) => const PersonajesScreen(),
      ),
      GoRoute(
        path: '/personajes/:id',
        builder: (context, state) {
          final id = int.parse(state.pathParameters['id']!);
          return PersonajeDetailScreen(personajeId: id);
        },
      ),
      GoRoute(
        path: '/aldeas',
        builder: (context, state) => const AldeasScreen(),
      ),
      GoRoute(
        path: '/jutsus',
        builder: (context, state) => const JutsusScreen(),
      ),
    ],
  );
}