import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    var rootHolder: RootHolder {
        appDelegate.rootHolder
    }
    
	var body: some Scene {
		WindowGroup {
            ContentView(rootCompoenent: rootHolder.root)
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification), perform: { _ in
                    debugPrint("Swift onResume")
                    LifecycleRegistryExtKt.resume(rootHolder.lifecycle)
                })
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification), perform: { _ in
                    debugPrint("Swift onPause")
                    LifecycleRegistryExtKt.pause(rootHolder.lifecycle)
                })
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didEnterBackgroundNotification), perform: { _ in
                    debugPrint("Swift onStop")
                    LifecycleRegistryExtKt.stop(rootHolder.lifecycle)
                })
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willTerminateNotification), perform: { _ in
                    debugPrint("Swift onDestroy")
                    LifecycleRegistryExtKt.destroy(rootHolder.lifecycle)
                    rootHolder.lifecycle.unsubscribe(callbacks: rootHolder.lifecycleImpl)
                })
		}
	}
}

class RootHolder: ObservableObject {
    private let _lifecycleImpl = LifeCycleCallbacksImpl()
    var lifecycleImpl: LifecycleCallbacks {
        _lifecycleImpl
    }
    
    let lifecycle: LifecycleRegistry
    let root: RootComponent
    
    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        
        let listViewModel = ProductsViewModel(productService: ProductClientService())
        
        root = DefaultRootComponent(componentContext: DefaultComponentContext(lifecycle: lifecycle),
                                    listViewModel: listViewModel)
        
        lifecycle.subscribe(callbacks: lifecycleImpl)
        LifecycleRegistryExtKt.create(lifecycle)
        
    }
    
    deinit {
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    let rootHolder: RootHolder = RootHolder()
}
