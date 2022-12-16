package org.abubaker.geofencingapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.abubaker.geofencingapi.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}


/**
 * Issue: Hilt
 * https://github.com/google/dagger/issues/2548
 * https://github.com/google/dagger/issues/2548#issuecomment-820282848
 *
 *
 */
