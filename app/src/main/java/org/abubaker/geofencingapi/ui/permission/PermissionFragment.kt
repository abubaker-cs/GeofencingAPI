package org.abubaker.geofencingapi.ui.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import org.abubaker.geofencingapi.R
import org.abubaker.geofencingapi.databinding.FragmentPermissionBinding
import org.abubaker.geofencingapi.util.ExtensionFunctions.observeOnce
import org.abubaker.geofencingapi.util.Permissions
import org.abubaker.geofencingapi.viewmodels.SharedViewModel

@AndroidEntryPoint
class PermissionFragment : Fragment(), EasyPermissions.PermissionCallbacks {

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

        // Check if the app is launched for the first time using our CUSTOM Extension Function:
        // .observeOnce()
        sharedViewModel.readFirstLaunch.observeOnce(viewLifecycleOwner) { firstLaunch ->

            // If it's the first launch, then we will navigate to the MapFragment
            if (firstLaunch) {

                // Navigate to the MapFragment (only for the first time
                findNavController().navigate(R.id.action_permissionFragment_to_add_geofence_graph)

                // Update the value of the firstLaunch to false
                sharedViewModel.saveFirstLaunch(false)

            } else {

                // Since we do not have any permission on the 1st launch, that's why we will ask the
                // user to grant the permission
                findNavController().navigate(R.id.action_permissionFragment_to_mapsFragment)

            }

        }
    }

    // Permission: Denied
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        if (EasyPermissions.somePermissionDenied(this, perms.first())) {
            SettingsDialog.Builder(requireContext()).build().show()
        } else {
            Permissions.requestsLocationPermission(this)
        }

    }

    // Permission: Granted
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            requireContext(),
            "Permission Granted! Tap on 'Continue' button to proceed.",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on [.requestPermissions].
     *
     *
     * **Note:** It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     *
     *
     * @param requestCode The request code passed in [.requestPermissions].
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     * which is either [android.content.pm.PackageManager.PERMISSION_GRANTED]
     * or [android.content.pm.PackageManager.PERMISSION_DENIED]. Never null.
     *
     * @see .requestPermissions
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        // This is the callback from the EasyPermissions library
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
