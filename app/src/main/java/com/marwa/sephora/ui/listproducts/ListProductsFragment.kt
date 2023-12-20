package com.marwa.sephora.ui.listproducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.marwa.sephora.databinding.FragmentListProductsBinding
import com.marwa.sephora.ui.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListProductsFragment : Fragment() {
    @Inject
    lateinit var productListAdapter: ProductSectionAdapter

    private var _binding: FragmentListProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListProducts(isCacheNotEnabled = false)
        initRecyclerView()
        initActions()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is ResultState.Error -> {
                            handleError()
                        }

                        ResultState.Loading -> {
                            handleLoading()

                        }

                        is ResultState.Success -> {
                            handleSuccess(it)
                        }
                    }
                }

            }
        }
    }

    private fun handleSuccess(it: ResultState.Success) {
        binding.progressBarLoadingSearchResults.visibility = View.GONE
        productListAdapter.submitList(it.products)
    }

    private fun handleLoading() {
        with(binding) {
            errorContainer.container.visibility = View.GONE
            container.visibility = View.VISIBLE
            progressBarLoadingSearchResults.visibility = View.VISIBLE
        }
    }

    private fun handleError() {
        with(binding) {
            progressBarLoadingSearchResults.visibility = View.GONE
            errorContainer.container.visibility = View.VISIBLE
            errorContainer.retryBtn.setOnClickListener {
                viewModel.getListProducts()
            }
        }

    }

    private fun initActions() {
        binding.searchButton.setOnClickListener {
            val searchText = binding.editTextSearch.text.toString()
            if (searchText.isNotEmpty()) {
                viewModel.filterListProduct(searchText)
            }
        }
        binding.icUp.setOnClickListener {
            viewModel.orderRating(isUp = true)
        }
        binding.icDown.setOnClickListener {
            viewModel.orderRating(isUp = false)
        }
        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrBlank()) {
                viewModel.getListProducts(isCacheNotEnabled = false)
            }
        }

    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productListAdapter
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ListProductsFragment()
    }
}