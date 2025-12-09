package uk.ac.tees.mad.safeher.presentation.Screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import uk.ac.tees.mad.safeher.R
import uk.ac.tees.mad.safeher.presentation.ViewModel.AuthViewModel
import uk.ac.tees.mad.safeher.presentation.ViewModel.HomeViewModel
import uk.ac.tees.mad.safeher.presentation.navigation.Routes

@Composable
fun SingInScreen(modifier: Modifier = Modifier,
                 homeViewModel: HomeViewModel,
                 authViewModel: AuthViewModel,
                 navController: NavController) {



    val PrimaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )
    val textColor = Color(0xFF010002)
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var Triggeer by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Triggeer) {
        delay(3000)
        if (passwordVisible){
            passwordVisible = !passwordVisible
        }

    }
    val context = LocalContext.current
    val cornerShape = RoundedCornerShape(14.dp)
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val passwordRegex = Regex("^(?=.*[!@#\$%^&*(),.?\":{}|<>]).{6,10}\$")
    val isPasswordValid = passwordRegex.matches(password)
    val isFormValid = name.isNotBlank() && isEmailValid && isPasswordValid
    val bgColor = Color(0xFFB289FD)

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
                .padding(horizontal = 10.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { input ->
                    name = input.split(" ").joinToString(" ") { word ->
                        if (word.isNotEmpty()) word.replaceFirstChar { it.uppercase() }
                        else word
                    }
                },
                placeholder = { Text("Name", color = textColor.copy(alpha = 0.6f)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                shape = cornerShape,
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = textColor,
                    disabledTextColor = textColor,
                    focusedContainerColor = bgColor,
                    unfocusedContainerColor = bgColor,
                    disabledContainerColor = bgColor,
                    focusedIndicatorColor = textColor,
                    unfocusedIndicatorColor = textColor,
                    disabledIndicatorColor = textColor,
                    focusedLabelColor = textColor,
                    unfocusedLabelColor = textColor,
                    disabledLabelColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(18.dp))
            val errorColor = Color(0xFFD32F2F)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email", color = textColor.copy(alpha = 0.6f)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                shape = cornerShape,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = textColor,
                    disabledTextColor = textColor,
                    focusedContainerColor = bgColor,
                    unfocusedContainerColor = bgColor,
                    disabledContainerColor = bgColor,
                    focusedIndicatorColor = textColor,
                    unfocusedIndicatorColor = textColor,
                    disabledIndicatorColor = textColor,
                    focusedLabelColor = textColor,
                    unfocusedLabelColor = textColor,
                    disabledLabelColor = textColor
                ), maxLines = 1
            )

            Spacer(modifier = Modifier.height(18.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password", color = textColor.copy(alpha = 0.6f)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                shape = cornerShape,
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                        Triggeer = !Triggeer
                    }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.baseline_visibility_24
                                else R.drawable.outline_visibility_off_24
                            ),
                            contentDescription = null, tint = textColor
                        )
                    }
                },

                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = textColor,
                    disabledTextColor = textColor,
                    focusedContainerColor = bgColor,
                    unfocusedContainerColor = bgColor,
                    disabledContainerColor = bgColor,
                    focusedIndicatorColor = textColor,
                    unfocusedIndicatorColor = textColor,
                    disabledIndicatorColor = textColor,
                    focusedLabelColor = textColor,
                    unfocusedLabelColor = textColor,
                    disabledLabelColor = textColor
                ), maxLines = 1
            )




        }

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            OutlinedButton(
                onClick = {
                    authViewModel.signUp(
                        email = email,
                        password = password,
                        name = name,
                        onResult = { message, success ->
                            if (success) {
                                isLoading = true
                                navController.navigate(Routes.HomeScreen)
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                isLoading = false
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )



                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(52.dp),
                shape = cornerShape,
                border = BorderStroke(2.dp, textColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Green
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(30.dp)
                    )
                } else {
                    Text(
                        "Sign Up",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(32.dp))
        }



    }

}

@Preview(showBackground = true, name = "SafeHer – Sign Up Screen")
@Composable
fun SingInScreenPreview() {
    val primaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )

    var name by rememberSaveable { mutableStateOf("Sarah Johnson") }
    var email by rememberSaveable { mutableStateOf("sarah.j@example.com") }
    var password by rememberSaveable { mutableStateOf("Safe@2025") }
    var passwordVisible by rememberSaveable { mutableStateOf(true) }

    val bgColor = Color(0xFFB289FD)
    val textColor = Color(0xFF010002)

    Box(modifier = Modifier.fillMaxSize().background(brush = primaryBrush)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Name", color = textColor.copy(alpha = 0.6f)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = bgColor,
                    unfocusedContainerColor = bgColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = textColor,
                    focusedIndicatorColor = textColor,
                    unfocusedIndicatorColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email", color = textColor.copy(alpha = 0.6f)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = bgColor,
                    unfocusedContainerColor = bgColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = textColor,
                    focusedIndicatorColor = textColor,
                    unfocusedIndicatorColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password", color = textColor.copy(alpha = 0.6f)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.baseline_visibility_24
                                else R.drawable.outline_visibility_off_24
                            ),
                            contentDescription = null,
                            tint = textColor
                        )
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = bgColor,
                    unfocusedContainerColor = bgColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = textColor,
                    focusedIndicatorColor = textColor,
                    unfocusedIndicatorColor = textColor
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(2.dp, textColor),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    "Sign uppf",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {}) {
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

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}