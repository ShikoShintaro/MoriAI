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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.olokogini.moriai.ui.login.LoginUiState
import com.olokogini.moriai.ui.bootstrap.BootstrapScreen
import com.olokogini.moriai.ui.bootstrap.LoginCheckScreen
import com.olokogini.moriai.ui.login.LoginViewModel
import com.olokogini.moriai.ui.main.home.HomeScreen
import com.olokogini.moriai.ui.main.profile.ProfileScreen
import com.olokogini.moriai.ui.main.settings.SettingsScreen
import com.olokogini.moriai.ui.register.RegisterViewModel

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

            val viewModel = remember { LoginViewModel(context) }

            LoginScreen(
                state = viewModel.state,

                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,

                onLoginClick = {
                    viewModel.login {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },

                onRegister = { navController.navigate("register") },
                onForgot = { navController.navigate("forgot_password") }
            )
        }

        composable("bootstrap") {
            BootstrapScreen(navController)
        }

        composable("login_check") {
            LoginCheckScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("settings") {
            SettingsScreen()
        }

        composable("profile"){
            ProfileScreen()
        }

        composable("chat") {
            MainScreen(navController)
        }

        composable("register") {
            val viewModel = remember {
                RegisterViewModel()
            }

            RegisterScreen(navController, viewModel)

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