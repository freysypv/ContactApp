package com.example.contactapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contactapp.data.Contact
import com.example.contactapp.ui.screens.ContactDetailScreen
import com.example.contactapp.ui.screens.ContactListScreen
import com.example.contactapp.ui.theme.ContactAppTheme
import com.example.contactapp.viewModels.ContactViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ContactViewModel = viewModel()
            val selectedContact = remember { mutableStateOf<Contact?>(null) }
            ContactAppTheme {
                if (selectedContact.value == null) {
                    ContactListScreen(
                        viewModel = viewModel,
                        onContactClick = {
                            contact -> selectedContact.value = contact
                        }
                    )

                }// end of if statement( selected contact is null)
                else {
                    ContactDetailScreen(
                        contactId = selectedContact.value!!.id,
                        viewModel = viewModel,
                        onBackClick = {
                            selectedContact.value = null }
                    )
                }
//                MaterialTheme{
//                    ContactListScreen(
//                    viewModel = viewModel,
//                     onContactClick = { })
//
//
//                }
            }
        }
    }
}

