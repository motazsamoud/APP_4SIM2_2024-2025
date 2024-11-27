import SwiftUI

struct Login: View {
    @StateObject private var viewModel = LoginViewModel() // Utilisation de ViewModel avec SwiftUI
    @State private var showSignup = false // Variable pour gérer la navigation vers Signup
    
    var body: some View {
        NavigationStack {
            ZStack {
                Color.black.edgesIgnoringSafeArea(.all)
                
                VStack(spacing: 30) {
                    // Header avec image de fond et titre
                    ZStack {
                        Image("top_background") // Remplacez par l'asset "top_background.png"
                            .resizable()
                            .scaledToFill()
                            .frame(maxWidth: .infinity)
                            .clipped()
                        
                        Text("Welcome back")
                            .font(.largeTitle)
                            .fontWeight(.bold)
                            .foregroundColor(.white)
                            .padding(.top, 30)
                    }
                    .frame(height: 200)
                    
                    Spacer()
                    
                    // Formulaire d'authentification
                    VStack(alignment: .leading, spacing: 20) {
                        // Champ email
                        HStack {
                            Image("email") // Remplacez par l'asset "email.png"
                                .resizable()
                                .scaledToFit()
                                .frame(width: 20, height: 20)
                            
                            ZStack(alignment: .leading) {
                                if viewModel.email.isEmpty {
                                    Text("Email")
                                        .foregroundColor(.white.opacity(0.7))
                                }
                                TextField("", text: $viewModel.email)
                                    .foregroundColor(.white)
                                    .keyboardType(.emailAddress)
                                    .autocapitalization(.none)
                                    .padding(.leading, 5)
                            }
                        }
                        .padding()
                        .background(Color.gray.opacity(0.2))
                        .cornerRadius(10)
                        
                        // Champ mot de passe
                        HStack {
                            Image("password") // Remplacez par l'asset "password.png"
                                .resizable()
                                .scaledToFit()
                                .frame(width: 20, height: 20)
                            
                            ZStack(alignment: .leading) {
                                if viewModel.password.isEmpty {
                                    Text("Password")
                                        .foregroundColor(.white.opacity(0.7))
                                }
                                SecureField("", text: $viewModel.password)
                                    .foregroundColor(.white)
                                    .padding(.leading, 5)
                            }
                        }
                        .padding()
                        .background(Color.gray.opacity(0.2))
                        .cornerRadius(10)
                        
                        // Bouton login
                        HStack {
                            Spacer()
                            Button(action: {
                                viewModel.login()
                            }) {
                                Image("btn_login") // Remplacez par l'asset "btn_login.png"
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 80, height: 80)
                                    .padding()
                                    .cornerRadius(40)
                            }
                        }
                        .padding(.top, 10)
                    }
                    
                    Spacer()
                    
                    // Section d'actions supplémentaires
                    VStack(spacing: 10) {
                        // Bouton Google
                        Button(action: {
                            // Action pour Google
                        }) {
                            HStack {
                                Image("google") // Remplacez par l'asset "google.png"
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 20, height: 20)
                                Text("Signup with Google")
                                    .fontWeight(.bold)
                            }
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(Color.gray.opacity(0.2))
                            .foregroundColor(.white)
                            .cornerRadius(10)
                        }
                        
                        // Bouton Forgot Password
                        Button(action: {
                            viewModel.forgotPassword()
                        }) {
                            HStack {
                                Image("password") // Remplacez par l'asset "facebook.png"
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 20, height: 20)
                                Text("Forgot password")
                                    .fontWeight(.bold)
                            }
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(Color.gray.opacity(0.2))
                            .foregroundColor(.white)
                            .cornerRadius(10)
                        }
                    }
                    
                    Spacer()
                    
                    // Lien vers l'inscription
                    NavigationLink(destination: Signup(), isActive: $showSignup) {
                        Text("Are you a new user? Register")
                            .foregroundColor(.yellow)
                            .font(.footnote)
                            .underline()
                            .padding(.bottom, 10)
                    }
                    .simultaneousGesture(TapGesture().onEnded {
                        showSignup = true
                    })
                    
                    // Message d'erreur
                    if let errorMessage = viewModel.errorMessage {
                        Text(errorMessage)
                            .foregroundColor(.red)
                            .padding(.top, 10)
                    }
                    
                    // Indicateur de chargement
                    if viewModel.isLoading {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: .white))
                    }
                }
                .padding(.horizontal, 20)
            }
        }
    }
}

struct Login_Previews: PreviewProvider {
    static var previews: some View {
        Login()
    }
}
