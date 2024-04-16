package com.nimesh.interviewtask.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.nimesh.interviewtask.R
import com.nimesh.interviewtask.Utils.permissionDialog
import com.nimesh.interviewtask.databinding.ActivityHomeBinding
import com.nimesh.interviewtask.viewmodel.MediaCoverageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private var isFirstTime = true
    private val readExternal = Manifest.permission.READ_EXTERNAL_STORAGE
    private val readVideo = Manifest.permission.READ_MEDIA_VIDEO
    private val readImages = Manifest.permission.READ_MEDIA_IMAGES
    private val permissionsForApi33 = arrayOf(
        readVideo, readImages
    )
    private lateinit var adapter: MediaCoverageAdapter
    private val viewModel: MediaCoverageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = MediaCoverageAdapter(this)
        binding.mediaCoverageRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.mediaCoverageRecyclerView.adapter = adapter
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        requestAppPermissions()
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            viewModel.mediaCoverages.collectLatest { pagingData ->
                pagingData?.let {
                    adapter.submitData(it)
                }

            }
        }


        lifecycleScope.launch {
            viewModel.permissionGranted.collectLatest { permissionGranted ->
                Log.e(TAG, "onStart: permissionGranted : $permissionGranted")
                if (permissionGranted) {
                    viewModel.getMediaCoverages()
                }
            }
        }

    }

    private fun requestAppPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notGrantedPermissions = permissionsForApi33.filterNot { permission ->
                ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }
            val positiveAction = Runnable {
                api33AndAbovePermission.launch(notGrantedPermissions.toTypedArray())
            }
            if (notGrantedPermissions.isNotEmpty()) {
                val showRationale = notGrantedPermissions.any { permission ->
                    shouldShowRequestPermissionRationale(permission)
                }
                if (showRationale) {
                    val negativeAction = Runnable {
                        Toast.makeText(
                            this,
                            getString(R.string.media_permission_denied),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    permissionDialog(
                        context = this,
                        title = getString(R.string.storage_permission),
                        description = getString(R.string.storage_permission_desc),
                        positiveText = getString(R.string.ok_btn),
                        negativeText = getString(R.string.cancel_btn),
                        positiveAction = positiveAction,
                        negativeAction = negativeAction
                    )
                } else {
                    if (!isFirstTime) {
                        navigateToAppSettingsDialog()
                    } else {
                        isFirstTime = false
                        positiveAction.run()
                    }
                }
            } else {
                managePermission(true)
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    readExternal
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val positiveAction = Runnable { belowApi33Permission.launch(readExternal) }
                if (shouldShowRequestPermissionRationale(readExternal)) {
                    val negativeAction = Runnable {
                        Toast.makeText(
                            this,
                            getString(R.string.media_permission_denied),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    permissionDialog(
                        context = this,
                        title = getString(R.string.storage_permission),
                        description = getString(R.string.storage_permission_desc),
                        positiveText = getString(R.string.ok_btn),
                        negativeText = getString(R.string.cancel_btn),
                        positiveAction = positiveAction,
                        negativeAction = negativeAction
                    )
                } else {
                    if (!isFirstTime) {
                        navigateToAppSettingsDialog()
                    } else {
                        isFirstTime = false
                        positiveAction.run()
                    }
                }
            } else {
                managePermission(true)
            }
        }
    }

    private val api33AndAbovePermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionMap ->
            if (permissionMap.all { it.value }) {
                managePermission(true)
                Log.d(TAG, "Read external storage permission granted")
            } else {
                managePermission(false)
                Log.d(TAG, "Media permissions not granted!")
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }
            return@registerForActivityResult
        }

    private fun managePermission(isGranted: Boolean) {
        viewModel.updatePermissionGrant(isGranted)
    }

    private val belowApi33Permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                managePermission(true)
                Log.d(TAG, "Read external storage permission granted")
            } else {
                managePermission(false)
                Log.d(TAG, "Read external storage permission denied!")
            }
            return@registerForActivityResult
        }

    private fun navigateToAppSettingsDialog() {
        val positiveAction =
            Runnable { navigateToAppSettings() }
        permissionDialog(
            context = this,
            title = getString(R.string.storage_permission),
            description = getString(R.string.storage_permission_desc),
            positiveText = getString(R.string.ok_btn),
            negativeText = null,
            positiveAction = positiveAction,
            negativeAction = null
        )
    }

    private fun navigateToAppSettings() {
        val intent = Intent()
        intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        lifecycleScope.cancel()
    }

    companion object {
        private const val TAG = "HomeActivity"
    }

}