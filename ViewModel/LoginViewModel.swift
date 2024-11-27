import Foundation
import SwiftUI

class LoginViewModel: ObservableObject {
    // Champs utilisateur
    @Published var email = ""
    @Published var password = ""
    
    // Réponses et états
    @Published var loginResponse: LoginResponse?
    @Published var errorMessage: String?
    @Published var isLoading = false
    @Published var isLoggedIn = false // Signal que l'utilisateur est connecté
    
    // Gestion du login
    func login() {
        guard !email.isEmpty, !password.isEmpty else {
            self.errorMessage = "Please enter both email and password"
            return
        }
        
        let loginRequest = LoginRequest(email: email, password: password)
        
        self.isLoading = true
        
        AuthService.shared.login(request: loginRequest) { result in
            DispatchQueue.main.async {
                self.isLoading = false
                switch result {
                case .success(let response):
                    self.loginResponse = response
                    self.errorMessage = nil
                    self.isLoggedIn = true // Connexion réussie
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }
    
    // Gestion du "Forgot Password"
    func forgotPassword() {
        guard !email.isEmpty else {
            self.errorMessage = "Please enter your email."
            return
        }
        
        self.isLoading = true
        
        let forgotPasswordRequest = ForgotPasswordRequest(email: email)
        
        AuthService.shared.forgotPassword(request: forgotPasswordRequest) { result in
            DispatchQueue.main.async {
                self.isLoading = false
                switch result {
                case .success:
                    self.errorMessage = "A reset link has been sent to your email."
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }
}
