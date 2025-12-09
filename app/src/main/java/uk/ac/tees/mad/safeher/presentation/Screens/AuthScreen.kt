package uk.ac.tees.mad.safeher.presentation.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import okhttp3.internal.wait
import uk.ac.tees.mad.safeher.R
import uk.ac.tees.mad.safeher.presentation.ViewModel.AuthViewModel
import uk.ac.tees.mad.safeher.presentation.ViewModel.HomeViewModel
import uk.ac.tees.mad.safeher.presentation.navigation.Routes

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    navController: NavController,
) {

    val cornerShape = RoundedCornerShape(14.dp)

    val PrimaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )
    val textColor = Color(0xFF6D3DFF)

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding()
            .background(
                brush = PrimaryBrush
            )
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.safe_her),
                contentDescription = "Weather illustration",
                modifier = Modifier
                    .fillMaxWidth(),
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Stay safe and confident — get instant alerts, trusted contacts, and real-time help wherever you are.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF000000),
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.padding(horizontal = 20.dp)
            )





        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = {
                    navController.navigate(Routes.SingInScreen)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(52.dp),
                shape = cornerShape,
                border = BorderStroke(2.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Green
                )
            ) {
                if (false) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(30.dp)
                    )
                } else {

                    Text(
                        "Let's get started",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color =  Color.Black
                    )

                }
            }


            TextButton(onClick = { navController.navigate(Routes.LogInScreen) }) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black
                            )
                        ) { append("Already have an account? ") }

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF0073FF),
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Medium
                            )
                        ) { append("Log in") }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))


    }
}

@Preview(showBackground = true, name = "SafeHer – Onboarding / Auth Screen")
@Composable
fun AuthScreenPreview() {
    val primaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = primaryBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.safe_her),
                contentDescription = "SafeHer illustration",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Stay safe and confident — get instant alerts, trusted contacts, and real-time help wherever you are.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.padding(horizontal = 20.dp),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(2.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    "Let's get started",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            TextButton(onClick = { }) {
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(color = Color.Black)) { append("Already have an account? ") }
                        withStyle(
                            SpanStyle(
                                color = Color(0xFF0073FF),
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Medium
                            )
                        ) { append("Log in") }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}