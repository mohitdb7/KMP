package decompose.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.seiko.imageloader.rememberImagePainter
import model.ProductsParentModelItem

@Composable
fun ListContent(
    component: ListComponent
) {
    val products = component.model.subscribeAsState()
    ProductListContent(products) {
        component.onItemClicked(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListContent(products: State<ListComponent.Model>, onItemClick: (ProductsParentModelItem) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var searchHistory = remember { mutableStateListOf("dummy") }

    BoxWithConstraints {
        val scope = this
        val maxWidth = scope.maxWidth

        var cols = 2
        var modifier = Modifier.fillMaxWidth()
        if (maxWidth > 840.dp) {
            cols = 3
            modifier = Modifier.widthIn(max= 1080.dp)
        }

        val scrollState = rememberLazyGridState()
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    "Products Catalog",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Left
                )
                Spacer(Modifier.weight(1f))
            }

            SearchBar(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                query = searchText,
                onActiveChange = {
                    isSearchActive = it
                },
                onQueryChange = {
                    searchText = it
                },
                onSearch = {
                    searchHistory.add(searchText)
                    isSearchActive = false
                },
                active = isSearchActive,
                placeholder = {
                    Text("Search Product")
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                },
                trailingIcon = {
                    if (isSearchActive) {
                        Icon(Icons.Default.Close, contentDescription = "Close Icon", modifier = Modifier.clickable {
                            if (searchText.isNotEmpty()) {
                                searchText = ""
                            } else {
                                isSearchActive = false
                            }
                        })
                    }
                }) {
                searchHistory.forEach { text ->
                    Row(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
                        Icon(Icons.Default.Search, contentDescription = "Search history")
                        Text(text)
                    }
                }
            }

            LazyVerticalGrid(columns = GridCells.Fixed(cols),
                state = scrollState,
                contentPadding = PaddingValues(16.dp)
            ) {
                items(products.value.items, key = { product -> product.id.toString() }) {product ->
                    Card(shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.padding(8.dp).fillMaxWidth().clickable {
                                                                                   onItemClick(product)
                        },
                        elevation = 2.dp) {
                        Column(verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            val painter = rememberImagePainter(url = product.image.toString())
                            Image(painter, modifier = Modifier
                                .height(138.dp)
                                .padding(8.dp),
                                contentDescription = product.title)

                            Spacer(modifier.height(8.dp))

                            Text(product.title.toString(),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(8.dp)
                                    .heightIn(min=40.dp))

                            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .heightIn(min = 40.dp),
                                    contentAlignment = Alignment.Center) {
                                    Text(
                                        "$ ${product.price.toString()}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Button(onClick = {

                                }, modifier = Modifier.padding(horizontal = 8.dp)) {
                                    Text("Add")
                                }
                            }

                            Spacer(modifier = Modifier.heightIn(min = 8.dp))
                        }
                    }
                }
            }
        }
    }
}
