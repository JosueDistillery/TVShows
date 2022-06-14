package com.distillery.tvshows.ui.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.distillery.tvshows.R
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.data.enums.NetError
import com.distillery.tvshows.databinding.FragmentShowsBinding
import com.distillery.tvshows.utils.ItemTouchHelperCallback
import com.distillery.tvshows.utils.createAndShowDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * TV Shows screen
 */
@AndroidEntryPoint
class ShowsFragment : Fragment() {

    private val viewModel by viewModels<ShowsViewModel>()

    private val adapter by lazy { ShowsAdapter(::onItemClick) }

    private val itemTouchHelper by lazy { ItemTouchHelper(itemTouchHelperCallback) }
    private val itemTouchHelperCallback by lazy { ItemTouchHelperCallback(::onSwipe) }

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

            itemTouchHelper.attachToRecyclerView(recyclerView)
        }
    }

    private fun initObservers() {
        with(viewModel) {
            tvShows.observe(viewLifecycleOwner) {
                it?.let {
                    adapter.setData(it)
                }
            }

            errorOcurred.observe(viewLifecycleOwner) {
                it?.let {
                    when (it) {
                        NetError.TIMEOUT, NetError.CONNECTIVITY -> {
                            createAndShowDialog(
                                requireContext(),
                                getString(R.string.oops_something_went_wrong),
                                getString(R.string.retry_action_connection),
                                onPositiveAction = ::loadTVShows
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onItemClick(tvShow: FavoriteTVShow) {
        findNavController().navigate(ShowsFragmentDirections.actionNavigationShowsToNavigationDetail(tvShow))
    }

    private fun onSwipe(tvShow: FavoriteTVShow, position: Int) = try {
        doFavoriteAction(tvShow, position)
    } catch (error: Throwable) {
        createAndShowDialog(
            requireContext(),
            getString(R.string.oops_something_went_wrong),
            getString(R.string.retry_action_favorite),
            onPositiveAction = { doFavoriteAction(tvShow, position) }
        )
    }

    private fun doFavoriteAction(tvShow: FavoriteTVShow, position: Int) {
        viewModel.doFavoriteAction(tvShow)
        adapter.setItemChanged(tvShow, position)
    }
}