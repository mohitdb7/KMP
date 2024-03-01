//
//  CountView.swift
//  iosApp
//
//  Created by Mohit Dubey on 27/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp

struct CountViewCompose: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> some UIViewController {
        CountViewControllerKt.CountViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    }
}

struct CountView: View {
    var body: some View {
        CountViewCompose()
    }
}
