package com.olokogini.moriai.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.olokogini.moriai.data.AppPreferences

import com.olokogini.moriai.ui.intro.IntroScreen
import com.olokogini.moriai.ui.login.LoginScreen
import com.olokogini.moriai.ui.register.RegisterScreen
import com.olokogini.moriai.ui.forgotpassword.ForgotPasswordScreen
import com.olokogini.moriai.ui.forgotpassword.ResetPasswordScreen
import com.olokogini.moriai.ui.otp.OtpScreen
import com.olokogini.moriai.ui.otp.ResetOtpScreen
import com.olokogini.moriai.ui.student.StudentInfoScreen

import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val prefs = AppPreferences(context)
    val scope = rememberCoroutineScope()

    val firstLaunch by prefs.isFirstLaunch.collectAsState(initial = null)

    if (firstLaunch == null ) {
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (firstLaunch == true ) "intro" else "login"
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
                onForgot = {  navController.navigate("forgot_password") } //disabled
            )
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }

        composable("otp/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            OtpScreen(navController, email)
        }

        composable("student_info") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            StudentInfoScreen(navController, email)
        }

        composable("reset_otp/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ResetOtpScreen(navController, email)
        }

        composable("reset_password/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ResetPasswordScreen(navController, email)
        }

    }
}