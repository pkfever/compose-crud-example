package com.test.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.myapplication.ui.edit.ProfileViewModel
import com.test.myapplication.ui.home.HomeScreen
import com.test.myapplication.ui.home.HomeViewModel
import com.test.myapplication.ui.login.LoginScreen
import com.test.myapplication.ui.login.LoginViewModel
import com.test.myapplication.ui.signup.SignupScreen
import com.test.myapplication.ui.signup.SignupViewModel
import com.test.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val context: Context = LocalContext.current
                    val navController = rememberNavController()
                    NavHostNavigation(navController, context, startDestination = "login")
                }
            }
        }
    }
}

@Composable
fun NavHostNavigation(
    navController: NavHostController = rememberNavController(),
    context: Context,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {

            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(loginViewModel, {
                navController.navigate("signup")
            }, {
                navController.navigate("home")
            })
        }
        composable("signup") {

            val signupViewModel = hiltViewModel<SignupViewModel>()
            SignupScreen(viewModel = signupViewModel) {
                navController.popBackStack()
            }
        }
        composable("home") {

            val profileViewModel = hiltViewModel<ProfileViewModel>()
            val homeViewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(homeViewModel = homeViewModel, profileViewModel = profileViewModel)

        }
    }
}