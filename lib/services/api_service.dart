import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/login_request.dart';
import '../models/login_response.dart';
import '../models/signup_request.dart';
import '../models/signup_response.dart';

class ApiService {
  static const String baseUrl = 'http://192.168.1.11:3000';

  Future<LoginResponse> login(String email, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/login'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(LoginRequest(email: email, password: password).toJson()),
    );

    print('Response status: ${response.statusCode}');
    print('Response body: ${response.body}');

    if (response.statusCode == 200) {
      return LoginResponse.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to login: ${response.body}');
    }
  }

  Future<SignupResponse> signup(SignupRequest request) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/signup'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(request.toJson()),
    );

    if (response.statusCode == 200) {
      return SignupResponse.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to signup: ${response.body}');
    }
  }

  // Ajoutez d'autres méthodes pour les autres routes ici
}