import SwiftUI

struct ActiviteRecyclageListView: View {
    // Un tableau fictif pour l'exemple, remplacez-le par vos données réelles
    let activites = ["Activité 1", "Activité 2", "Activité 3", "Activité 4", "Activité 5"]
    
    var body: some View {
        ZStack {
            // Couleur de fond noire avec image de fond
            Color.black.edgesIgnoringSafeArea(.all)
            
            VStack {
                // Image en haut (arrière-plan)
                Image("top_background") // Assurez-vous que l'image existe dans votre projet
                    .resizable()
                    .scaledToFill()
                    .frame(height: 200)
                    .clipped()
                
                Spacer().frame(height: 20)
                
                // RecyclerView (Liste)
                List(activites, id: \.self) { activite in
                    HStack {
                        Text(activite)
                            .fontWeight(.bold)
                            .foregroundColor(.white) // Couleur du texte
                        Spacer()
                    }
                    .padding()
                    .background(Color.orange.opacity(0.2)) // Fond léger orange pour chaque élément
                    .cornerRadius(8)
                    .padding(.horizontal)
                }
                .background(Color.clear) // Rendre le fond de la liste transparent pour voir l'arrière-plan
            }
        }
    }
}

struct ActiviteRecyclageListView_Previews: PreviewProvider {
    static var previews: some View {
        ActiviteRecyclageListView()
    }
}
