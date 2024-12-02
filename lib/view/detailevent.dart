import 'package:flutter/material.dart';
import 'package:dashbord/models/Event.dart';
import 'package:intl/intl.dart';

class DetailEventPage extends StatelessWidget {
  final Event event;

  const DetailEventPage({Key? key, required this.event}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Détails de l\'événement'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              event.eventName,
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 10),
            Text(
              'Date: ${DateFormat('yyyy-MM-dd').format(event.eventDate)}',
              style: TextStyle(fontSize: 18),
            ),
            SizedBox(height: 10),
            Text(
              'Lieu: ${event.eventLocation}',
              style: TextStyle(fontSize: 18),
            ),
            SizedBox(height: 10),
            Text(
              'description: ${event.eventDescription}',
              style: TextStyle(fontSize: 18),
            ),
            SizedBox(height: 10),
            Text(
              'groupe WhatsApp: ${event.textwhats}',
              style: TextStyle(fontSize: 18),
            ),
            // Ajoutez d'autres détails de l'événement ici selon votre modèle Event
          ],
        ),
      ),
    );
  }
}
