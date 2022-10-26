package com.example.eserciziologin.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.eserciziologin.R
import com.example.eserciziologin.databinding.FragmentChecklistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChecklistFragment : Fragment() {

    private lateinit var binding: FragmentChecklistBinding
    private val viewModel by viewModels<ViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_checklist,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getProvince()
        viewModel.getRegioni()

/*        val adapter = ComuniAdapter(ComuniAdapter.OnClickListener {
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToDetailFragment(it))
        })
        binding.recyclerViewFilter.adapter = adapter*/

        /** OBSERVERS */
        viewModel.listaProvince.observe(viewLifecycleOwner) {
            val menuProvinceAdapter = ArrayAdapter(requireContext(),R.layout.list_item_edit_text,it)
            binding.autoCompleteTextViewProvincia.setAdapter(menuProvinceAdapter)
        }

        viewModel.listaRegioni.observe(viewLifecycleOwner) {
            val menuRegioniAdapter = ArrayAdapter(requireContext(),R.layout.list_item_edit_text,it)
            binding.autoCompleteTextViewRegione.setAdapter(menuRegioniAdapter)
        }

/*        viewModel.listaComuni.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }*/


        /** LISTENERS */

        // Regione
        binding.autoCompleteTextViewRegione.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                viewModel.getComuniFromRegione()
                viewModel.getProvinceFromRegione()

                //HideKeyboard
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)

                return@OnEditorActionListener true
            }
            false
        })

        binding.autoCompleteTextViewRegione.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ ->
                viewModel.getComuniFromRegione()
                viewModel.getProvinceFromRegione() }

        // Provincia
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

        binding.autoCompleteTextViewProvincia.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ -> viewModel.getComuniFromProvincia() }

    }
}