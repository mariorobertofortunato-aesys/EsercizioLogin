package com.example.eserciziologin.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.eserciziologin.R
import com.example.eserciziologin.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding
    private val viewModel by viewModels<ViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_filter,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // TODO funziona solo la seconda volta che si accede??
        val menuProvincieAdapter = ArrayAdapter(requireContext(),R.layout.list_item_edit_text,viewModel.listaProvince)
        binding.autoCompleteTextViewProvincia.setAdapter(menuProvincieAdapter)
        val menuRegioniAdapter = ArrayAdapter(requireContext(),R.layout.list_item_edit_text,viewModel.listaRegioni)
        binding.autoCompleteTextViewRegione.setAdapter(menuRegioniAdapter)


        // TODO a parte che non funziona, i filtri andrebbero applicati solo dopo il button press
        binding.autoCompleteTextViewProvincia.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                viewModel.getComuniFromProvincia()

                //HideKeyboard
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)

                return@OnEditorActionListener true
            }
            false
        })

        binding.autoCompleteTextViewRegione.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                viewModel.getComuniFromRegione()

                //HideKeyboard
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)

                return@OnEditorActionListener true
            }
            false
        })




    }
}