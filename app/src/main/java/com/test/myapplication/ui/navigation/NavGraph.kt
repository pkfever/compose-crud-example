package com.test.myapplication.ui.navigation

import androidx.compose.runtime.Composable
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

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {

            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                navController,
                loginViewModel
            )
        }

        composable("signup") {

            val signupViewModel = hiltViewModel<SignupViewModel>()
            SignupScreen(navController, viewModel = signupViewModel)
        }

        composable("home") {

            val profileViewModel = hiltViewModel<ProfileViewModel>()
            val homeViewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(homeViewModel = homeViewModel, profileViewModel = profileViewModel)

        }
    }
}