package com.olokogini.moriai.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.olokogini.moriai.data.AppPreferences
import com.olokogini.moriai.ui.intro.IntroScreen
import com.olokogini.moriai.ui.login.LoginScreen
import com.olokogini.moriai.ui.register.RegisterScreen
import com.olokogini.moriai.ui.forgotpassword.ForgotPasswordScreen
import com.olokogini.moriai.ui.forgotpassword.ResetPasswordScreen
import com.olokogini.moriai.ui.main.MainScreen
import com.olokogini.moriai.ui.otp.OtpScreen
import com.olokogini.moriai.ui.otp.ResetOtpScreen
import com.olokogini.moriai.ui.student.StudentInfoScreen
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import com.olokogini.moriai.ui.login.LoginUiState
import com.olokogini.moriai.ui.bootstrap.BootstrapScreen
import com.olokogini.moriai.ui.main.home.HomeScreen

@Composable
fun AppNavigation(startDestination: String) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val appPrefs = AppPreferences(context)

    val userPrefs = context.getSharedPreferences(
        "user_prefs",
        android.content.Context.MODE_PRIVATE
    )

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("intro") {
            IntroScreen(
                onFinish = {
                    scope.launch {
                        appPrefs.setFirstLaunchComplete()
                        navController.navigate("login") {
                            popUpTo("intro") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("login") {

            val loginState = remember { mutableStateOf(LoginUiState()) }

            LoginScreen(
                state = loginState.value,
                onEmailChange = {
                    loginState.value = loginState.value.copy(email = it)
                },
                onPasswordChange = {
                    loginState.value = loginState.value.copy(password = it)
                },
                onLoginClick = {
                    userPrefs.edit()
                        .putBoolean("is_logged_in", true)
                        .apply()

                    navController.navigate("chat") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRegister = { navController.navigate("register") },
                onForgot = { navController.navigate("forgot_password") }
            )
        }

        composable("bootstrap") {
            BootstrapScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("chat") {
            MainScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }

        composable("otp/{email}") {
            val email = it.arguments?.getString("email") ?: ""
            OtpScreen(navController, email)
        }

        composable("student_info/{email}") {
            val email = it.arguments?.getString("email") ?: ""
            StudentInfoScreen(navController, email)
        }

        composable("reset_otp/{email}") {
            val email = it.arguments?.getString("email") ?: ""
            ResetOtpScreen(navController, email)
        }

        composable("reset_password/{email}") {
            val email = it.arguments?.getString("email") ?: ""
            ResetPasswordScreen(navController, email)
        }
    }
}