package com.olokogini.moriai.ui.main

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import coil.compose.AsyncImage
import com.olokogini.moriai.api.ProfileResponse
import com.olokogini.moriai.ui.main.profile.ProfileGetHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {
    var profileName by remember { mutableStateOf("Loading. . .") }
    var profileEmail by remember {mutableStateOf("")}
    var profileImageUrl by remember { mutableStateOf("") }

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val userEmail = prefs.getString("email", "") ?: ""

    val innerNavController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun isProfileRoute(route: String?) =
        route?.startsWith("profile") == true

    LaunchedEffect(Unit) {
        if (userEmail.isNotEmpty()) {
            ProfileGetHelper.getProfile(
                userEmail,
                object : ProfileGetHelper.CallbackListener {
                    override fun onSuccess(profile: ProfileResponse?) {
                        profileName = profile?.fullName ?: "Unknown"
                        profileEmail = userEmail
                        profileImageUrl = profile?.imageUrl ?: ""
                    }
                    override fun onError(error: String) {}
                }
            )
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        "MORI AI",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        "Your AI Companion",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.clickable{
                                scope.launch { drawerState.close() }
                                innerNavController.navigate("settings")
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    innerNavController.navigate("profile")
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = profileImageUrl,
                                contentDescription = null,
                                modifier = Modifier.size(44.dp)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(profileName)
                                Text (profileEmail,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    NavigationDrawerItem(
                        label = { Text("Chat") },
                        selected = currentRoute == "chat",
                        onClick = {
                            scope.launch { drawerState.close() }
                            innerNavController.navigate("chat")
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text("Events") },
                        selected = currentRoute == "events",
                        onClick = {
                            scope.launch { drawerState.close() }
                            innerNavController.navigate("events")
                        }
                    )

                }

            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text (
                            when (currentRoute) {
                                "chat" -> "MORI AI"
                                "profile" -> "Profile"
                                "settings" -> "Settings"
                                "events" -> "Events"
                                else -> ""
                            }
                        )
                    },

                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.close() }
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