package com.distillery.tvshows.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.distillery.tvshows.R
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.databinding.FragmentFavoritesBinding
import com.distillery.tvshows.utils.createAndShowDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Favorite TV Shows screen
 */
@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel by viewModels<FavoritesViewModel>()

    private val adapter by lazy { FavoritesAdapter(::onItemClick, ::onLongClick) }

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
        viewModel.loadFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    private fun initObservers() {
        with(viewModel) {
            favorites.observe(viewLifecycleOwner) {
                it?.let {
                    adapter.setData(it)
                }
            }
        }
    }

    private fun onItemClick(tvShow: FavoriteTVShow)  =
        findNavController().navigate(FavoritesFragmentDirections.actionNavigationFavoritesToNavigationDetail(tvShow))

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