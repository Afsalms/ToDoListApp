package com.example.afsal.todolistapp.adaptor

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.afsal.todolistapp.R
import com.example.afsal.todolistapp.model.GroceryObject
import kotlinx.android.synthetic.main.grocery_list.view.*
import java.text.FieldPosition

class GroceryAdaptor(var itemList : ArrayList<GroceryObject>): RecyclerView.Adapter<GroceryAdaptor.ViewHolder>() {


    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val itemName = itemView.item_name
        val itemCount = itemView.item_count

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.grocery_list,parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = itemList[position].name
        holder.itemCount.text = itemList[position].count.toString()

    }




}