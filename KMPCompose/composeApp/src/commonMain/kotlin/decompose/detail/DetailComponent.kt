package decompose.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.ProductsParentModelItem

interface DetailComponent {
    data class Model(
        val item: ProductsParentModelItem
    )

    val model: Value<Model>
    fun onBackPressed()
}

class DefaultProductDetailComponent(
    private val componentContext: ComponentContext,
    private val item: ProductsParentModelItem,
    private val onBackPress: () -> Unit
): DetailComponent, ComponentContext by componentContext {
    private val _model = MutableValue<DetailComponent.Model>(DetailComponent.Model(item = item))
    override val model: Value<DetailComponent.Model> = _model

    override fun onBackPressed() {
        onBackPress()
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            // Perform any NW operation if required
        }
    }
}