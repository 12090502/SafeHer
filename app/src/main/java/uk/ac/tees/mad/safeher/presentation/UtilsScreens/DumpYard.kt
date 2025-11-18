package uk.ac.tees.mad.safeher.presentation.UtilsScreens

//    fun getAddressFromCoordinates(context: Context, lat: Double, lon: Double): String {
//        return try {
//            val geocoder = Geocoder(context, Locale.getDefault())
//            val addresses = geocoder.getFromLocation(lat, lon, 1)
//
//            if (!addresses.isNullOrEmpty()) {
//                val address = addresses[0]
//                val city = address.locality ?: "Unknown city"
//                _cityName.value = city
//                address.getAddressLine(0) ?: "Address not found"
//
//            } else {
//                "Address not found"
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            "Unable to get address"
//        }
//    }