package org.abubaker.geofencingapi.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.abubaker.geofencingapi.util.Constants.PREFERENCE_FIRST_LAUNCH
import org.abubaker.geofencingapi.util.Constants.PREFERENCE_NAME
import java.io.IOException

// Name: geofence_preference
private val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

class DataStoreRepository(private val context: Context) {

    // Key: firstLaunch
    private object PreferenceKey {
        val firstLaunch = booleanPreferencesKey(PREFERENCE_FIRST_LAUNCH)
    }

    // Reference to DataStore
    private val dataStore: DataStore<Preferences> = context.dataStore

    // Store + Save Boolean value in the dataStore
    suspend fun saveFirstLaunch(firstLaunch: Boolean) {

        // Edit: We want to write
        dataStore.edit { preference ->
            preference[PreferenceKey.firstLaunch] = firstLaunch
        }

    }

    val readFirstLaunch: Flow<Boolean> = dataStore.data

        // Catch any error
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->

            // Get the value of the key
            val firstLaunch = preference[PreferenceKey.firstLaunch] ?: false

            // Return the firstLaunch value
            firstLaunch

        }

}
