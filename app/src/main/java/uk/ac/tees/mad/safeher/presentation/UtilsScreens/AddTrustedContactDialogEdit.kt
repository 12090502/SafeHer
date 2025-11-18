package uk.ac.tees.mad.safeher.presentation.UtilsScreens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddTrustedContactDialogEdit(
    context: Context,
    onDismiss: () -> Unit,
    onSave: ( String, String, String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        containerColor = Color(0xFFB289FD),
        title = {
            Text(
                text = "Edit Trusted Contact",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background,
                fontSize = 20.sp
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it.take(30) },
                    label = { Text("Name", color = MaterialTheme.colorScheme.background) },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.background),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                        focusedLabelColor = MaterialTheme.colorScheme.background,
                        unfocusedLabelColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        cursorColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = number,
                    onValueChange = { if (it.length <= 10) number = it },
                    label = {
                        Text(
                            "Contact Number",
                            color = MaterialTheme.colorScheme.background
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.background),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                        focusedLabelColor = MaterialTheme.colorScheme.background,
                        unfocusedLabelColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        cursorColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = relationship,
                    onValueChange = { relationship = it.take(20) },
                    label = { Text("Relationship", color = MaterialTheme.colorScheme.background) },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.background),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                        focusedLabelColor = MaterialTheme.colorScheme.background,
                        unfocusedLabelColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        cursorColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                when {
                    name.isBlank() -> Toast.makeText(
                        context,
                        "Please enter name",
                        Toast.LENGTH_SHORT
                    ).show()

                    number.length != 10 -> Toast.makeText(
                        context,
                        "Enter valid 10-digit number",
                        Toast.LENGTH_SHORT
                    ).show()

                    relationship.isBlank() -> Toast.makeText(
                        context,
                        "Please enter relationship",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        onSave(name, number, relationship)
                        onDismiss()
                    }
                }
            }) {
                Text("Edit", color = MaterialTheme.colorScheme.background)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel", color = MaterialTheme.colorScheme.background)
            }
        }
    )
}
