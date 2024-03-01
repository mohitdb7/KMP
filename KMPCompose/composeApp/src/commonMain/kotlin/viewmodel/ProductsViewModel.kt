package viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.ProductsParentModelItem
import network.ProductClientService

class ProductsViewModel(productService: ProductClientService): ViewModel() {
    private val _products = MutableStateFlow<List<ProductsParentModelItem>>(listOf())
    val products = _products.asStateFlow()

    init {
        viewModelScope.launch {
            productService.getProducts().collect {product ->
                _products.update { it + product }
            }
        }
    }
}