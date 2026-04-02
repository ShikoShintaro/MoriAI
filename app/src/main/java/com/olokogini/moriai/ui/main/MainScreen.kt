package com.olokogini.moriai.ui.main

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen( navController: NavController) {

    val innerNavController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry = innerNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

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
                }

                HorizontalDivider()

                Spacer(modifier = Modifier.height(10.dp))

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
                    label = { Text("Profile") },
                    selected = currentRoute == "profile",
                    onClick = {
                        scope.launch { drawerState.close() }
                        innerNavController.navigate("profile")
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = currentRoute == "settings",
                    onClick = {
                        scope.launch { drawerState.close() }
                        innerNavController.navigate("settings")
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        when (currentRoute) {
                            "chat" -> Text("Mori AI")
                            else -> {}
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon( Icons.Default.Menu, contentDescription = null )
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