package uk.ac.tees.mad.safeher.presentation.Screens

import ContactCard
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.safeher.data.local.ContactsEntity
import uk.ac.tees.mad.safeher.presentation.UtilsScreens.AddTrustedContactDialog
import uk.ac.tees.mad.safeher.presentation.UtilsScreens.AddTrustedContactDialogEdit
import uk.ac.tees.mad.safeher.presentation.UtilsScreens.BottomNavigation
import uk.ac.tees.mad.safeher.presentation.ViewModel.AuthViewModel
import uk.ac.tees.mad.safeher.presentation.ViewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
    val contacts by homeViewModel.allContacts.collectAsState()

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddTrustedContactDialog(
            context = context,
            onDismiss = { showDialog = false },
            onSave = { name, number, relationship ->

                homeViewModel.insertContact(
                    contact = ContactsEntity(
                        name = name,
                        relationShip = number,
                        contactNumber = relationship
                    )
                )
                Toast.makeText(context, "Saved $name ($relationship)", Toast.LENGTH_SHORT).show()
                showDialog = false
            }
        )
    }




    Scaffold(
        modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Trusted Contacts",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.background,
                            fontWeight = FontWeight.Bold
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
                navController = navController, modifier = modifier
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (contacts.size < 5) {
                        showDialog = !showDialog
                    } else {
                        Toast.makeText(
                            context,
                            "You can only add up to 5 trusted contacts",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

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
                if (contacts.size != 0) {

                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(contacts) { contact ->
                            ContactCard(
                                contact = contact,
                                onDelete = {
                                    homeViewModel.deleteContact(it)
                                },
                                homeViewModel = homeViewModel
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No contacts found",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.background,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Please add your trusted contacts.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.background
                                ),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { showDialog = !showDialog },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF894AFF)
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "Add Trusted Contact",
                                    color = MaterialTheme.colorScheme.background,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }


            }
        }

    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "SafeHer – Trusted Contacts (Empty State)")
@Composable
fun TrustedContactScreenPreview_Empty() {
    val primaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Trusted Contacts",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFC1A4FA))
            )
        },
        bottomBar = {
            Box(modifier = Modifier.height(66.dp).fillMaxWidth().background(Color.White))
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = Color(0xFF894AFF),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Contact")
            }
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No contacts found",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Please add your trusted contacts.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF894AFF)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Add Trusted Contact", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "SafeHer – Trusted Contacts (With Contacts)")
@Composable
fun TrustedContactScreenPreview_WithContacts() {
    val primaryBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC1A4FA),
            Color(0xFFB289FD),
            Color(0xFFAC7AFF)
        )
    )

    val sampleContacts = listOf(
        ContactsEntity(id = 1, name = "Mom", relationShip = "Mother", contactNumber = "+44 7700 900111"),
        ContactsEntity(id = 2, name = "Best Friend", relationShip = "Friend", contactNumber = "+44 7700 900222"),
        ContactsEntity(id = 3, name = "Sister", relationShip = "Sibling", contactNumber = "+44 7700 900333")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Trusted Contacts",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFC1A4FA))
            )
        },
        bottomBar = {
            Box(modifier = Modifier.height(66.dp).fillMaxWidth().background(Color.White))
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = Color(0xFF894AFF),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Contact")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = primaryBrush)
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sampleContacts) { contact ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Avatar placeholder
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF894AFF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = contact.name.first().toString(),
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(contact.name, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(contact.relationShip, color = Color.White.copy(alpha = 0.8f))
                                Text(contact.contactNumber, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                            }

                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}