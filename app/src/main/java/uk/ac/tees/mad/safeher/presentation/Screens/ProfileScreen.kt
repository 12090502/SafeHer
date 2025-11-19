package uk.ac.tees.mad.safeher.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.tees.mad.safeher.presentation.UtilsScreens.BottomNavigation
import uk.ac.tees.mad.safeher.presentation.ViewModel.AuthViewModel
import uk.ac.tees.mad.safeher.presentation.ViewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier,
                  homeViewModel: HomeViewModel,
                  authViewModel: AuthViewModel,
                  navController: NavHostController) {

    val PrimaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.background
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFC1A4FA)
                )
            )
        },
        bottomBar = {
            BottomNavigation(
                navController = navController,
                modifier = modifier
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = PrimaryBrush)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // Content can go here later
        }
    }

}