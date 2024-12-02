class SignupResponse {
  final String userId;
  final String accessToken;
  final String refreshToken;

  SignupResponse({
    required this.userId,
    required this.accessToken,
    required this.refreshToken,
  });

  factory SignupResponse.fromJson(Map<String, dynamic> json) {
    return SignupResponse(
      userId: json['userId'],
      accessToken: json['accessToken'],
      refreshToken: json['refreshToken'],
    );
  }
}