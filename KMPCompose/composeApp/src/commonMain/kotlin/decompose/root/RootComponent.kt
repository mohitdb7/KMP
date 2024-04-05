package decompose.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import decompose.detail.DefaultProductDetailComponent
import decompose.detail.DetailComponent
import kotlinx.serialization.Serializable
import decompose.list.DefaultProductListComponent
import decompose.list.ListComponent
import model.ProductsParentModelItem
import viewmodel.ProductsViewModel

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>
    fun onBackPressed()

    sealed class Child {
        class ListChild(val component: ListComponent): Child()

        class DetailChild(val component: DetailComponent): Child()
    }
}

class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val listViewModel: ProductsViewModel
): RootComponent, ComponentContext by componentContext {
    @Serializable
    sealed interface Config {
        @Serializable
        data object List: Config
        @Serializable
        data class Detail(val item: ProductsParentModelItem): Config
    }

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.List,
        handleBackButton = true,
        childFactory = ::childFactory
    )

    private fun childFactory(config: Config, componentContext: ComponentContext): RootComponent.Child {
        return when (config) {
            is Config.List -> RootComponent.Child.ListChild(
                DefaultProductListComponent(componentContext, listViewModel) { item ->
                    navigation.push(Config.Detail(item))
                }
            )

            is Config.Detail -> RootComponent.Child.DetailChild(
                DefaultProductDetailComponent(componentContext, config.item) {
                    navigation.pop()
                }
            )
        }
    }

    override fun onBackPressed() {

    }

}