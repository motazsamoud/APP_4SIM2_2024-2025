import SwiftUI

struct ChangePasswordView: View {
    @State private var oldPassword: String = ""
    @State private var newPassword: String = ""
    
    var body: some View {
        ZStack {
            // Fond noir pour l'ensemble de l'écran
            Color.black.edgesIgnoringSafeArea(.all)
            
            VStack {
                // Image de fond en haut
                Image("top_background")
                    .resizable()
                    .scaledToFill()
                    .frame(height: 200)
                    .clipped()
                
                ScrollView {
                    VStack(spacing: 30) {
                        // Titre "Change Password"
                        Text("Change Password")
                            .font(.system(size: 40, weight: .bold))
                            .foregroundColor(.white)
                            .padding(.top, 50)
                        
                        // Champ pour l'ancien mot de passe
                        CustomSecureField(icon: "password", placeholder: "Old Password", text: $oldPassword)
                        
                        // Champ pour le nouveau mot de passe
                        CustomSecureField(icon: "password", placeholder: "New Password", text: $newPassword)
                        
                        // Bouton de confirmation
                        HStack {
                            Spacer()
                            Button(action: {
                                // Action pour changer le mot de passe
                                print("Password changed")
                            }) {
                                Image("btn_login")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 80, height: 80)
                                    .clipShape(Circle())
                            }
                        }
                        .padding(.horizontal)
                        
                        // Texte pour rediriger vers Signup
                        Text("Already have an account? Signup")
                            .foregroundColor(Color.yellow)
                            .fontWeight(.bold)
                            .padding(.top, 30)
                    }
                    .padding(.horizontal, 16)
                }
            }
        }
    }
}

// Champ de texte personnalisé pour les mots de passe
struct CustomSecureField: View {
    var icon: String
    var placeholder: String
    @Binding var text: String
    
    var body: some View {
        HStack {
            Image(icon) // Icône du champ
                .resizable()
                .scaledToFit()
                .frame(width: 20, height: 20)
                .padding(.leading, 10)
            
            SecureField(placeholder, text: $text)
                .placeholder(when: text.isEmpty) {
                    Text(placeholder).foregroundColor(.gray)
                }
                .foregroundColor(.white)
                .padding(.vertical, 12)
        }
        .background(
            RoundedRectangle(cornerRadius: 8)
                .stroke(Color.white, lineWidth: 1)
        )
        .padding(.horizontal)
    }
}

// Extension pour gérer les placeholders
extension View {
    func placeholderr<Content: View>(
        when shouldShow: Bool,
        alignment: Alignment = .leading,
        @ViewBuilder placeholder: () -> Content) -> some View {
        ZStack(alignment: alignment) {
            placeholder().opacity(shouldShow ? 1 : 0)
            self
        }
    }
}

// Aperçu de l'interface
struct ChangePasswordView_Previews: PreviewProvider {
    static var previews: some View {
        ChangePasswordView()
    }
}
