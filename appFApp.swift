//
//  appFApp.swift
//  appF
//
//  Created by Motaz on 21/11/2024.
//

import SwiftUI

@main
struct appFApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            Login()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
