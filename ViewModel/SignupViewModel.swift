import SwiftUI
import Combine
import Foundation

class SignupViewModel: ObservableObject {
    @Published var name: String = ""
    @Published var email: String = ""
    @Published var password: String = ""
    @Published var isLoading: Bool = false
    @Published var errorMessage: String? = nil
    @Published var signupResponse: SignupResponse? = nil
    
    private var cancellables = Set<AnyCancellable>()
    
    func signup() {
        isLoading = true
        errorMessage = nil
        
        let request = SignupRequest(name: name, email: email, password: password)
        
        AuthService.shared.signup(request: request) { [weak self] result in
            DispatchQueue.main.async {
                self?.isLoading = false
                switch result {
                case .success(let response):
                    self?.signupResponse = response
                case .failure(let error):
                    self?.errorMessage = error.localizedDescription
                }
            }
        }
    }
}
