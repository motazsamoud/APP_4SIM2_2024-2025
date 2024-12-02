import 'package:flutter/material.dart';
import '../models/signup_request.dart';
import '../models/signup_response.dart';
import '../services/api_service.dart';

class SignupViewModel extends ChangeNotifier {
  final ApiService apiService;

  SignupViewModel({required this.apiService});

  SignupResponse? _signupResponse;
  String? _errorMessage;

  SignupResponse? get signupResponse => _signupResponse;
  String? get errorMessage => _errorMessage;

  Future<void> signup(String name, String email, String password) async {
    try {
      final request = SignupRequest(name: name, email: email, password: password);
      _signupResponse = await apiService.signup(request);
      _errorMessage = null;
    } catch (e) {
      _signupResponse = null;
      _errorMessage = e.toString();
    }
    notifyListeners();
  }
}