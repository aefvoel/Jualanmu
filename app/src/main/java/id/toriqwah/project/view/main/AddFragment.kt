package id.toriqwah.project.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import id.toriqwah.project.R
import id.toriqwah.project.databinding.FragmentAddBinding
import id.toriqwah.project.helper.UtilityHelper
import id.toriqwah.project.model.Product
import id.toriqwah.project.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_home.view_parent
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject

class AddFragment: BaseFragment(){

    private lateinit var binding: FragmentAddBinding
    private val viewModel by inject<MainViewModel>()
    private var product = Product()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
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
            pushSuccess.observe(viewLifecycleOwner, Observer {
                activity!!.supportFragmentManager.popBackStack()
            })
        }
        setView()
    }

    private fun setView(){
        setToolbar("Tambah Produk")
        setNavigation()
        toolbar_add.setOnClickListener {
            proceed()
        }
    }

    private fun proceed(){
        product.name = name.text.toString()
        product.category = category.text.toString()
        product.code = code.text.toString()
        product.cost = cost.text.toString().toLong()
        product.margin = margin.text.toString()
        product.price = price.text.toString().toLong()
        product.stock = stock.text.toString().toLong()
        product.min_stock = min_stock.text.toString().toLong()


        viewModel.pushMenu(product)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddFragment()
    }
}