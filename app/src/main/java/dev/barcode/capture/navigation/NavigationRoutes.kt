package dev.barcode.capture.navigation

sealed class NavigationRoutes(val route: String) {
    data object Home : NavigationRoutes("Home")
    data object WebView : NavigationRoutes("WebView")
}