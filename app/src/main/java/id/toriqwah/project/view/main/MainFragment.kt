package id.toriqwah.project.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.toriqwah.project.R
import id.toriqwah.project.adapter.MenuAdapter
import id.toriqwah.project.databinding.FragmentHomeBinding
import id.toriqwah.project.helper.UtilityHelper
import id.toriqwah.project.model.Product
import id.toriqwah.project.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view_parent
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject

class MainFragment: BaseFragment(), MenuAdapter.Listener{

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by inject<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        with(viewModel){
            snackbarMessage.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Int -> UtilityHelper.snackbarLong(view_parent, getString(it))
                    is String -> UtilityHelper.snackbarLong(view_parent, it)
                }
            })
            networkError.observe(viewLifecycleOwner, Observer {
                UtilityHelper.snackbarLong(view_parent, getString(R.string.error_network))
            })
            isLoading.observe(viewLifecycleOwner, Observer { bool ->
                bool.let { loading ->
                    if(loading){ showWaitingDialog() }
                    else { hideWaitingDialog() }
                }
            })
            listMenu.observe(viewLifecycleOwner, Observer {
                setListMenu(it)
            })
            pushSuccess.observe(viewLifecycleOwner, Observer {
                viewModel.getMenu("product")
            })
        }
        setView()
    }

    private fun setView(){
        viewModel.getMenu("product")
        setToolbar("Gudang")
        toolbar_back.visibility = View.GONE
        toolbar_add.setOnClickListener {
            addFragment(AddFragment.newInstance())
        }
    }

    private fun setListMenu(list: ArrayList<Product>) {
        Log.d("liatmenu", list.toString())
        rv_menu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MenuAdapter(context, list, this@MainFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onAddClicked() {
        addFragment(AddFragment.newInstance())
    }

    override fun onRemoveClicked(id: String?) {
        viewModel.removeProduct(id)
    }
}