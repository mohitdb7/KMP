import androidx.compose.foundation.Image
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import network.ProductClientService
import org.jetbrains.compose.resources.ExperimentalResourceApi
import viewmodel.ProductsViewModel


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProductsCompose() {
    MaterialTheme {
        ProductComponent(
            productViewModel = ProductsViewModel(
            productService = ProductClientService()
            )
        )
    }
}

@Composable
fun ProductComponent(productViewModel: ProductsViewModel) {
    val products = productViewModel.products.collectAsState()

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

            LazyVerticalGrid(columns = GridCells.Fixed(cols),
                state = scrollState,
                contentPadding = PaddingValues(16.dp)
            ) {
                items(products.value, key = { product -> product.id.toString() }) {product ->
                    Card(shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
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