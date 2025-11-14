package uk.ac.tees.mad.safeher.presentation.Screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.tees.mad.safeher.presentation.UtilsScreens.BottomNavigation
import uk.ac.tees.mad.safeher.presentation.ViewModel.AuthViewModel
import uk.ac.tees.mad.safeher.presentation.ViewModel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController,
) {
//    abhishek.adgamadigital@gmail.com
//    Adgama123@
    val locationState by homeViewModel.locationState.collectAsState()
    val cityName by homeViewModel.cityName.collectAsState()
    val fullAddress by homeViewModel.fullAddress.collectAsState()
    var address by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as ComponentActivity


    val permissions = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions.add(Manifest.permission.POST_NOTIFICATIONS)
    }
    val notGranted = permissions.filter {
        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
    }
    if (notGranted.isNotEmpty()) {
        ActivityCompat.requestPermissions(
            activity, notGranted.toTypedArray(), 101
        )
    }

    val PrimaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )

    Scaffold(
        modifier
            .fillMaxSize(), bottomBar = {
            BottomNavigation(
                navController = navController, modifier = modifier
            )
        }) { paddingValues ->
        Box(
            modifier
                .fillMaxSize()
                .background(brush = PrimaryBrush),
            contentAlignment = Alignment.Center
        ) {
            Column {

            }
            Column(modifier.align(Alignment.BottomCenter)) {

                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0076)),
                    shape = CircleShape,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 60.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
                ) {
                    Text(
                        text = "SOS",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                }
                Spacer(modifier.height(50.dp))
            }


        }
    }


}
