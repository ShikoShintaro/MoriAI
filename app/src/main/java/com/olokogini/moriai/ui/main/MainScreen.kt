package com.olokogini.moriai.ui.main

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.navigation.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.olokogini.moriai.api.ProfileResponse
import com.olokogini.moriai.ui.main.profile.ProfileGetHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {

    var profileName by remember { mutableStateOf("Loading...") }
    var profileEmail by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf("") }

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val userEmail = prefs.getString("email", "") ?: ""

    val innerNavController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry = innerNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    // Helper to handle nested routes like profile/settings
    fun isProfileRoute(route: String?): Boolean {
        return route?.startsWith("profile") == true
    }

    LaunchedEffect(Unit) {
        if (userEmail.isNotEmpty()) {
            ProfileGetHelper.getProfile(
                userEmail,
                object : ProfileGetHelper.CallbackListener {
                    override fun onSuccess(profile: ProfileResponse?) {
                        if (profile != null) {
                            profileName = profile.fullName ?: "Unknown"
                            profileEmail = userEmail
                            profileImageUrl = profile.imageUrl ?: ""
                        }
                    }

                    override fun onError(error: String) {
                        println("Profile load error: $error")
                    }
                }
            )
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "MORI AI",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Your AI Companion",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔹 Profile Preview
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        // SETTINGS ICON (LEFT SIDE)
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    innerNavController.navigate("settings")
                                }
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        // PROFILE SECTION (IMAGE + TEXT)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    innerNavController.navigate("profile")
                                }
                        ) {

                            Surface(
                                shape = MaterialTheme.shapes.extraLarge,
                                modifier = Modifier.size(50.dp)
                            ) {
                                AsyncImage(
                                    model = profileImageUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(profileName, style = MaterialTheme.typography.bodyLarge)
                                Text(profileEmail, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(10.dp))

                    // Navigation items
                    NavigationDrawerItem(
                        label = { Text("Chat") },
                        selected = currentRoute == "chat",
                        onClick = {
                            scope.launch { drawerState.close() }
                            innerNavController.navigate("chat")
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    NavigationDrawerItem(
                        label = { Text("Events & Schedule") },
                        selected = currentRoute == "events",
                        onClick = {
                            scope.launch { drawerState.close() }
                            innerNavController.navigate("events")
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        when {
                            currentRoute == "chat" -> Text("MORI AI")
                            isProfileRoute(currentRoute) -> Text("Profile")
                            else -> Text("")
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                MainContent(innerNavController)
            }
        }
    }
}