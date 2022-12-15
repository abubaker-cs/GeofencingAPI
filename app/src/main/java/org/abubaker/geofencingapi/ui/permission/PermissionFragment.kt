package org.abubaker.geofencingapi.ui.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.abubaker.geofencingapi.R
import org.abubaker.geofencingapi.databinding.FragmentPermissionBinding
import org.abubaker.geofencingapi.util.Permissions
import org.abubaker.geofencingapi.viewmodels.SharedViewModel


class PermissionFragment : Fragment() {

    private var _binding: FragmentPermissionBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // Get the SharedViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentPermissionBinding.inflate(inflater, container, false)

        // Continue Button
        binding.continueButton.setOnClickListener {

            if (Permissions.hasLocationPermission(requireContext())) {
                checkFirstLaunch()
            } else {
                Permissions.requestsLocationPermission(this)
            }

        }


        return binding.root
    }

    private fun checkFirstLaunch() {
        sharedViewModel.readFirstLaunch.observe(viewLifecycleOwner) { firstLaunch ->

            // If it's the first launch, then we will navigate to the MapFragment
            if (firstLaunch) {

                // Navigate to the MapFragment (only for the first time
                findNavController().navigate(R.id.action_permissionFragment_to_add_geofence_graph)
                sharedViewModel.saveFirstLaunch(false)
            } else {

                // Navigate to the GeofenceListFragment
                findNavController().navigate(R.id.action_permissionFragment_to_mapsFragment)

            }
            
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
