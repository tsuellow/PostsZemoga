package com.example.android.postszemoga.presentation.common_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DeleteFab(onClick: () -> Unit, icon:ImageVector=Icons.Filled.Delete) {
    var wasClicked by remember {
        mutableStateOf(false)
    }
    val rotation = animateFloatAsState(
        targetValue = if (wasClicked) 180f else 360f,
        finishedListener = {
            if (wasClicked) {
                onClick()
            }
            wasClicked = false
        }
    )
    FloatingActionButton(modifier = Modifier.padding(8.dp), onClick = { wasClicked = true }) {
        Icon(
            imageVector = icon,
            contentDescription = "delete",
            modifier = Modifier.graphicsLayer(rotationZ = rotation.value)
        )
    }
}