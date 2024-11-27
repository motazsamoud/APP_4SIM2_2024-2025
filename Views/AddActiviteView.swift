import SwiftUI

struct AddActiviteView: View {
    @State private var dateAndTime: String = ""
    @State private var recyclableMaterial: String = ""
    @State private var quantity: String = ""
    @State private var selectedImage: UIImage? = nil
    @State private var isImagePickerPresented: Bool = false

    var body: some View {
        ZStack {
            // Couleur de fond noire pour l'écran entier
            Color.black.edgesIgnoringSafeArea(.all)
            
            VStack {
                // Image en haut
                Image("top_background")
                    .resizable()
                    .scaledToFill()
                    .frame(height: 200)
                    .clipped()
                
                Spacer().frame(height: 20)
                
                // Champs de saisie
                VStack(spacing: 15) {
                    CustomTextField(icon: "", placeholder: "Time", text: $dateAndTime)
                    CustomTextField(icon: "", placeholder: "Recyclable Material", text: $recyclableMaterial)
                    CustomTextField(icon: "", placeholder: "Quantity", text: $quantity)
                }
                .padding(.horizontal, 20)
                
                // Bouton pour importer une image
                Button(action: {
                    isImagePickerPresented.toggle()
                }) {
                    Text("Import Image")
                        .fontWeight(.bold)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.orange)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
                .padding(.horizontal, 20)
                .sheet(isPresented: $isImagePickerPresented) {
                    ImagePicker(image: $selectedImage)
                }
                
                // Image sélectionnée
                if let image = selectedImage {
                    Image(uiImage: image)
                        .resizable()
                        .scaledToFill()
                        .frame(height: 200)
                        .clipped()
                        .cornerRadius(8)
                        .padding(.horizontal, 20)
                } else {
                    Rectangle()
                        .fill(Color.gray.opacity(0.5))
                        .frame(height: 200)
                        .cornerRadius(8)
                        .padding(.horizontal, 20)
                }
                
                // Bouton pour créer une activité
                Button(action: {
                    // Action pour créer une activité
                }) {
                    Text("Create Activity")
                        .fontWeight(.bold)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.orange)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
                .padding(.horizontal, 20)
                
                Spacer()
            }
        }
    }
}

// Composant personnalisé pour les champs de saisie
struct CustomTextField1: View {
    var icon: String? = nil
    var placeholder: String
    @Binding var text: String
    
    var body: some View {
        HStack {
            if let icon = icon {
                Image(systemName: icon)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 20, height: 20)
            }
            TextField(placeholder, text: $text)
                .foregroundColor(.white) // Couleur du texte saisi
                .padding()
                .background(Color.gray.opacity(0.2)) // Fond gris clair avec opacité
                .cornerRadius(8) // Coins arrondis
                .placeholder(when: text.isEmpty) {
                    Text(placeholder)
                        .foregroundColor(.orange) // Couleur bien visible du placeholder
                }
        }
        .overlay(Rectangle().frame(height: 1).foregroundColor(.yellow)) // Bordure en bas
        .padding(.horizontal)
    }
}

// Composant pour la sélection d'image
struct ImagePicker: UIViewControllerRepresentable {
    @Binding var image: UIImage?

    func makeUIViewController(context: Context) -> UIImagePickerController {
        let picker = UIImagePickerController()
        picker.delegate = context.coordinator
        picker.sourceType = .photoLibrary
        return picker
    }

    func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {}

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    class Coordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
        var parent: ImagePicker

        init(_ parent: ImagePicker) {
            self.parent = parent
        }

        func imagePickerController(
            _ picker: UIImagePickerController,
            didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
            if let image = info[.originalImage] as? UIImage {
                parent.image = image
            }
            picker.dismiss(animated: true)
        }
    }
}

struct AddActiviteView_Previews: PreviewProvider {
    static var previews: some View {
        AddActiviteView()
    }
}
