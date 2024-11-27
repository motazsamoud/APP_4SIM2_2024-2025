import Foundation

struct MaterialStatistics: Identifiable, Codable {
    var id: String
    var count: Int // Agrégation du nombre
    
    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case count
    }
}
