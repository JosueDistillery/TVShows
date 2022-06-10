package com.distillery.tvshows

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.databinding.ActivityMainBinding
import com.distillery.tvshows.databinding.FragmentDetailBinding
import com.distillery.tvshows.databinding.FragmentFavoritesBinding
import com.distillery.tvshows.ui.detail.DetailFragment
import com.distillery.tvshows.utils.getDefaultAppBarConfiguration
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Boolean.getBoolean

/**
 * Parent screen from all fragments
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val navController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> navController.popBackStack()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initUi() {
        with(binding) {
            setupActionBarWithNavController(navController, getDefaultAppBarConfiguration())
            bottomNavView.setupWithNavController(navController)
        }
    }
}