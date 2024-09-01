package dev.barcode.capture.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.barcode.capture.navigation.NavigationRoutes
import dev.barcode.capture.viewmodels.HomeViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = viewModel.inputUrl.collectAsState().value,
                onValueChange = {
                    viewModel.setInputUrl(it)
                }
            )
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    navController.navigate(
                        NavigationRoutes.WebView.route
                            .replace(
                                oldValue = "{url}",
                                newValue = URLEncoder.encode(
                                    viewModel.inputUrl.value,
                                    StandardCharsets.UTF_8.toString()
                                )
                            )
                    )
                }
            ) {
                Text(text = "Open web page", fontSize = 16.sp)
            }
        }
    }
}