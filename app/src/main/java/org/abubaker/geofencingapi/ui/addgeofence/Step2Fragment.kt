package org.abubaker.geofencingapi.ui.addgeofence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.abubaker.geofencingapi.databinding.FragmentStep2Binding
import org.abubaker.geofencingapi.viewmodels.SharedViewModel

class Step2Fragment : Fragment() {

    private var _binding: FragmentStep2Binding? = null
    private val binding get() = _binding!!

    // Get the SharedViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStep2Binding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
