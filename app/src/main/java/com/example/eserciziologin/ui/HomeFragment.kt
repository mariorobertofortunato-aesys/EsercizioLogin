package com.example.eserciziologin.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.MenuRes
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import com.example.eserciziologin.R
import com.example.eserciziologin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ViewModel>()
    private lateinit var searchString: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        /** RV */
        val comuniadapter = ComuniAdapter(ComuniAdapter.OnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
        })



        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = comuniadapter
            itemAnimator = null
        }

        viewModel.listaComuni.observe(viewLifecycleOwner) {
            comuniadapter.submitList(it)

          lifecycleScope.launch {
              delay(5000)
              binding.recyclerView.scrollToPosition(0)
          }

        }

        /** SEARCHBAR */
        // SearchBar Listener/Watcher
        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                searchString = p0.toString()
                viewModel.getSearchedComune(searchString)
            }
        })
        // Searchbar expander
        binding.searchEdit.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    animateWidth(binding.cerca, binding.cerca.width, ((binding.cerca.parent as View).width) - 24)
                }
            }
            false
        }
        // Hiding animation
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && binding.cerca.isShown) {
                    binding.cerca.alpha = 0f
                    binding.cerca.animate().alpha(1f).setDuration(1000).start()
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.cerca.alpha = 0f
                    binding.cerca.animate().alpha(1f).setDuration(1000).start()
                }

            }
        })


        /** TOP RIGHT MENU*/
        binding.topRightHeaderImg.setOnClickListener {
            showMenu(it, R.menu.overflow_menu)
        }


    }


    /** MENU METHOD (Show & Listen) */
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

            when (menuItem.title) {
                "Filtra per: Regione" -> {
                    popup.setOnMenuItemClickListener {
                        viewModel.regioneSelected = it.title.toString()
                        viewModel.getComuniFromRegione()
                        viewModel.getProvinceFromRegione()
                        return@setOnMenuItemClickListener true
                    }
                }
                "Filtra per: Provincia" -> {
                    popup.setOnMenuItemClickListener {
                        viewModel.provinciaSelected = it.title.toString()
                        viewModel.getComuniFromProvincia()
                        return@setOnMenuItemClickListener true
                    }
                }
                // Filter Reset
                else -> {
                    viewModel.loadComuni()
                    viewModel.getProvince()

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


    /** ANIMATION */
    private fun animateWidth(view: View, startWidth: Int, endWidth: Int) {

        val widthAnimator = ValueAnimator.ofInt(startWidth, endWidth).setDuration(1250)
        widthAnimator.repeatMode = ValueAnimator.RESTART

        widthAnimator.addUpdateListener {
            val value: Int = widthAnimator.animatedValue as Int
            view.layoutParams.width = value
            view.requestLayout()
        }
        widthAnimator.start()
    }


}





