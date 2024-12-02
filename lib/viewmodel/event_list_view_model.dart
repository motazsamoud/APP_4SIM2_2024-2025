import 'package:flutter/material.dart';
import 'package:dashbord/models/Event.dart';
import 'package:dashbord/services/EventService.dart';

class EventListViewModel extends ChangeNotifier {
  final EventService _eventService = EventService();
  List<Event> _events = [];
  List<Event> _displayedEvents = []; // Liste filtrée pour la recherche
  bool _isLoading = false;

  List<Event> get events => _events;
  bool get isLoading => _isLoading;
  List<Event> get displayedEvents => _displayedEvents;

  Future<void> fetchEvents() async {
    _isLoading = true;
    notifyListeners();

    try {
      List<Event> fetchedEvents = await _eventService.getEvents();
      _events = fetchedEvents;
      _displayedEvents =
          List.from(_events); // Initialiser les événements affichés
    } catch (e) {
      // Gérer les erreurs de chargement d'événements
      debugPrint('Error fetching events: $e');
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  void searchEvents(String query) {
    if (query.isEmpty) {
      _displayedEvents = List.from(_events);
    } else {
      _displayedEvents = _events
          .where((event) =>
              event.eventName.toLowerCase().contains(query.toLowerCase()) ||
              event.eventLocation.toLowerCase().contains(query.toLowerCase()))
          .toList();
    }
    notifyListeners();
  }

  void resetSearch() {
    _displayedEvents = List.from(_events);
    notifyListeners();
  }
}
