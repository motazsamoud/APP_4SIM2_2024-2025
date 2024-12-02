import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:dashbord/models/Event.dart';
import 'dart:io';

class EventService {
  Future<List<Event>> getEvents() async {
    try {
      final response = await http
          .get(Uri.parse('https://ecolink.onrender.com/api/evenements'));

      if (response.statusCode == 200) {
        final Map<String, dynamic> responseData = json.decode(response.body);
        final List<dynamic> data = responseData['list'];
        List<Event> events =
            data.map<Event>((item) => Event.fromJson(item)).toList();
        return events;
      } else {
        throw Exception('Failed to load events');
      }
    } catch (e) {
      debugPrint('Error: $e');
      throw Exception('Network error bro');
    }
  }
}
