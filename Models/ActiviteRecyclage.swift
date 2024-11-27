import Foundation

struct ActiviteRecyclage: Identifiable, Codable {
    var id: String? // Le champ _id en Kotlin devient id en Swift
    var dateAndTime: String
    var quantity: Int
    var recyclableMaterial: String
    var user: String
    var image: String // URL de l'image
    
    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case dateAndTime
        case quantity
        case recyclableMaterial
        case user
        case image
    }
}
