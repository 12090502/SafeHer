package uk.ac.tees.mad.safeher.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrUpdate(contact: ContactsEntity)


    @Update
    suspend fun updateContact(contact: ContactsEntity)

    // Delete a contact
    @Delete
    suspend fun deleteContact(contact: ContactsEntity)



    @Query("SELECT * FROM contacts_table ORDER BY name ASC")
     fun getAllContacts(): Flow<List<ContactsEntity>>




}