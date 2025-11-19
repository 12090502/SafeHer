package uk.ac.tees.mad.safeher.presentation.ViewModel

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.safeher.data.local.ContactDao
import uk.ac.tees.mad.safeher.data.local.ContactsEntity
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val contactDao: ContactDao) : ViewModel() {
    private val _locationState = MutableStateFlow(Coordinates())

    val locationState: StateFlow<Coordinates> = _locationState.asStateFlow()
    private val _cityName = MutableStateFlow<String>("")
    val cityName: StateFlow<String> = _cityName.asStateFlow()

    private val _fullAddress = MutableStateFlow<String>("")
    val fullAddress: StateFlow<String> = _fullAddress.asStateFlow()

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(context: Context) {
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
}


data class Coordinates(val lon: Double = 0.0, val lat: Double = 0.0)