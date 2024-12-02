class Event {
  final String eventName;
  final DateTime eventDate;
  final String textwhats;
  final String eventLocation;
  final String eventDescription;
  final bool isFavorites;
  final String imageURL;

  Event({
    required this.eventName,
    required this.eventDate,
    required this.textwhats,
    required this.eventLocation,
    required this.eventDescription,
    required this.isFavorites,
    required this.imageURL,
  });

  factory Event.fromJson(Map<String, dynamic> json) {
    return Event(
      eventName: json['eventName'] ?? '',
      eventDate: DateTime.tryParse(json['eventDate'] ?? '') ?? DateTime.now(),
      textwhats: json['textwhats'] ?? '',
      eventLocation: json['eventLocation'] ?? '',
      eventDescription: json['eventDescription'] ?? '',
      isFavorites: json['isFavorites'] ?? false,
      imageURL: json['imageURL'] ?? '',
    );
  }
}
