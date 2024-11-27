import Foundation

struct ActiviteRating: Identifiable, Codable {
    var id: String? // Le champ _id en Kotlin devient id en Swift
    var activite: String
    var comment: String
    var rating: Float
    var user: String
    
    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case activite
        case comment
        case rating
        case user
    }
}
