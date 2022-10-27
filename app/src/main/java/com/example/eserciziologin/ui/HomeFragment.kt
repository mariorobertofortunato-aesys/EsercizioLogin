package com.example.eserciziologin.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.annotation.MenuRes
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eserciziologin.R
import com.example.eserciziologin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ViewModel>()
    private lateinit var searchString: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        /** RV */
        val adapter = ComuniAdapter(ComuniAdapter.OnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it
                )
            )
        })
        binding.recyclerView.adapter = adapter

        viewModel.listaComuni.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        /** SEARCHBAR */
        // SearchBar Listener/Watcher
        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                searchString = p0.toString()
                viewModel.getSearchedComune(searchString)
                binding.recyclerView.layoutManager!!.scrollToPosition(0)
            }
        })

        /** FAB (Hiding or showing on RV scroll) */
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && binding.searchFab.isShown) {
                    binding.searchFab.alpha = 0.5f
                    binding.searchFab.animate()
                        .alpha(1f)
                        .translationY(binding.searchFab.height + 16f)
                        .setDuration(1000)
                        .setInterpolator(AccelerateInterpolator(2f))
                        .start()
                    binding.searchFab.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.searchFab.alpha = 0.5f
                    binding.searchFab.animate()
                        .alpha(1f)
                        .translationY(-1f)
                        .setDuration(1000)
                        .setInterpolator(DecelerateInterpolator(2f))
                        .start()
                    binding.searchFab.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        /** TODO questo va controllato */
        binding.searchFab.setOnClickListener {
            binding.searchEdit.isVisible = true
            binding.searchFab.text = ""

            animateWidth(
                binding.searchFab,
                binding.searchFab.width,
                ((binding.searchFab.parent as View).width) - 24
            )
        }

        /** TOP RIGHT MENU*/
        binding.topRightHeaderImg.setOnClickListener {
            showMenu(it, R.menu.overflow_menu)
        }


    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {

        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        val itemRegioni = popup.menu.findItem(R.id.regione)
        viewModel.listaRegioniFromDB.forEachIndexed { i, regione ->
            itemRegioni.subMenu.add(Menu.NONE, i, Menu.NONE, regione)
        }
        val itemProvince = popup.menu.findItem(R.id.provincia)
        viewModel.listaProvinceFromDB.forEachIndexed { i, provincia ->
            itemProvince.subMenu.add(Menu.NONE, i, Menu.NONE, provincia)
        }

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->

            if (menuItem.title == "Filtra per: Regione") {
                popup.setOnMenuItemClickListener {
                    viewModel.regioneSelected = it.title.toString()
                    viewModel.getComuniFromRegione()
                    return@setOnMenuItemClickListener true
                }
            } else {
                popup.setOnMenuItemClickListener {
                    viewModel.provinciaSelected = it.title.toString()
                    viewModel.getComuniFromProvincia()
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener true
        }


        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }


    private fun animateWidth(view: View, startWidth: Int, endWidth: Int) {
        val widthAnimator = ValueAnimator.ofInt(startWidth, endWidth).setDuration(500)
        widthAnimator.repeatMode = ObjectAnimator.RESTART

        widthAnimator.addUpdateListener {
            val value: Int = widthAnimator.animatedValue as Int
            view.layoutParams.width = value
            view.requestLayout()
        }
        widthAnimator.start()
    }


}




