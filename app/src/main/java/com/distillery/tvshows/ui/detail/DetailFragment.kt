package com.distillery.tvshows.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.distillery.tvshows.R
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.databinding.FragmentDetailBinding
import com.distillery.tvshows.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * TV Show detail screen
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel by viewModels<DetailViewModel>()

    private val supportActionBar by lazy { (requireActivity() as AppCompatActivity).supportActionBar }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Set up listeners
     * Set up viewmodel observers
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initUi()
        initObservers()
        viewModel.loadDetail(args.tvShowDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_toolbar_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        lifecycleScope.launch(Dispatchers.Main) {
            val isFavoriteTVShow = viewModel.isFavoriteTVShow(viewModel.tvShowDetail.value!!.id)
            menu.findItem(R.id.action_add).isVisible = !isFavoriteTVShow
            menu.findItem(R.id.action_remove).isVisible = isFavoriteTVShow
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_add -> {
                viewModel.addFavorite(viewModel.tvShowDetail.value!!)
                Toast.makeText(requireContext(), getString(R.string.sucess_message),Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_remove -> {
                viewModel.removeFavorite(viewModel.tvShowDetail.value!!)
                Toast.makeText(requireContext(), getString(R.string.sucess_message),Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        with(binding) {
            tvShowIMDB.setOnClickListener { openBrowser() }
        }
    }

    private fun initObservers() {
        with(viewModel) {
            tvShowDetail.observe(viewLifecycleOwner) {
                it?.let {
                    supportActionBar?.title = it.name
                    Glide.with(requireContext()).load(it.image).into(binding.tvShowImage)
                    binding.tvShowRating.rating = it.rating
                    binding.tvShowSummary.setHtmlText(it.summary)
                    binding.tvShowType.setBoldTitle(getString(R.string.show_type), it.type)
                    binding.tvShowLanguage.setBoldTitle(getString(R.string.language), it.language)
                    binding.tvShowGenres.setBoldTitle(getString(R.string.genres), it.genres.joinToString())
                    binding.tvShowPremiered.setBoldTitle(getString(R.string.premiered), it.premiered)
                    binding.tvShowEnded.setBoldTitle(getString(R.string.ended), it.ended)
                    binding.tvShowSite.setBoldTitle(getString(R.string.official_site), it.officialSite)
                    binding.tvShowNetwork.setBoldTitle(getString(R.string.network), it.network)
                    binding.tvShowIMDB.setBoldTitle(getString(R.string.imdb), it.imdb)
                    binding.tvShowIMDB.setVisibility(!it.imdb.isEmpty())
                }
            }
        }
    }

    private fun openBrowser() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.imdb_url, viewModel.tvShowDetail.value!!.imdb))))
    }
}