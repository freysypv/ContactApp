package com.example.contactapp.ui.componets

import android.R
import android.R.attr.icon
import android.R.id.icon
import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import com.example.contactapp.data.Contact

@Composable
fun ContactItem(
    contact: Contact,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var showConfirmationDialog by remember { mutableStateOf(false) }
    // Delete confirmation dialog
    if (showConfirmationDialog) {
        // Challenge Create the delete confirmation dialog
        AlertDialog(
            onDismissRequest = {
                showConfirmationDialog = false
            },
            title = { Text(text = "Delete Confirmation") },
            text = { Text("Are you sure you want to delete this contact?") },
            confirmButton = {
                Button(onClick = {
                    onDeleteClick()
                    showConfirmationDialog = false
                })
                { Text(text = "Delete") }
            },
            dismissButton = {

                Button(onClick = {
                    showConfirmationDialog = false
                })
                { Text(text = "Cancel") }
            }
        )

    } // end of if statement
    // Cards  wraps the contact information in a card with elevation and styling
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically

        ){
            // Challenge add person avatar
            Icon(imageVector = Icons.Default.Person,
                contentDescription = "Person Icon",
                tint = MaterialTheme.colorScheme.primary,
             modifier = Modifier
                 .size(42.dp)
                )

            // challenge add contact name
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
                    .size(42.dp)
            ) {
                Text(text = contact.name,
                    style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = contact.phoneNumber,
                    style = MaterialTheme.typography.bodySmall
                    //color = MaterialTheme.colorScheme.onSurfacevariant
                )

                Spacer(modifier = Modifier.width(16.dp))


            }
            // End of the column for the contact name and phone number
            // challenge add delete button
            IconButton(
                onClick = { showConfirmationDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Contact",
                    tint = MaterialTheme.colorScheme.secondary

                )
            }


            Spacer(modifier = Modifier.width(16.dp))

        }

    }
}

@Preview(showBackground = true)
@Composable
fun ContactItemPreview(){
    ContactItem(
        contact = Contact(
            id = 1,
            name = "John Doe",
            phoneNumber = "1234567890",),
        onClick = {},
        onDeleteClick= {}
    )
}