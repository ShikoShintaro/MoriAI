package com.olokogini.moriai.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.olokogini.moriai.data.AppPreferences

import com.olokogini.moriai.ui.intro.IntroScreen
import com.olokogini.moriai.ui.login.LoginScreen
import com.olokogini.moriai.ui.register.RegisterScreen
import com.olokogini.moriai.ui.forgotpassword.ForgotPasswordScreen

import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val prefs = AppPreferences(context)
    val scope = rememberCoroutineScope()

    val firstLaunch by prefs.isFirstLaunch.collectAsState(initial = true)

    NavHost(
        navController = navController,
        startDestination = if (firstLaunch) "intro" else "login"
    ) {

        composable("intro") {
            IntroScreen(
                onFinish = {
                    scope.launch {
                        prefs.setFirstLaunchComplete()
                        navController.navigate("login") {
                            popUpTo("intro") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("login") {
            LoginScreen(
                onLogin = { navController.navigate("chat") },
                onRegister = { navController.navigate("register") },
                onForgot = { } //disabled
            )
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }

    }
}