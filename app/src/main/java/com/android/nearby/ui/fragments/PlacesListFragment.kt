package com.adyen.android.assignment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adyen.android.assignment.data.APIResult
import com.adyen.android.assignment.databinding.FragmentPlacesListBinding
import com.adyen.android.assignment.ui.adapters.PlacesListAdapter
import com.adyen.android.assignment.ui.viewmodel.MainViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * Fragment for showing list of places and
 * error text if something fails
 */
class PlacesListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mBinding: FragmentPlacesListBinding

    private lateinit var venueListAdapter: PlacesListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        mainViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding =
            FragmentPlacesListBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        initLoaderObserver()
        initPlacesObserver()
    }


    private fun initPlacesObserver() {
        mainViewModel.placesLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is APIResult.Success -> {
                    hideProgressBar()
                    hideErrorText()
                    venueListAdapter.differ.submitList(response.data)
                }
                is APIResult.Error -> {
                    mBinding.errorText.visibility = View.VISIBLE
                    mBinding.errorText.text = response.exception
                }
            }
        }
    }

    private fun initLoaderObserver() {
        mainViewModel.loaderLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                true -> {
                    hideErrorText()
                    showProgressBar()
                }
                false -> hideProgressBar()
            }
        }
    }


    private fun hideErrorText() {
        mBinding.errorText.visibility = View.GONE
    }

    private fun showProgressBar() {
        mBinding.listProgressBar.visibility = View.VISIBLE

    }

    private fun hideProgressBar() {
        mBinding.listProgressBar.visibility = View.GONE
    }

    private fun setUpRecyclerView() {
        venueListAdapter = PlacesListAdapter()
        mBinding.rvVenuesList.apply {
            adapter = venueListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}