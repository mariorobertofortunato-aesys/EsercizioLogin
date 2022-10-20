package com.example.eserciziologin.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eserciziologin.R
import com.example.eserciziologin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ViewModel>()
    private  var comune: String ?= ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ComuniAdapter(ComuniAdapter.OnClickListener {
            //TODO findNavController().navigate()
        })
        binding.recyclerView.adapter = adapter
        viewModel.listaComuni.observe(viewLifecycleOwner) { listaComuni ->
            adapter.submitList(listaComuni)
        }

        // TODO:click search button
        viewModel.getComuneFromDB(comune?: "")

        binding.searchEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                comune = p0.toString()


            }

        })

        return binding.root
    }


}