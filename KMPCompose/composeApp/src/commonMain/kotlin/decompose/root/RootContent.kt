package decompose.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import decompose.detail.DetailContent
import decompose.list.ListContent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(slide())
    ) {
        val child = it.instance

        when(child) {
            is RootComponent.Child.ListChild -> ListContent(child.component)
            is RootComponent.Child.DetailChild -> DetailContent(child.component)
        }
    }
}