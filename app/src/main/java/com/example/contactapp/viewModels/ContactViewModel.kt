package com.example.contactapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactapp.data.Contact
import com.example.contactapp.data.ContactDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactViewModel (application: Application): AndroidViewModel(application){
    // get the Dao from database singleton class
    //This is the single entry point of access to the database operations
    // the DAO was created in the ContactDatabase.getContactDao()
    // so calling it again returns the same instance of the DAO
    private val _dao = ContactDatabase.getDatabase(application).contactDao()
    // Expose the data list of contact as a StateFlow
    // StateFlow is a hot flow that emits the current and new value
    // state.in lets the flow start emitting values
    // scope = viewModelScope means that the flow will be canceled when the ViewModel is cleared
    val contacts : StateFlow<List<Contact>> = _dao.getContacts().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed( stopTimeoutMillis = 5000),
        initialValue = emptyList()
    )
    // Insert a new contact
    fun saveContact(contact: Contact){
        viewModelScope.launch {
            _dao.insertContact(contact)
        }
    }

    // Update an existing contact
    //same pattern as saveContact - background coroutine, non-blocking

    // challenge 1: Create a function to update a contact
    fun updateContact(contact: Contact){
        viewModelScope.launch {
            _dao.updateContact(contact)
        }
    }
    //challenge 2: Create a function to delete a contact
    fun deleteContact(contact: Contact){
        viewModelScope.launch{
            _dao.deleteContact(contact)
        }
    }




}
