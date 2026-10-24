package com.example.contactapp.ui.screens

import android.R.attr.navigationIcon
import android.R.attr.title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.TimePickerDefaults.colors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDragHandleDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.contactapp.ui.componets.ContactDialog
import com.example.contactapp.viewModels.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    contactId: Int,
    viewModel: ContactViewModel,
    onBackClick: () -> Unit
) {
    // get the contact from the viewModel
    val contacts by viewModel.contacts.collectAsState()
    // find the contact in the list. using the id
    val contact = contacts.find { it.id == contactId }
    // If contact was deleted go back to the list

    if (contact == null) {
        onBackClick()
        return // end the function if contact is null
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    // call the contactDialog with the contact detail
    if (showEditDialog) {
        ContactDialog(
            contact = contact,
            onConfirm = { updatedContact ->
                viewModel.updateContact(updatedContact)
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false },


            )
    }
    // challenge
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = { Text(text = "Delete Confirmation") },
            text = { Text("Delete this contact? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteContact(contact)
                    showDeleteDialog = false
                })
                { Text(text = "Delete") }
            },
            dismissButton = {

                Button(onClick = {
                    showDeleteDialog = false
                })
                { Text(text = "Cancel") }
            } // end of dismissButton
        )// end of AlertDialog
    } // end of if statement
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Contact"
                        )
                    }
                    // Challenge add the delete button
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Contact"
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // challenge add the contact details name
            Text(
                text = contact.name,
                style = MaterialTheme.typography.headlineMedium
            )
            // challenge add the contact details phone number
            Text(
                text = contact.phoneNumber,
                style = MaterialTheme.typography.bodyLarge
            )
            // challenge add the contact details email
            if (contact.email.isNotEmpty()){
            Text(
                text = contact.email,
                style = MaterialTheme.typography.bodyLarge

            )}

        }

    }

}


// end of ContactDetailScreen

