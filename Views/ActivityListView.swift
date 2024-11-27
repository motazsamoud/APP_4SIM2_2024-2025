import SwiftUI

struct ActivityListView: View {
    var body: some View {
        ZStack {
            // Fond noir pour l'ensemble de l'écran
            Color.black.edgesIgnoringSafeArea(.all)
            
            VStack(spacing: 20) {
                // Image en haut (background)
                Image("top_background")
                    .resizable()
                    .scaledToFill()
                    .frame(height: 200)
                    .clipped()
                
                // Section principale contenant une liste (similaire à RecyclerView)
                Rectangle()
                    .fill(Color.white)
                    .cornerRadius(10)
                    .frame(height: 300)
                    .overlay(
                        ScrollView {
                            VStack {
                                Text("Recyclage Activities")
                                    .font(.headline)
                                    .padding()
                                // Ajoutez vos activités ici
                                ForEach(0..<10) { _ in
                                    HStack {
                                        Text("Activity Item")
                                            .foregroundColor(.black)
                                        Spacer()
                                    }
                                    .padding()
                                    .background(Color.gray.opacity(0.1))
                                    .cornerRadius(8)
                                    .padding(.horizontal, 10)
                                }
                            }
                        }
                    )
                    .padding(.horizontal, 16)
                
                // Texte informatif
                Text("Need to update your password? Change it here.")
                    .foregroundColor(Color.orange)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 16)
                
                // Boutons limités en taille
                ScrollView { // Utilisé pour scroller si nécessaire
                    VStack(spacing: 10) {
                        CustomButton(title: "Commandes Vocales")
                        CustomButton(title: "Export PDF")
                        CustomButton(title: "Stats")
                        CustomButton(title: "My Activities")
                        CustomButton(title: "Add Activity")
                    }
                    .padding(.horizontal, 16)
                }
                .frame(maxHeight: 200) // Limiter la hauteur totale des boutons
            }
        }
    }
}

// Bouton personnalisé
struct CustomButton: View {
    var title: String
    var body: some View {
        Button(action: {
            print("\(title) button tapped")
        }) {
            Text(title)
                .frame(maxWidth: .infinity, minHeight: 40, maxHeight: 50) // Limiter la taille des boutons
                .padding()
                .background(Color.orange)
                .foregroundColor(.white)
                .cornerRadius(8)
        }
    }
}

struct ActivityListView_Previews: PreviewProvider {
    static var previews: some View {
        ActivityListView()
    }
}
