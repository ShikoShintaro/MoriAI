package com.olokogini.moriai.ui.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.room.util.splitToIntList
import coil.compose.AsyncImage
import com.olokogini.moriai.api.ProfileResponse
import com.olokogini.moriai.ui.main.profile.ProfileGetHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {

    var profileName by remember { mutableStateOf("Loading...") }
    var profileEmail by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf("") }

    val context = LocalContext.current
    val prefs = context.getSharedPreferences(
        "user_prefs",
        Context.MODE_PRIVATE
    )

    val userEmail = prefs.getString("email", "") ?: ""

    val innerNavController = rememberNavController()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

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

                    override fun onError(error: String) {

                    }
                }
            )
        }
    }

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(

                modifier = Modifier.width(300.dp),

                drawerContainerColor =
                    MaterialTheme.colorScheme.surface

            ) {

                Column(

                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                        .padding(20.dp)

                ) {

                    Text(
                        text = "MORI AI",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Your AI Companion",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.7f
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(

                        shape = RoundedCornerShape(22.dp),

                        colors = CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceVariant
                        )

                    ) {

                        Row(

                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    scope.launch {
                                        drawerState.close()
                                    }

                                    innerNavController.navigate("profile")
                                }
                                .padding(16.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            AsyncImage(
                                model = profileImageUrl,
                                contentDescription = null,

                                modifier = Modifier
                                    .size(58.dp)
                                    .clip(CircleShape)
                            )

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = profileName,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = profileEmail,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.7f
                                    )
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,

                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {

                                        scope.launch {
                                            drawerState.close()
                                        }

                                        innerNavController.navigate("settings")
                                    }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    NavigationDrawerItem(

                        icon = {

                            Icon(
                                Icons.Default.Chat,
                                contentDescription = null
                            )
                        },

                        label = {

                            Column {

                                Text("Chat")

                                Text(

                                    text = "Talk with MORI",

                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },

                        selected = currentRoute == "chat",

                        onClick = {

                            scope.launch {
                                drawerState.close()
                            }

                            innerNavController.navigate("chat")
                        },

                        shape = RoundedCornerShape(18.dp),

                        colors = NavigationDrawerItemDefaults.colors(

                            selectedContainerColor =
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),

                            selectedTextColor =
                                MaterialTheme.colorScheme.primary,

                            selectedIconColor =
                                MaterialTheme.colorScheme.primary
                        ),

                        modifier = Modifier.padding(vertical = 5.dp)
                    )

                    NavigationDrawerItem(

                        icon = {

                            Icon(
                                Icons.Default.Event,
                                contentDescription = null
                            )
                        },

                        label = {

                            Column {

                                Text("Events")

                                Text(

                                    text = "Schedules & campus activities",

                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },

                        selected = currentRoute == "events",

                        onClick = {

                            scope.launch {
                                drawerState.close()
                            }

                            innerNavController.navigate("events")
                        },

                        shape = RoundedCornerShape(18.dp),

                        colors = NavigationDrawerItemDefaults.colors(

                            selectedContainerColor =
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),

                            selectedTextColor =
                                MaterialTheme.colorScheme.primary,

                            selectedIconColor =
                                MaterialTheme.colorScheme.primary
                        ),

                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
    ) {

        Scaffold(

            topBar = {

                TopAppBar(

                    colors = TopAppBarDefaults.topAppBarColors(

                        containerColor = MaterialTheme.colorScheme.surface.copy(
                            alpha = 0.92f
                        ),

                        scrolledContainerColor = MaterialTheme.colorScheme.surface,

                        titleContentColor = MaterialTheme.colorScheme.onSurface,

                        navigationIconContentColor = MaterialTheme.colorScheme.primary
                    ),

                    title = {

                        Column {

                            Text(

                                text = when (currentRoute) {

                                    "chat" -> "MORI AI"
                                    "profile" -> "Profile"
                                    "settings" -> "Settings"
                                    "events" -> "Events"

                                    else -> ""

                                },

                                style = MaterialTheme.typography.titleLarge
                            )

                            if (currentRoute != "settings") {

                                Text(

                                    text = when (currentRoute) {

                                        "chat" -> "AI School Assistant"
                                        "events" -> "Schedules & Activities"
                                        "profile" -> "Student Information"

                                        else -> ""

                                    },

                                    style = MaterialTheme.typography.bodySmall,

                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.65f
                                    )
                                )
                            }
                        }
                    },

                    navigationIcon = {

                        IconButton(

                            onClick = {

                                scope.launch {
                                    drawerState.open()
                                }
                            }

                        ) {

                            Surface(

                                shape = CircleShape,

                                color = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.12f
                                )

                            ) {

                                Box(
                                    modifier = Modifier.padding(8.dp)
                                ) {

                                    Icon(
                                        Icons.Default.Menu,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                )
            }

        ) { padding ->

            Box(
                modifier = Modifier.padding(padding)
            ) {

                MainContent(innerNavController)

            }
        }
    }
}