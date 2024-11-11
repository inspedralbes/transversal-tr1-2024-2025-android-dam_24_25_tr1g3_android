package com.mbad


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mbad.sockets.SocketManager
import com.mbad.ui.StoreViewModel
import com.mbad.ui.screens.CartScreen
import com.mbad.ui.screens.PaymentScreen
import com.mbad.ui.screens.ShopScreen
import com.mbad.ui.screens.StartScreen

enum class AppScreen(){
    Start,
    Shop,
    Order,
    Payment,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mbadAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = currentScreen.name,
                color = MaterialTheme.colorScheme.onPrimaryContainer, // Color del texto
                style = MaterialTheme.typography.titleLarge  // Tamaño y estilo del texto
            )
        },
        modifier = modifier.height(60.dp), // Ajusta la altura de la barra
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.secondary, // Color del título
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary // Color del icono de navegación
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }

    )
}

@Composable
fun mbadApp(
    viewModel: StoreViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    SocketManager: SocketManager = SocketManager()

){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Start.name
    )
    Scaffold(
        topBar = {
            mbadAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ){innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = AppScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.Start.name){
                StartScreen(
                    modifier = Modifier,
                    onNextButtonClicked = { navController.navigate(AppScreen.Shop.name) },

                )
            }
            composable(route = AppScreen.Shop.name) {
                ShopScreen(
                    modifier = Modifier,
                    storeViewModel = viewModel,
                    onCartButtonClicked = { navController.navigate(AppScreen.Order.name) },
                    onIncreaseProduct = { productId ->
                        println("ADD producto ${productId}")
                        viewModel.addProductToCart(productId)
                    },
                    onDecreaseProduct = { productId ->
                        println("REMOVE producto ${productId}")
                        viewModel.removeProductFromCart(productId)
                    }
                )
            }
            composable(route = AppScreen.Order.name) {
                CartScreen(
                    modifier = Modifier,
                    storeViewModel = viewModel,
                    onIncreaseProduct = { productId ->
                        println("ADD producto ${productId}")
                        viewModel.addProductToCart(productId)
                    },
                    onDecreaseProduct = { productId ->
                        println("REMOVE producto ${productId}")
                        viewModel.removeProductFromCart(productId)
                    },
                    onPayClick = {
                        viewModel.sendOrder(navController)
                    }
                )
            }
            composable(route = AppScreen.Payment.name) {
                PaymentScreen(
                    modifier = Modifier,
                    storeViewModel = viewModel,
                )
            }
        }


    }
}

