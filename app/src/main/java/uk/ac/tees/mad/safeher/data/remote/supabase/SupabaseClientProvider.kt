package uk.ac.tees.mad.planty.data.remote.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.storage.Storage

object SupabaseClientProvider {
    val client =
        createSupabaseClient(
            supabaseUrl = "https://kqtfrcvmdxrfqgdsgmll.supabase.co",
            supabaseKey = "sb_publishable_R2Z_X3t_WgA58CVTQSQVfw_jutJhFDK"
        ) {
            install(GoTrue.Companion)
            install(Storage.Companion)
        }


}
