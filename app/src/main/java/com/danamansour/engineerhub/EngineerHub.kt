package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Engineering
import androidx.compose.material.icons.outlined.Factory
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material.icons.outlined.Router
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.Timer

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
            ModalDrawerSheet(modifier = Modifier.width(IntrinsicSize.Min)) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Menu",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )

                NavigationDrawerItem(
                    label = { Text("Dashboard",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Dashboard"
                        )
                    },
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
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text("Electrical Hub",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = "Electrical Hub"
                        )
                    },
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
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text("Mechanical Hub",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Mechanical Hub"
                        )
                    },
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
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )


                NavigationDrawerItem(
                    label = { Text("Chemical Hub",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Science,
                            contentDescription = "Chemical Hub"
                        )
                    },
                    selected = currentScreen == "chemical",
                    onClick = {
                        currentScreen = "chemical"
                        navController.navigate(Screen.Chemical)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )
                NavigationDrawerItem(
                        label = { Text("Thermodynamics Hub",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Thermostat,
                            contentDescription = "Thermodynamics Hub"
                        )
                    },
                    selected = currentScreen == "thermo",
                    onClick = {
                        currentScreen = "thermo"
                        navController.navigate(Screen.Thermo)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )


                NavigationDrawerItem(
                    label = { Text("Telecommunication Hub",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Router,
                            contentDescription = "Telecommunication Hub"
                        )
                    },
                    selected = currentScreen == "telecom",
                    onClick = {
                        currentScreen = "telecom"
                        navController.navigate(Screen.Telecom)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )


                NavigationDrawerItem(
                    label = { Text("Civil Hub",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Engineering,
                            contentDescription = "Civil Hub"
                        )
                    },
                    selected = currentScreen == "civil",
                    onClick = {
                        currentScreen = "civil"
                        navController.navigate(Screen.Civil)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )


                NavigationDrawerItem(
                    label = { Text("Industrial Hub",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Factory,
                            contentDescription = "Industrial Hub"
                        )
                    },
                    selected = currentScreen == "industrial",
                    onClick = {
                        currentScreen = "industrial"
                        navController.navigate(Screen.Industrial)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )


                NavigationDrawerItem(
                    label = { Text("Aerospace Hub",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.RocketLaunch,
                            contentDescription = "Aerospace Hub"
                        )
                    },
                    selected = currentScreen == "aerospace",
                    onClick = {
                        currentScreen = "aerospace"
                        navController.navigate(Screen.Aerospace)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )


                NavigationDrawerItem(
                    label = { Text("Unit Converter",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.SwapHoriz,
                            contentDescription = "Unit Converter"
                        )
                    },
                    selected = currentScreen == "unit_converter",
                    onClick = {
                        currentScreen = "unit_converter"
                        navController.navigate(Screen.UnitConverter)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )


                NavigationDrawerItem(
                    label = { Text("Constants Sheet",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.MenuBook,
                            contentDescription = "Constants Sheet"
                        )
                    },
                    selected = currentScreen == "constants_sheet",
                    onClick = {
                        currentScreen = "constants_sheet"
                        navController.navigate(Screen.ConstantsSheet)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Pomodoro Timer",fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Timer,
                            contentDescription = "Pomodoro Timer"
                        )
                    },
                    selected = currentScreen == "pomodoro",
                    onClick = {
                        currentScreen = "pomodoro"
                        navController.navigate(Screen.Pomodoro)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = LightBlueMain,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = DarkBlueText,
                        unselectedTextColor = Color.Black,
                        selectedIconColor = Color.White,
                        unselectedIconColor = DarkBlueText
                    )
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
            )
            NavHost(
                navController = navController,
                startDestination = Screen.Dashboard,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.Dashboard) {
                    HomeScreen(onMenuClick = { scope.launch { drawerState.open() } })
                }
                composable(Screen.Electrical) {
                    ElectricalScreen()
                }
                composable(Screen.Mechanical) {
                    MechanicalScreen()
                }
                composable(Screen.Thermo) {
                    ThermodynamicsScreen()
                }
                composable(Screen.Telecom) {
                    TelecomScreen()
                }
                composable(Screen.Chemical) {
                    ChemicalScreen()
                }
                composable(Screen.Civil) {
                    CivilScreen()
                }
                composable(Screen.Industrial) {
                    IndustrialScreen()
                }
                composable(Screen.Aerospace) {
                    AerospaceScreen()
                }
                composable(Screen.UnitConverter) {
                    UnitConverterScreen()
                }
                composable(Screen.ConstantsSheet) {
                    ConstantsSheetScreen()
                }
                composable(Screen.Pomodoro) {
                    PomodoroScreen()
                }
            }
        }
    }
}