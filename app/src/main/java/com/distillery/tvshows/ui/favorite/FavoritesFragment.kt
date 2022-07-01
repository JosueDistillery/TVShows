package com.distillery.tvshows.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.distillery.tvshows.R
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.databinding.FragmentFavoritesBinding
import com.distillery.tvshows.ui.detail.DetailFragmentDirections
import com.distillery.tvshows.utils.DualPaneOnBackPressedCallback
import com.distillery.tvshows.utils.createAndShowDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * Favorite TV Shows screen
 */
@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel by viewModels<FavoritesViewModel>()

    private val isDualPane by lazy { resources.getBoolean(R.bool.isDualPane) }

    private val adapter by lazy { FavoritesAdapter(::onItemClick, ::onLongClick) }

    private var binding: FragmentFavoritesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
        viewModel.loadFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initUi() {
        binding?.let {
            it.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            it.recyclerView.adapter = adapter

            it.slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                DualPaneOnBackPressedCallback(it.slidingPaneLayout)
            )
        }
    }

    private fun initObservers() {
        viewModel.favorites.observe(viewLifecycleOwner) {
            it?.let { adapter.setData(it) }
        }
    }

    private fun onItemClick(tvShow: FavoriteTVShow) {
        if (isDualPane) {
            binding?.let {
                it.detailNavHostFragment?.findNavController()
                    ?.navigate(DetailFragmentDirections.actionDetailFragment(tvShow))
                it.slidingPaneLayout.open()
            }
        } else {
            findNavController().navigate(FavoritesFragmentDirections.actionDetailFragment(tvShow))
        }
    }

    private fun onLongClick(tvShow: FavoriteTVShow, position: Int) {
        createAndShowDialog(
            requireContext(),
            "",
            getString(R.string.delete_action_favorite),
            getString(R.string.ok),
            onPositiveAction = {
                viewModel.removeFavorite(tvShow)
                adapter.setItemRemoved(position)
            }
        )
    }
}