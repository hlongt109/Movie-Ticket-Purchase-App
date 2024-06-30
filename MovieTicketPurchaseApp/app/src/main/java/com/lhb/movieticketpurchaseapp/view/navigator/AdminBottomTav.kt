package com.lhb.movieticketpurchaseapp.view.navigator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.StatisticsScreen
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminBottomTav(navController: NavController) {
    val navigationController = rememberNavController()
    val selected = remember {
        mutableStateOf(Icons.Outlined.Category)
    }
    val movieViewModel: MovieViewModel = viewModel()
    val movieGenreViewModel: MovieGenreViewModel = viewModel()
    //
    val bottomSheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetMenu(navController)
        },
        scaffoldState = bottomSheetState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetContainerColor = Color("#08070b".toColorInt()),
    ) { paddingValues ->
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    containerColor = Color("#000000".toColorInt()).copy(alpha = (0.79f)),
                    modifier = Modifier.height(100.dp)
                ) {
                    IconButton(
                        onClick = {
                            selected.value = Icons.Outlined.Category
                            navigationController.navigate(BottomBarScreens.AdminDashboard.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Outlined.Category, contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Outlined.Category) Color(0xff6C47DB) else Color(
                                0xffffffff
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        FloatingActionButton(
                            onClick = {
                                scope.launch {
                                    bottomSheetState.bottomSheetState.expand()
                                }
                            },
                            modifier = Modifier.width(40.dp),
                            containerColor = Color(0xffffffff),
                            shape = RoundedCornerShape(7.dp)
                        ) {
                            Icon(
                                Icons.Default.Add, contentDescription = "",
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            selected.value = Icons.Outlined.BarChart
                            navigationController.navigate(BottomBarScreens.AdminStatistics.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Outlined.BarChart, contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Outlined.BarChart) Color(0xff6C47DB) else Color(
                                0xffffffff
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navigationController,
                startDestination = BottomBarScreens.AdminDashboard.screen,
                modifier = Modifier.padding(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                )
            ) {
                composable(BottomBarScreens.AdminDashboard.screen) {
                    ManagementScreen(
                        navController,
                        movieViewModel,
                        movieGenreViewModel
                    )
                }
                composable(BottomBarScreens.AdminStatistics.screen) { StatisticsScreen(navController) }
            }
        }
    }
}

