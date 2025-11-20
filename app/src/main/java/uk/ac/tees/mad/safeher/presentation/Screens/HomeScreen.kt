package uk.ac.tees.mad.safeher.presentation.Screens

import android.Manifest
import android.R.id.message
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.tees.mad.safeher.presentation.UtilsScreens.BottomNavigation
import uk.ac.tees.mad.safeher.presentation.ViewModel.AuthViewModel
import uk.ac.tees.mad.safeher.presentation.ViewModel.HomeViewModel
import java.io.File.separator

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController,
) {

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
    val contacts by homeViewModel.allContacts.collectAsState()

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
            modifier = Modifier
                .fillMaxSize()
                .background(brush = PrimaryBrush)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header text
                Text(
                    text = "Emergency Assistance",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Subtext / Description
                Text(
                    text = "In case of any danger or emergency, press the SOS button below to alert your trusted contacts immediately.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        MaterialTheme.colorScheme.background,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // SOS Button at bottom
            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {

                        if (contacts.size != 0) {

                            homeViewModel.fetchCurrentLocation(context)

                            val phoneNumbers: List<String> = contacts.map { it.contactNumber }

                            homeViewModel.smsIntent(
                                context = context,
                                lon = locationState.lon,
                                lat = locationState.lat,
                                phoneNumbers = phoneNumbers,
                                cityName = cityName
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please add a trusted contact first",
                                Toast.LENGTH_SHORT
                            ).show()
                        }



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

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Press and hold in case of emergency",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }


}


@Preview(showBackground = true, name = "SafeHer – Home / Emergency Screen")
@Composable
fun HomeScreenPreview() {
    val primaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )

    Scaffold(
        bottomBar = {
            // Fake bottom navigation just for preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .background(Color.White.copy(alpha = 0.95f))
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = primaryBrush)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Emergency Assistance",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "In case of any danger or emergency, press the SOS button below to alert your trusted contacts immediately.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // SOS Button
            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0076)),
                    shape = CircleShape,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 60.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp)
                ) {
                    Text(
                        text = "SOS",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Press and hold in case of emergency",
                    color = Color.White.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}