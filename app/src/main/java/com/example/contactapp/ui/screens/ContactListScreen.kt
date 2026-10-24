package com.example.contactapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.contactapp.data.Contact
import com.example.contactapp.ui.componets.ContactDialog
import com.example.contactapp.ui.componets.ContactItem
import com.example.contactapp.viewModels.ContactViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    viewModel: ContactViewModel,
    onContactClick: (Contact) -> Unit,
) {
    val contacts by viewModel.contacts.collectAsState()
    var showAddContactDialog by remember { mutableStateOf(false) }
    if (showAddContactDialog) {
        ContactDialog(
            // Challenge add the onConfirm and onDismiss parameters
            // onConfirm: (Contact) -> Unit,
            contact = null,
            onConfirm = { newContact ->
                viewModel.saveContact(newContact)
                showAddContactDialog = false // close the screen

            },
            onDismiss = {
                showAddContactDialog = false
            } // end of onDismiss
        )
    } // end of if statement
    // Scaffold provides the material layout structure
    // topBar, bottomBar, floatingActionButton, content
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Contacts") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }, // end of topBar
        // add floating action button
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddContactDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Contact")
            }
        }


    ) { paddingValues ->
        // challenge if there are no contacts show a message
        // Show a message if there are no contacts
        if (contacts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "No contacts available. \n Tap + to add a contact",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        } // end of if statement
            // Show the list of contacts
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(contacts, key = { it.id }) { contact ->
                    ContactItem(
                        contact = contact,
                        onClick = { onContactClick(contact) },
                        onDeleteClick = { viewModel.deleteContact(contact) }
                    )
                }

            } // End of LazyColumn
        } // end of else statement


    } // end of Scaffold
}// end of ContactListScreen
