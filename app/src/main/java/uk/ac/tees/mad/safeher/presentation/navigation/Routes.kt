package uk.ac.tees.mad.safeher.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Routes {

    @Serializable
    data object AuthScreen

    @Serializable
    data object SingInScreen

    @Serializable
    data object LogInScreen

    @Serializable
    data object HomeScreen

    @Serializable
    data object TrustedContactScreen

    @Serializable
    data object ProfileScreen


}