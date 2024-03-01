import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CountView() {
    var count by remember {
        mutableStateOf(0)
    }
    var allItems = arrayOf("First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eighth")

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                count++
            }) {
                Text("Count is $count")
            }
            LazyColumn { items(allItems.toList()) { stringItem ->
                Text(stringItem)
            } }
        }
    }
}

@Composable
fun MessageList(messages: List<String>) {
    Column {
        messages.forEach { message ->
            Text(message)
        }
    }
}