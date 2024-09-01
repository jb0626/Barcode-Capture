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
import dev.barcode.capture.viewmodels.HomeViewModel
import dev.barcode.capture.viewmodels.WebViewModel
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
                        HomeScreen(navController, HomeViewModel())
                    }
                    composable(
                        route = NavigationRoutes.WebView.route,
                        arguments = listOf(
                            navArgument("url") {
                                defaultValue = ""
                                type = NavType.StringType
                            }
                        )
                    ) {
                        WebViewScreen(
                            navController = navController,
                            viewModel = WebViewModel(it.arguments?.getString("url") ?: "")
                        )
                    }
                    composable(
                        route = NavigationRoutes.WebViewNoParams.route,
                    ) {
                        WebViewScreen(
                            navController = navController,
                            viewModel = WebViewModel("")
                        )
                    }
                }
            }
        }
    }
}