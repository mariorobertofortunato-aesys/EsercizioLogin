package com.example.eserciziologin.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eserciziologin.R
import com.example.eserciziologin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ViewModel>()
    private lateinit var searchString: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        /** RV */
        val adapter = ComuniAdapter(ComuniAdapter.OnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
        })
        binding.recyclerView.adapter = adapter

        viewModel.listaComuni.observe(viewLifecycleOwner) { listaComuni ->
            adapter.submitList(listaComuni)
            // TODO Check se funziona correttamente lo scrollTo
            binding.recyclerView.layoutManager!!.scrollToPosition(0)
        }

        /** SEARCHBAR */
        // SearchBar Listener/Watcher
        binding.searchEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                searchString = p0.toString()
                viewModel.getSearchedComune(searchString)
                binding.recyclerView.layoutManager!!.scrollToPosition(0)
            }
        })

    }


}