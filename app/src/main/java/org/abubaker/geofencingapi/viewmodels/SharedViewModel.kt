package org.abubaker.geofencingapi.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.abubaker.geofencingapi.data.DataStoreRepository
import javax.inject.Inject

/**
 * SharedViewModel is a class that is used to SHARED DATA between multiple fragments.
 */

// This annotation will handle all backend work for us, so we do not need any factory
@HiltViewModel
class SharedViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    // We will use this variable to store the value of the firstLaunch
    val app = application

    // DataStore
    val readFirstLaunch = dataStoreRepository.readFirstLaunch.asLiveData()

    // Function to save the firstLaunch value
    fun saveFirstLaunch(firstLaunch: Boolean) =

        // We are using viewModelScope because we are using DataStore
        viewModelScope.launch(Dispatchers.IO) {

            // Save the value of the firstLaunch
            dataStoreRepository.saveFirstLaunch(firstLaunch)

        }


}
