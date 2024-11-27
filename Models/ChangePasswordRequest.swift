import Foundation

struct ChangePasswordRequest: Codable {
    let oldPassword: String
    let newPassword: String
}
