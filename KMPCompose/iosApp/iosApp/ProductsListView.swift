//
//  ProductsListView.swift
//  iosApp
//
//  Created by Mohit Dubey on 01/03/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp

struct ProductsListViewCompose: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> some UIViewController {
        ProductsViewControllerKt.ProductsViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        
    }
}

struct ProductsListView: View {
    var body: some View {
        ProductsListViewCompose()
    }
}
