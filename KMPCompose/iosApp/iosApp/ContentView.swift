import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    let rootCompoenent: RootComponent
    
    var body: some View {
        VStack {
//            ComposeView()
//            CountView()
//            ProductsListView()
            DecomposeProductsListView(rootCompoenent: rootCompoenent)
        }.ignoresSafeArea(.keyboard)
    }
}
