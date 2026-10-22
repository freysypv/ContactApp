package com.example.contactapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// @Database decleares this class as a Room Database
//entities is a list of tables in the database
// Contact::class is the table in dB
// blueprint to database
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class  ContactDatabase : RoomDatabase(){
    abstract fun contactDao() : ContactDao

    companion object{
        @Volatile
        private var _INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            return _INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact_database"
                ).build()
                _INSTANCE = instance
                instance
            }

        }
    }
}
