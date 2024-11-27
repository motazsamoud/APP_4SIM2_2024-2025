import Foundation

class NetworkManager {
    static let shared = NetworkManager()
    private let baseURL = "http://localhost:3000"

    private init() {}

    func createRequest(
        endpoint: String,
        method: String,
        body: Data? = nil,
        headers: [String: String] = [:]
    ) -> URLRequest? {
        guard let url = URL(string: "\(baseURL)\(endpoint)") else { return nil }
        var request = URLRequest(url: url)
        request.httpMethod = method
        request.httpBody = body
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")

        headers.forEach { key, value in
            request.setValue(value, forHTTPHeaderField: key)
        }
        return request
    }

    func performRequest<T: Decodable>(
        request: URLRequest,
        completion: @escaping (Result<T, Error>) -> Void
    ) {
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }

            guard let response = response as? HTTPURLResponse else {
                completion(.failure(NSError(domain: "InvalidResponse", code: -1, userInfo: nil)))
                return
            }

            // Si la réponse est un code 204 (No Content), traiter le cas spécial
            if response.statusCode == 204 {
                // Vérifier si T est Void, auquel cas on renvoie un succès avec Void
                if T.self == Void.self {
                    completion(.success(() as! T)) // Force un cast vers Void
                } else {
                    // Si T n'est pas Void, c'est probablement un modèle de réponse attendu,
                    // mais il n'y a pas de contenu, donc erreur.
                    completion(.failure(NSError(domain: "NoContent", code: 204, userInfo: [NSLocalizedDescriptionKey: "No content returned"])))
                }
                return
            }

            // Sinon, on traite la réponse comme des données JSON
            guard let data = data else {
                completion(.failure(NSError(domain: "NoData", code: -1, userInfo: nil)))
                return
            }

            do {
                let decodedResponse = try JSONDecoder().decode(T.self, from: data)
                completion(.success(decodedResponse))
            } catch {
                completion(.failure(error))
            }
        }
        task.resume()
    }





}
