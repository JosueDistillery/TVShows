package com.distillery.tvshows.ui.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.distillery.tvshows.R
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.data.model.TVShow
import com.distillery.tvshows.databinding.FragmentShowsBinding
import com.distillery.tvshows.utils.createAndShowDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * TV Shows screen
 */
@AndroidEntryPoint
class ShowsFragment : Fragment() {

    private val viewModel by viewModels<ShowsViewModel>()

    private val adapter by lazy { ShowsAdapter(::onItemClick, ::onLongClick) }

    private val supportActionBar by lazy { (requireActivity() as AppCompatActivity).supportActionBar }

    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        supportActionBar?.title = getString(R.string.app_name)

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    private fun initObservers() {
        with(viewModel) {
            tvShows.observe(viewLifecycleOwner) {
                it?.let {
                    adapter.setData(it)
                }
            }

            timeoutOcurred.observe(viewLifecycleOwner) {
                if (it) {
                    createAndShowDialog(
                        requireContext(),
                        getString(R.string.oops_something_went_wrong),
                        getString(R.string.retry_action_connection),
                        onPositiveAction = {
                            loadTVShows()
                        }
                    )
                }
            }

            hasConnectivity.observe(viewLifecycleOwner) {
                if (!it) {
                    createAndShowDialog(
                        requireContext(),
                        getString(R.string.oops_something_went_wrong),
                        getString(R.string.retry_action_connection),
                        onPositiveAction = {
                            loadTVShows()
                        }
                    )
                }
            }
        }
    }

    private fun onItemClick(tvShow: TVShow) {
        val tvShowDetail = FavoriteTVShow.from(tvShow)
        findNavController().navigate(ShowsFragmentDirections.actionNavigationShowsToNavigationDetail(tvShowDetail))
    }

    private fun onLongClick(tvShow: TVShow) {
        lifecycleScope.launch(Dispatchers.Main) {
            val isFavoriteTVShow = viewModel.isFavoriteTVShow(tvShow.id)
            createAndShowDialog(
                requireContext(),
                "",
                if (isFavoriteTVShow) getString(R.string.delete_action_favorite) else getString(R.string.add_action_favorite),
                getString(R.string.ok),
                onPositiveAction = {
                    requireFavoriteAction(tvShow, isFavoriteTVShow)
                }
            )
        }
    }

    private fun requireFavoriteAction(tvShow: TVShow, isFavoriteTVShow: Boolean) = try {
        if (isFavoriteTVShow)
            viewModel.removeFavorite(tvShow)
        else
            viewModel.addFavorite(tvShow)
    } catch (error: Throwable) {
        createAndShowDialog(
            requireContext(),
            getString(R.string.oops_something_went_wrong),
            getString(R.string.retry_action_favorite),
            onPositiveAction = {
                if (isFavoriteTVShow)
                    viewModel.removeFavorite(tvShow)
                else
                    viewModel.addFavorite(tvShow)
            }
        )
    }
}