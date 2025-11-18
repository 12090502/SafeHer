package uk.ac.tees.mad.safeher.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("contacts_table")
data class ContactsEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val relationShip: String,
    val contactNumber: String


)