package com.distillery.tvshows.utils

import androidx.navigation.ui.AppBarConfiguration
import com.distillery.tvshows.R

/**
 * Returns [AppBarConfiguration] for app
 */
fun getDefaultAppBarConfiguration(): AppBarConfiguration {
    /**
     * Add top level destinations to the following set
     * Top level destinations won't have a back button.
     */
    val topLevelDestinations = setOf(
        R.id.showsFragment,
        R.id.favoritesFragment
    )
    return AppBarConfiguration(topLevelDestinations)
}