package com.example.contactapp.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// @Dao marks this interface as a Data Access Object
// Room reeds this interface at compile time and generates
// The full SQL implementation automatically - you never write SQL manually

@Dao
interface ContactDao {
    // Retrieves all contacts in alphabetical order from the database
    // @Query lets you write custom SQL queries when the standard annotations are not enough
    // Select *  FROM contacts, means get every column from the contacts table
    // suspend runs this disk write operation in a background thread
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getContacts(): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    // Updates a contact in the database
    // @Update lets you write custom SQL queries when the standard annotations are not enough
    // @update tells room to generate a SQL UpDATE statement for you
    // Room matches the contact by ID and updates all other fields with new values
    // suspend runs this disk write operation off the main thread

    @Update
    suspend fun  updateContact(contact: Contact)

    // Deletes a contact from the database
    // @Delete lets you write custom SQL queries when the standard annotations are not enough
    // @delete tells room to generate a SQL DELETE statement for you
    // Room matches the contact by ID and deletes all other fields with new values
    // suspend runs this disk write operation off the main thread

    @Delete
    suspend fun deleteContact(contact: Contact)
}
