import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.seiko.imageloader.LocalImageLoader
import decompose.root.DefaultRootComponent
import decompose.root.RootComponent
import decompose.root.RootContent
import network.ProductClientService
import viewmodel.ProductsViewModel

fun DecomposeProductListViewController(rootComponent: RootComponent) = ComposeUIViewController {
    CompositionLocalProvider(
        LocalImageLoader provides remember { generateImageLoader() },
    ) {
        RootContent(rootComponent)
    }
}

class LifeCycleCallbacksImpl: Lifecycle.Callbacks {
    override fun onCreate() {
        super.onCreate()
        println("onCreate")
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onStop() {
        super.onStop()
        println("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }
}