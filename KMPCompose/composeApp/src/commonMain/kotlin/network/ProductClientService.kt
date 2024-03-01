package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import model.ProductsParentModelItem

class ProductClientService {
    private suspend fun getProductsFromApi(): List<ProductsParentModelItem> {
        val response = httpClient.get("https://fakestoreapi.com/products")
        return response.body<List<ProductsParentModelItem>>()
    }

    fun getProducts() = flow {
        emit(getProductsFromApi())
    }
}