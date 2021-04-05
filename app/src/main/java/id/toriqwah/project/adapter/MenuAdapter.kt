package id.toriqwah.project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.toriqwah.project.R
import id.toriqwah.project.helper.UtilityHelper
import id.toriqwah.project.model.Product

class MenuAdapter(context : Context, list: ArrayList<Product>, private val listener: Listener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val contexts = context
    private val itemList = list

    interface Listener{
        fun onAddClicked()
        fun onRemoveClicked(id: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val view =  LayoutInflater.from(contexts).inflate(R.layout.item_menu,parent,false)
                MenuListViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(contexts).inflate(R.layout.item_empty,parent,false)
                EmptyViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList.isEmpty())
            0
        else
            1
    }

    override fun getItemCount(): Int {
        return if (itemList.size == 0) {
            1
        } else {
            itemList.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MenuListViewHolder){
            UtilityHelper.setImage(contexts, itemList[position].img, holder.img)
            holder.name.text = itemList[position].name
            holder.price.text = "Rp. ${itemList[position].price}"
            holder.code.text = itemList[position].code
            holder.stock.text = "Stock: ${itemList[position].stock}"
            holder.remove.setOnClickListener {
                listener.onRemoveClicked(itemList[position].uid)
            }

        } else if (holder is EmptyViewHolder){
            holder.add.setOnClickListener {
                listener.onAddClicked()
            }
        }

    }

    class MenuListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val remove: ImageView = itemView.findViewById(R.id.remove)
        val img: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val code: TextView = itemView.findViewById(R.id.code)
        val stock: TextView = itemView.findViewById(R.id.stock)
    }
    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val add: Button = itemView.findViewById(R.id.add)
    }

}