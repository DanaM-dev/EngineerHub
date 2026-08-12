package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.saveable.rememberSaveable
import com.danamansour.engineerhub.ui.theme.ThemeSetting
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EngineerHubApp(
    currentTheme: ThemeSetting,
    onThemeChange: (ThemeSetting) -> Unit,
    eventViewModel: EventViewModel = viewModel()
) {
    var recentToolIds by rememberSaveable { mutableStateOf(listOf("formulas", "pomodoro")) }

    val recentShortcutItems = remember(recentToolIds) {
        recentToolIds.mapNotNull { id ->
            when (id) {
                "formulas", Screen.ConstantsSheet -> ShortcutItem("formulas", "Constants", Icons.Outlined.MenuBook)
                "pomodoro", Screen.Pomodoro -> ShortcutItem("pomodoro", "Timer", Icons.Outlined.Timer)
                "converter", Screen.UnitConverter -> ShortcutItem("converter", "Converter", Icons.Outlined.SwapHoriz)
                "electrical", Screen.Electrical -> ShortcutItem("electrical", "Electrical", Icons.Default.Bolt)
                "mechanical", Screen.Mechanical -> ShortcutItem("mechanical", "Mechanical", Icons.Default.Settings)
                "telecom", Screen.Telecom -> ShortcutItem("telecom", "Telecom", Icons.Outlined.Router)
                "civil", Screen.Civil -> ShortcutItem("civil", "Civil", Icons.Outlined.Engineering)
                "chemical", Screen.Chemical -> ShortcutItem("chemical", "Chemical", Icons.Outlined.Science)
                "thermo", Screen.Thermo -> ShortcutItem("thermo", "Thermo", Icons.Outlined.Thermostat)
                "industrial", Screen.Industrial -> ShortcutItem("industrial", "Industrial", Icons.Outlined.Factory)
                "aerospace", Screen.Aerospace -> ShortcutItem("aerospace", "Aerospace", Icons.Outlined.RocketLaunch)
                else -> null
            }
        }
    }

    fun trackToolVisit(toolId: String) {
        if (toolId == Screen.Dashboard || toolId == "home") return
        recentToolIds = (listOf(toolId) + recentToolIds.filterNot { it == toolId }).take(5)
    }

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("home") }

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
                    label = { Text("Dashboard", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Dashboard"
                        )
                    },
                    selected = currentScreen == "home",
                    onClick = {
                        currentScreen = "home"
                        navController.navigateToTop(Screen.Dashboard)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text("Electrical Hub", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = "Electrical Hub"
                        )
                    },
                    selected = currentScreen == "electrical",
                    onClick = {
                        currentScreen = "electrical"
                        trackToolVisit(Screen.Electrical)
                        navController.navigateToTop(Screen.Electrical)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text("Mechanical Hub", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Mechanical Hub"
                        )
                    },
                    selected = currentScreen == "mechanical",
                    onClick = {
                        currentScreen = "mechanical"
                        trackToolVisit(Screen.Mechanical)
                        navController.navigateToTop(Screen.Mechanical)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Chemical Hub", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Science,
                            contentDescription = "Chemical Hub"
                        )
                    },
                    selected = currentScreen == "chemical",
                    onClick = {
                        currentScreen = "chemical"
                        trackToolVisit(Screen.Chemical)
                        navController.navigateToTop(Screen.Chemical)
                        scope.launch { drawerState.close() }
                    },

                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Thermo Hub", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Thermostat,
                            contentDescription = "Thermodynamics Hub"
                        )
                    },
                    selected = currentScreen == "thermo",
                    onClick = {
                        currentScreen = "thermo"
                        trackToolVisit(Screen.Thermo)
                        navController.navigateToTop(Screen.Thermo)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Telecom Hub", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Router,
                            contentDescription = "Telecommunication Hub"
                        )
                    },
                    selected = currentScreen == "telecom",
                    onClick = {
                        currentScreen = "telecom"
                        trackToolVisit(Screen.Telecom)
                        navController.navigateToTop(Screen.Telecom)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Civil Hub", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Engineering,
                            contentDescription = "Civil Hub"
                        )
                    },
                    selected = currentScreen == "civil",
                    onClick = {
                        currentScreen = "civil"
                        trackToolVisit(Screen.Civil)
                        navController.navigateToTop(Screen.Civil)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Industrial Hub", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Factory,
                            contentDescription = "Industrial Hub"
                        )
                    },
                    selected = currentScreen == "industrial",
                    onClick = {
                        currentScreen = "industrial"
                        trackToolVisit(Screen.Industrial)
                        navController.navigateToTop(Screen.Industrial)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Aerospace Hub", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.RocketLaunch,
                            contentDescription = "Aerospace Hub"
                        )
                    },
                    selected = currentScreen == "aerospace",
                    onClick = {
                        currentScreen = "aerospace"
                        trackToolVisit(Screen.Aerospace)
                        navController.navigateToTop(Screen.Aerospace)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Unit Converter", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.SwapHoriz,
                            contentDescription = "Unit Converter"
                        )
                    },
                    selected = currentScreen == "unit_converter",
                    onClick = {
                        currentScreen = "unit_converter"
                        trackToolVisit(Screen.UnitConverter)
                        navController.navigateToTop(Screen.UnitConverter)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Constants Sheet", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.MenuBook,
                            contentDescription = "Constants Sheet"
                        )
                    },
                    selected = currentScreen == "constants_sheet",
                    onClick = {
                        currentScreen = "constants_sheet"
                        trackToolVisit(Screen.ConstantsSheet)
                        navController.navigateToTop(Screen.ConstantsSheet)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Pomodoro Timer", fontFamily = FontFamily.SansSerif) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Timer,
                            contentDescription = "Pomodoro Timer"
                        )
                    },
                    selected = currentScreen == "pomodoro",
                    onClick = {
                        currentScreen = "pomodoro"
                        trackToolVisit(Screen.Pomodoro)
                        navController.navigateToTop(Screen.Pomodoro)
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = Color.White,

                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.primary
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
                    HomeScreen(
                        viewModel = eventViewModel,
                        recentShortcuts = recentShortcutItems,
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onNavigateToTool = { toolId ->
                            trackToolVisit(toolId)
                            val targetRoute = when (toolId) {
                                "converter", "unit_converter" -> Screen.UnitConverter
                                "formulas", "constants" -> Screen.ConstantsSheet
                                "pomodoro" -> Screen.Pomodoro
                                "telecom" -> Screen.Telecom
                                "electrical" -> Screen.Electrical
                                "mechanical" -> Screen.Mechanical
                                "thermo" -> Screen.Thermo
                                "chemical" -> Screen.Chemical
                                "civil" -> Screen.Civil
                                "industrial" -> Screen.Industrial
                                "aerospace" -> Screen.Aerospace
                                else -> null
                            }
                            targetRoute?.let { route ->
                                currentScreen = route
                                trackToolVisit(route)
                                navController.navigateToTop(route)
                            }
                        }
                    )
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

fun NavHostController.navigateToTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
