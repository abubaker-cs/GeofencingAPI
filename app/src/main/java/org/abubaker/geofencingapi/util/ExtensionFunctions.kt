package org.abubaker.geofencingapi.util

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

object ExtensionFunctions {

    // Show
    fun View.show() {
        this.visibility = View.VISIBLE
    }

    // Hide
    fun View.hide() {
        this.visibility = View.GONE
    }

    // Enable
    fun View.enable() {
        this.isEnabled = true
    }

    // Disable
    fun View.disable() {
        this.isEnabled = false
    }

    // ObserveOnce
    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

}
