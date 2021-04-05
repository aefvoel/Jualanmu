package id.toriqwah.project.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.toriqwah.project.model.Product
import id.toriqwah.project.util.SingleLiveEvent
import id.toriqwah.project.view.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel(){


    private val database = FirebaseDatabase.getInstance()
    val pushSuccess = SingleLiveEvent<Unit>()
    val listMenu = MutableLiveData<ArrayList<Product>>()

    fun getMenu(child: String) {
        isLoading.value = true
        viewModelScope.launch {
            val menuListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    isLoading.value = false
                    val data = arrayListOf<Product>()
                    for (list in dataSnapshot.children){
                        data.add(list.getValue(Product::class.java)!!)
                        Log.d("list", list.toString())
                    }
                    listMenu.value = data
                }


                override fun onCancelled(error: DatabaseError) {
                    isLoading.value = false
                }

            }
            database.reference.child(child).addListenerForSingleValueEvent(menuListener)
        }
    }

    fun pushMenu(product: Product){
        isLoading.value = true
        viewModelScope.launch {
            val key = database.reference.child("product").push().key
            if (key != null) {
                product.uid = key
                database.reference.child("product").child(key).setValue(product)
                    .addOnSuccessListener {
                        isLoading.value = false
                        pushSuccess.call()
                    }
                    .addOnFailureListener {
                        isLoading.value = false
                    }
            }

        }
    }
    fun removeProduct(uid: String?){
        isLoading.value = true
        viewModelScope.launch {
            if (uid != null) {
                database.reference.child("product").child(uid).removeValue()
                    .addOnSuccessListener {
                        isLoading.value = false
                        pushSuccess.call()
                    }
                    .addOnFailureListener {
                        isLoading.value = false
                    }
            }
        }
    }
}