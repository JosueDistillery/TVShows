package com.distillery.tvshows.ui.shows

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.distillery.tvshows.R
import com.distillery.tvshows.data.entity.TVShow
import com.distillery.tvshows.data.enums.NetError
import com.distillery.tvshows.databinding.FragmentShowsBinding
import com.distillery.tvshows.ui.detail.DetailFragmentDirections
import com.distillery.tvshows.utils.DualPaneOnBackPressedCallback
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

    private val isDualPane by lazy { resources.getBoolean(R.bool.isDualPane) }

    private val itemTouchHelper by lazy { ItemTouchHelper(ItemTouchHelperCallback(::onSwipe)) }

    private val supportActionBar by lazy { (requireActivity() as AppCompatActivity).supportActionBar }

    private var binding: FragmentShowsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
        viewModel.loadTVShows()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initUi() {
        supportActionBar?.title = getString(R.string.app_name)

        binding?.let {
            it.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            it.recyclerView.adapter = adapter

            itemTouchHelper.attachToRecyclerView(it.recyclerView)

            it.slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                DualPaneOnBackPressedCallback(it.slidingPaneLayout)
            )
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
                        else -> {}
                    }
                }
            }
        }
    }

    private fun onItemClick(tvShow: TVShow) {
        if (isDualPane) {
            binding?.let {
                it.detailNavHostFragment?.findNavController()
                    ?.navigate(DetailFragmentDirections.actionDetailFragment(tvShow))
                it.slidingPaneLayout.open()
            }
        } else{
            findNavController().navigate(ShowsFragmentDirections.actionDetailFragment(tvShow))
        }
    }

    private fun onSwipe(tvShow: TVShow, position: Int) = try {
        doFavoriteAction(tvShow, position)
    } catch (error: Throwable) {
        createAndShowDialog(
            requireContext(),
            getString(R.string.oops_something_went_wrong),
            getString(R.string.retry_action_favorite),
            onPositiveAction = { doFavoriteAction(tvShow, position) }
        )
    }

    private fun doFavoriteAction(tvShow: TVShow, position: Int) {
        viewModel.doFavoriteAction(tvShow)
        adapter.setItemChanged(tvShow, position)
        if(isDualPane) requireActivity().invalidateOptionsMenu()
    }
}