package uk.ac.tees.mad.safeher.presentation.ViewModel

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.planty.data.remote.supabase.SupabaseClientProvider
import uk.ac.tees.mad.safeher.data.local.ContactDao
import uk.ac.tees.mad.safeher.data.local.ContactsEntity
import java.util.Locale
import javax.inject.Inject
import kotlin.jvm.java

@HiltViewModel
class HomeViewModel @Inject constructor(private val contactDao: ContactDao) : ViewModel() {
    private val _locationState = MutableStateFlow(Coordinates())

    val locationState: StateFlow<Coordinates> = _locationState.asStateFlow()
    private val _cityName = MutableStateFlow<String>("")
    val cityName: StateFlow<String> = _cityName.asStateFlow()

    private val _fullAddress = MutableStateFlow<String>("")
    val fullAddress: StateFlow<String> = _fullAddress.asStateFlow()

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(context: Context,phoneNumbers: List<String>) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        viewModelScope.launch() {
            try {
                val location = fusedLocationClient.lastLocation.await()
                if (location != null) {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        val city = address.locality ?: "Unknown city"
                        _cityName.value = city
                        _fullAddress.value = address.getAddressLine(0) ?: "Address not found"


                        smsIntent(
                            context = context,
                            cityName = city,
                            lon = location.longitude,
                            lat = location.latitude,
                            phoneNumbers = phoneNumbers
                        )
                    }
                    _locationState.update {
                        it.copy(
                            lon = location.longitude,
                            lat = location.latitude
                        )
                    }


                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun smsIntent(context: Context, cityName: String, lon: Double, lat: Double, phoneNumbers: List<String>) {
        val message =
            " Emergency! I need help. Here's my location: https://maps.google.com/?q=$lon,$lat"



        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:" + phoneNumbers.joinToString(separator = ";"))
            putExtra("sms_body", message)
        }
        context.startActivity(intent)
    }


    val allContacts = contactDao.getAllContacts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    // Insert or update a contact
    fun insertContact(contact: ContactsEntity) {
        viewModelScope.launch {
            contactDao.insertOrUpdate(contact)
        }
    }

    // Update an existing contact
    fun updateContact(contact: ContactsEntity) {
        viewModelScope.launch {
            contactDao.updateContact(contact)
        }
    }

    // Delete a contact
    fun deleteContact(contact: ContactsEntity) {
        viewModelScope.launch {
            contactDao.deleteContact(contact)
        }
    }


    val db = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()



    private val _currentUserData = MutableStateFlow(GetUserInfo())
    val currentUserData: StateFlow<GetUserInfo> = _currentUserData
    fun fetchCurrentUserData() {
        auth.currentUser?.uid?.let { userId ->

            db.collection("user").document(userId).addSnapshotListener { snapshot, e ->

                if (e != null) {

                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val data = snapshot.toObject(GetUserInfo::class.java)
                    data?.let {
                        _currentUserData.value = it
                        Log.d("Firestore","$it")
                    }
                }
            }
        }
    }



    fun updateProfile(
        ProfielImageByteArray: ByteArray,
        name: String,
        mob: String,
        onResult: (String, Boolean) -> Unit,
    ) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            val imageFileName = "safeHer_pf_img/$userId.jpg"

            try {
                val ImageBucket = SupabaseClientProvider.client.storage["safeHer_pf_img"]
                ImageBucket.upload(imageFileName, ProfielImageByteArray, upsert = true)


                val profileImageUrl = ImageBucket.publicUrl(imageFileName)
                val updates = mapOf(
                    "profileImageUrl" to profileImageUrl,
                    "name" to name,
                    "mobNumber" to mob

                )
                db.collection("user").document(userId).update(updates).addOnSuccessListener {
                    onResult("Profile Update Success", true)
                }.addOnFailureListener { e ->
                    onResult(e.toString(), false)
                }

            } catch (e: Exception) {
                onResult(e.toString(), false)
            }
        }




    }

    fun logoutUser() {

        auth.signOut()

    }
}


data class Coordinates(val lon: Double = 0.0, val lat: Double = 0.0)