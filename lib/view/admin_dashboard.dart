import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';
import 'package:dashbord/viewmodel/event_list_view_model.dart';
import 'package:dashbord/models/Event.dart';
import 'package:table_calendar/table_calendar.dart';
import 'package:dashbord/view/detailevent.dart';

class AdminDashboard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) {
        var eventListViewModel = EventListViewModel();
        eventListViewModel.fetchEvents();
        return eventListViewModel;
      },
      child: EventListPage(),
    );
  }
}

class EventListPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var eventListViewModel = Provider.of<EventListViewModel>(context);

    TextEditingController searchController = TextEditingController();

    return Scaffold(
      appBar: AppBar(
        title: Text('Events Admin ....'),
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              controller: searchController,
              decoration: InputDecoration(
                labelText: 'Recherche evenement',
                suffixIcon: IconButton(
                  icon: Icon(Icons.clear),
                  onPressed: () {
                    searchController.clear();
                    eventListViewModel.resetSearch();
                  },
                ),
              ),
              onChanged: (value) {
                eventListViewModel.searchEvents(value);
              },
            ),
          ),
          Expanded(
            child: Center(
              child: eventListViewModel.isLoading
                  ? CircularProgressIndicator()
                  : ListView.builder(
                      itemCount: eventListViewModel.displayedEvents.length,
                      itemBuilder: (context, index) {
                        var event = eventListViewModel.displayedEvents[index];
                        return ListTile(
                          leading: Image.network(
                            'https://cdn-icons-png.flaticon.com/512/839/839888.png',
                            width: 50,
                            height: 50,
                            fit: BoxFit.cover,
                          ),
                          title: Row(
                            children: [
                              Expanded(
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(event.eventName),
                                    Text(
                                      DateFormat('yyyy-MM-dd')
                                          .format(event.eventDate),
                                      style: TextStyle(
                                          fontSize: 12, color: Colors.grey),
                                    ),
                                  ],
                                ),
                              ),
                              ElevatedButton(
                                onPressed: () {
                                  // Action de suppression ici
                                },
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.red,
                                ),
                                child: Text(
                                  'Supprimer',
                                  style: TextStyle(color: Colors.white),
                                ),
                              ),
                              SizedBox(width: 10),
                              ElevatedButton(
                                onPressed: () {
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                      builder: (context) =>
                                          DetailEventPage(event: event),
                                    ),
                                  );
                                },
                                child: Text('Détails'),
                              ),
                            ],
                          ),
                          subtitle: Text(event.eventLocation),
                        );
                      },
                    ),
            ),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          eventListViewModel.fetchEvents();
        },
        child: Icon(Icons.refresh),
      ),
      persistentFooterButtons: [
        TextButton(
          onPressed: () {
            _showCalendar(context);
          },
          child: Text('Ouvrir le calendrier'),
        ),
      ],
    );
  }

  void _showCalendar(BuildContext context) {
    var eventListViewModel =
        Provider.of<EventListViewModel>(context, listen: false);
    List<Event> events = eventListViewModel.events;

    Map<DateTime, List<Event>> eventsByDate = {};

    for (var event in events) {
      DateTime eventDate = event.eventDate;
      if (eventsByDate.containsKey(eventDate)) {
        eventsByDate[eventDate]!.add(event);
      } else {
        eventsByDate[eventDate] = [event];
      }
    }

    List<DateTime> eventDays = eventsByDate.keys.toList();

    DateTime? firstEventDate;
    DateTime? lastEventDate;

    if (events.isNotEmpty) {
      events.sort((a, b) => a.eventDate.compareTo(b.eventDate));
      firstEventDate = events.first.eventDate;
      lastEventDate = events.last.eventDate;
    }

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return Dialog(
          child: Container(
            padding: EdgeInsets.all(16.0),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                Text('Calendrier'),
                TableCalendar(
                  focusedDay: firstEventDate ??
                      (events.isNotEmpty
                          ? events[0].eventDate
                          : DateTime.now()),
                  firstDay: firstEventDate ?? DateTime.now(),
                  lastDay: lastEventDate ?? DateTime.now(),
                  eventLoader: (day) {
                    return eventsByDate[day] ?? [];
                  },
                  calendarStyle: CalendarStyle(
                    // Personnalisation de l'apparence du calendrier
                    outsideDaysVisible: true,
                    weekendTextStyle: TextStyle(color: Colors.black),
                    outsideTextStyle: TextStyle(color: Colors.grey),
                  ),
                  // Autres paramètres et événements
                  // ...
                ),
                SizedBox(height: 16),
                TextButton(
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                  child: Text('Fermer'),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}
