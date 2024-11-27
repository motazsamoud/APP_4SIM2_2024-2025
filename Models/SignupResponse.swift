struct SignupResponse: Codable {
    let userId: String
    let accessToken: String
    let refreshToken: String
}
