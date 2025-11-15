package uk.ac.tees.mad.safeher.presentation.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.safeher.presentation.UtilsScreens.BottomNavigation
import uk.ac.tees.mad.safeher.presentation.ViewModel.AuthViewModel
import uk.ac.tees.mad.safeher.presentation.ViewModel.HomeViewModel

@Composable
fun TrustedContactScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController,
) {

    val PrimaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }



    if (showDialog) {
        AddTrustedContactDialog(
            context = context,
            onDismiss = { showDialog = false },
            onSave = { name, number, relationship ->
                Toast.makeText(context, "Saved $name ($relationship)", Toast.LENGTH_SHORT).show()
                showDialog = false
            }
        )
    }

    Scaffold(
        modifier
            .fillMaxSize(), bottomBar = {
            BottomNavigation(
                navController = navController, modifier = modifier
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = !showDialog
                },
                modifier.padding(end = 20.dp, bottom = 20.dp),
                containerColor = Color(0xFF894AFF),
                contentColor = MaterialTheme.colorScheme.background
            ) {

                Icon(
                    imageVector = if (showDialog) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (showDialog) "Close" else "Add"
                )
            }
        }) { paddingValues ->
        Box(
            modifier
                .fillMaxSize()
                .background(brush = PrimaryBrush),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {


            }
        }

    }
}

@Composable
fun AddTrustedContactDialog(
    context: Context,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Add Trusted Contact", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it.take(30) },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = number,
                    onValueChange = { if (it.length <= 10) number = it },
                    label = { Text("Contact Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = relationship,
                    onValueChange = { relationship = it.take(20) },
                    label = { Text("Relationship") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                when {
                    name.isBlank() -> Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show()
                    number.length != 10 -> Toast.makeText(context, "Enter valid 10-digit number", Toast.LENGTH_SHORT).show()
                    relationship.isBlank() -> Toast.makeText(context, "Please enter relationship", Toast.LENGTH_SHORT).show()
                    else -> {
                        onSave(name, number, relationship)
                        onDismiss()
                    }
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
