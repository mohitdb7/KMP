//
//  DecomposeProductsListView.swift
//  iosApp
//
//  Created by Mohit Dubey on 03/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp

struct DecomposeProductsListViewCompose: UIViewControllerRepresentable {
    let rootComponent: RootComponent
    func makeUIViewController(context: Context) -> some UIViewController {
        DecomposeProductListViewControllerKt.DecomposeProductListViewController(rootComponent: rootComponent)
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        
    }
}

struct DecomposeProductsListView: View {
    let rootCompoenent: RootComponent
    
    var body: some View {
        DecomposeProductsListViewCompose(rootComponent: rootCompoenent)
    }
}
