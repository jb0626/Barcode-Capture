package dev.barcode.capture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.barcode.capture.navigation.NavigationRoutes
import dev.barcode.capture.ui.theme.BarcodeCaptureTheme
import dev.barcode.capture.views.HomeScreen
import dev.barcode.capture.views.WebViewScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BarcodeCaptureTheme {
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.Home.route
                ) {
                    composable(NavigationRoutes.Home.route) {
                        HomeScreen(navController)
                    }
                    composable(
                        route = NavigationRoutes.WebView.route,
                        arguments = listOf(
                            navArgument("url") {
                                defaultValue = "http://172.30.1.44:8080/BarcodeDetector.html"
                                type = NavType.StringType
                            }
                        )
                    ) {
                        WebViewScreen(
                            navController = navController,
                            url = it.arguments?.getString("url") ?: ""
                        )
                    }
                }
            }
        }
    }
}