package com.example.contactapp.ui.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.contactapp.data.Contact


@Composable
fun ContactDialog(
    contact: Contact? = null, // a contact that don't exist in the database
    onConfirm: (Contact) -> Unit,
    onDismiss: () -> Unit
){
    // it is like a toggle in the database In other languages it is called a modal
    // Local variables for the three input fields
    // by {mutableState} is use because they are local variables
    var name by remember { mutableStateOf(contact?.name ?: "") } // to possibility to edit the name
    // challenge
    var phoneNumber by remember { mutableStateOf(contact?.phoneNumber ?: "") }// to possibility to edit the phone number or place a new one
    var email by remember { mutableStateOf(contact?.email ?: "") }// to possibility to edit the email

    // Determine the title bases on whether we are editing or adding a new contact or add a new contact
    // contact == null means that we are adding a new contact, otherwise we are editing an existing contact
    val title = if (contact == null) "Add Contact" else "Edit Contact"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Column (
                modifier = Modifier.padding(all=.8.dp) ){
                // challenge

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(text = "Name") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.small,
                    supportingText = {
                        Text(text = "Required")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = { Text(text = "Phone Number") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)

                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text(text = "Email") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

            }
        },

        confirmButton = {
            Button(
                onClick = {
                    val newContact = Contact(
                        id = contact?.id ?: 0,
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email
                    ) // end of newContact variable
                    onConfirm(newContact)
                }
            ) {
                Text("Save")
            }
        },
        // Dismiss button
        // challenge
        dismissButton ={
                Button(
                    onClick = onDismiss
                ){
            Text("Cancel")}

        }

    )
}
