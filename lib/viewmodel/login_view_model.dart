import 'package:flutter/material.dart';
import '../models/login_response.dart';
import '../services/api_service.dart';


class LoginViewModel extends ChangeNotifier {
  final ApiService apiService;

  LoginViewModel({required this.apiService});

  LoginResponse? _loginResponse;
  String? _errorMessage;

  LoginResponse? get loginResponse => _loginResponse;
  String? get errorMessage => _errorMessage;

  Future<void> login(String email, String password) async {
    try {
      _loginResponse = await apiService.login(email, password);
      _errorMessage = null;
    } catch (e) {
      _loginResponse = null;
      _errorMessage = e.toString();
    }
    notifyListeners();
  }
}