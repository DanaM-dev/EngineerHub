package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EngineerHubApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("home") }
    val LightBlueMain = Color(0xFFBBDEFB)
    val DarkBlueText = Color(0xFF0D47A1)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Menu",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )

                NavigationDrawerItem(
                    label = { Text("Dashboard",fontFamily = FontFamily.SansSerif) },
                    selected = currentScreen == "home",
                    onClick = {
                        currentScreen = "home"
                        navController.navigate(Screen.Dashboard)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text("Electrical Hub") },
                    selected = currentScreen == "electrical",
                    onClick = {
                        currentScreen = "electrical"
                        navController.navigate(Screen.Electrical)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text("Mechanical Hub") },
                    selected = currentScreen == "mechanical",
                    onClick = {
                        currentScreen = "mechanical"
                        navController.navigate(Screen.Mechanical)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black
                    )
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("EngineerHub") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Dashboard,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.Dashboard) {
                    HomeScreen()
                }
                composable(Screen.Electrical) {
                    ElectricalScreen()
                }
                composable(Screen.Mechanical) {
                    MechanicalScreen()
                }
            }
        }
    }
}