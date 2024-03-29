package com.distillery.tvshows

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.distillery.tvshows.databinding.ActivityMainBinding
import com.distillery.tvshows.utils.getDefaultAppBarConfiguration
import dagger.hilt.android.AndroidEntryPoint

/**
 * Parent screen from all fragments
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val isDualPane by lazy { resources.getBoolean(R.bool.isDualPane) }

    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initUi()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->  if (isDualPane) return navController.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUi() {
        setupActionBarWithNavController(navController, getDefaultAppBarConfiguration())
        binding?.bottomNavView?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(getDefaultAppBarConfiguration()) || super.onSupportNavigateUp()
    }
}