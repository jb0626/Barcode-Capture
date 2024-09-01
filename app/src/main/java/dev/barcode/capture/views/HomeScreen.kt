package dev.barcode.capture.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.barcode.capture.navigation.NavigationRoutes

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                navController.navigate(NavigationRoutes.WebView.route)
            }
        ) {
            Text(text = "Open web page")
        }
    }
}