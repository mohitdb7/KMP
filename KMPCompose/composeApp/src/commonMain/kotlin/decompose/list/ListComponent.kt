package decompose.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.ProductsParentModelItem
import viewmodel.ProductsViewModel

interface ListComponent {
    data class Model(
        val items: List<ProductsParentModelItem>
    )

    val model: Value<Model>
    fun onItemClicked(item: ProductsParentModelItem)
}

class DefaultProductListComponent(
    private val componentContext: ComponentContext,
    private val listViewModel: ProductsViewModel,
    private val selectedItem: (item: ProductsParentModelItem) -> Unit
): ListComponent, ComponentContext by componentContext {
    private val _model = MutableValue<ListComponent.Model>(ListComponent.Model(items = emptyList()))
    override val model: Value<ListComponent.Model> = _model

    override fun onItemClicked(item: ProductsParentModelItem) {
        selectedItem(item)
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            listViewModel.products.collect {
                _model.value = ListComponent.Model(items = it)
            }
        }
    }
}