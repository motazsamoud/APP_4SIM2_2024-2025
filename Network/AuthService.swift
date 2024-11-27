import Foundation

class AuthService {
    static let shared = AuthService()

    private init() {}

    // Login
    func login(
        request: LoginRequest,
        completion: @escaping (Result<LoginResponse, Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/auth/login",
            method: "POST",
            body: try? JSONEncoder().encode(request)
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    // Signup
    func signup(
        request: SignupRequest,
        completion: @escaping (Result<SignupResponse, Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/auth/signup",
            method: "POST",
            body: try? JSONEncoder().encode(request)
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }
        
        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    // Change Password
    func changePassword(
        request: ChangePasswordRequest,
        completion: @escaping (Result<ChangePasswordResponse, Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/auth/change-password",
            method: "PUT",
            body: try? JSONEncoder().encode(request)
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        // Appel à performRequest en utilisant ChangePasswordResponse pour gérer la réponse vide
        NetworkManager.shared.performRequest(request: urlRequest) { (result: Result<ChangePasswordResponse, Error>) in
            completion(result) // Passez directement le résultat à la completion
        }
    }
// Forgot Password
    func forgotPassword(
        request: ForgotPasswordRequest,
        completion: @escaping (Result<ForgotPasswordResponse, Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/auth/forgot-password",
            method: "POST",
            body: try? JSONEncoder().encode(request)
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        // Appel à performRequest en utilisant ForgotPasswordResponse pour gérer la réponse vide
        NetworkManager.shared.performRequest(request: urlRequest) { (result: Result<ForgotPasswordResponse, Error>) in
            completion(result) // Passez directement le résultat à la completion
        }
    }



    
    // Activité routes
    func getActivitesRecyclage(completion: @escaping (Result<[ActiviteRecyclage], Error>) -> Void) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/activite",
            method: "GET"
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    func getActiviteRecyclageByUser(id: String, completion: @escaping (Result<[ActiviteRecyclage], Error>) -> Void) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/activite/get/\(id)",
            method: "GET"
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    func addActiviteRecyclage(
        activite: ActiviteRecyclage,
        completion: @escaping (Result<ActiviteRecyclage, Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/activite/add",
            method: "POST",
            body: try? JSONEncoder().encode(activite)
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    func updateActivite(
        id: String,
        activite: ActiviteRecyclage,
        completion: @escaping (Result<ActiviteRecyclage, Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/activite/update/\(id)",
            method: "PATCH",
            body: try? JSONEncoder().encode(activite)
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    func deleteActivite(
        id: String,
        completion: @escaping (Result<ActiviteRecyclage, Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/activite/delete/\(id)",
            method: "DELETE"
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    func getMaterialStatistics(completion: @escaping (Result<[MaterialStatistics], Error>) -> Void) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/activite/statistics/material",
            method: "GET"
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    func getActiviteById(
        activiteId: String,
        completion: @escaping (Result<ActiviteRecyclage, Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/activite/\(activiteId)",
            method: "GET"
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    func getAllRatingsForActivite(
        activiteId: String,
        completion: @escaping (Result<[ActiviteRating], Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/ratings/activite/\(activiteId)",
            method: "GET"
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }

    func addRatingActivite(
        rating: ActiviteRating,
        completion: @escaping (Result<ActiviteRating, Error>) -> Void
    ) {
        guard let urlRequest = NetworkManager.shared.createRequest(
            endpoint: "/rating/add",
            method: "POST",
            body: try? JSONEncoder().encode(rating)
        ) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        NetworkManager.shared.performRequest(request: urlRequest, completion: completion)
    }
}
