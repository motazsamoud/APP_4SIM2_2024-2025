//
//  Signup.swift
//  PDM
//
//  Created by Amal on 15/11/2024.
//

import SwiftUI

struct Signup: View {
    @StateObject private var viewModel = SignupViewModel()
    
    var body: some View {
        ZStack {
            // Fond noir pour l'écran
            Color.black.edgesIgnoringSafeArea(.all)
            
            VStack {
                // Section "Create Account" avec un fond d'image
                ZStack {
                    Image("top_background")
                        .resizable()
                        .scaledToFill()
                        .frame(maxWidth: .infinity)
                        .clipped()
                    
                    Text("Create Account")
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .padding(.top, 30)
                }
                .frame(height: 200)
                
                Spacer().frame(height: 40)
                
                VStack(spacing: 20) {
                    CustomTextField(icon: "email", placeholder: "Email", text: $viewModel.email)
                    CustomTextField(icon: "password", placeholder: "Password", isSecure: true, text: $viewModel.password)
                    CustomTextField(icon: "name", placeholder: "Name", text: $viewModel.name)
                }
                .padding(.horizontal, 40)
                
                Spacer().frame(height: 40)
                
                // Bouton "Signup"
                HStack {
                    Spacer()
                    Button(action: {
                        viewModel.signup()
                    }) {
                        Image("btn_login")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 80, height: 80)
                            .clipShape(Circle())
                    }
                }
                
                Spacer()
                
                // Lien pour basculer sur Login
                Text("Already have an account? Login")
                    .foregroundColor(Color.yellow)
                    .padding(.bottom, 20)
                
                // Affichage des erreurs
                if let errorMessage = viewModel.errorMessage {
                    Text(errorMessage)
                        .foregroundColor(.red)
                        .padding(.top, 10)
                }
                
                // Indicateur de chargement
                if viewModel.isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: .white))
                        .padding(.top, 10)
                }
            }
        }
    }
}

struct CustomTextField: View {
    var icon: String
    var placeholder: String
    var isSecure: Bool = false
    @Binding var text: String
    
    var body: some View {
        HStack {
            Image(icon)
                .resizable()
                .scaledToFit()
                .frame(width: 20, height: 20)
            
            if isSecure {
                SecureField(placeholder, text: $text)
                    .foregroundColor(.white)
            } else {
                TextField(placeholder, text: $text)
                    .foregroundColor(.white)
            }
        }
        .padding()
        .background(Color.gray.opacity(0.2))
        .cornerRadius(10)
    }
}

extension View {
    func placeholder<Content: View>(
        when shouldShow: Bool,
        alignment: Alignment = .leading,
        @ViewBuilder placeholder: () -> Content
    ) -> some View {
        ZStack(alignment: alignment) {
            placeholder().opacity(shouldShow ? 1 : 0)
            self
        }
    }
}

struct Signup_Previews: PreviewProvider {
    static var previews: some View {
        Signup()
    }
}
