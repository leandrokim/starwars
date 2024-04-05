package com.leandrokim.starwars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.leandrokim.starwars.presentation.di.ViewModelFactory
import com.leandrokim.starwars.ui.screen.DetailsScreen
import com.leandrokim.starwars.ui.screen.HomeScreen
import com.leandrokim.starwars.ui.screen.SplashScreen
import com.leandrokim.starwars.ui.theme.StarWarsTheme
import com.leandrokim.starwars.ui.viewModel.DetailsViewModel
import com.leandrokim.starwars.ui.viewModel.HomeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            StarWarsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen {
                                navController.navigate("home") {
                                    popUpTo("splash") {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                        composable("home") {
                            val viewModel =
                                viewModel<HomeViewModel> { ViewModelFactory.createHomeViewModel() }
                            HomeScreen(viewModel) { name ->
                                navController.navigate("personDetails/$name")
                            }
                        }
                        composable(
                            "personDetails/{personName}",
                            arguments = listOf(navArgument("personName") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val viewModel = viewModel<DetailsViewModel> { ViewModelFactory.createDetailsViewModel() }
                            val personName = backStackEntry.arguments!!.getString("personName")!!
                            viewModel.getPerson(personName)
                            DetailsScreen(
                                viewModel = viewModel,
                                personName = personName
                            ) { navController.popBackStack() }
                        }
                    }
                }
            }
        }
    }
}
