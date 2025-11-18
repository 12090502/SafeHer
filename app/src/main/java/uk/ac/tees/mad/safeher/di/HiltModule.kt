package uk.ac.tees.mad.safeher.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import uk.ac.tees.mad.safeher.data.local.AppDatabase
import uk.ac.tees.mad.safeher.data.local.ContactDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)


    @Provides
    @Singleton
    fun providesDB(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java,"app_db").build()
    }

    @Provides
    fun providesDao(db: AppDatabase): ContactDao{
        return db.contactDao()
    }

}