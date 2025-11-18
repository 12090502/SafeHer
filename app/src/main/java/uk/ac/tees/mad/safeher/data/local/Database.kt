package uk.ac.tees.mad.safeher.data.local

import android.provider.Contacts
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ ContactsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {


    abstract fun contactDao(): ContactDao


}