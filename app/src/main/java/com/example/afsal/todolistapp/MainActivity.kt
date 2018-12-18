package com.example.afsal.todolistapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.afsal.todolistapp.adaptor.GroceryAdaptor
import com.example.afsal.todolistapp.db.DataBaseHandler
import com.example.afsal.todolistapp.model.GroceryObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var numberOfItem = 0
    var dbhandler = DataBaseHandler(this)


    override fun onClick(v: View?) {
        when(v){
            increment -> {
                numberOfItem ++
                updateText()

            }
            decrement -> {
                if (numberOfItem > 0){
                    numberOfItem --
                    updateText()
                }
            }
            addbutton -> {
                if (editText.text.length >0 && numberOfItem >0){
                    var item = GroceryObject(count = numberOfItem, name = editText.text.toString())
                    var is_added = dbhandler.addItem(item)
                    if(is_added){
                        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "Not added", Toast.LENGTH_SHORT).show()

                    }
                    editText.text = null
                    numberOfItem = 0
                    updateText()
                    populateGroceryList()
                }


            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        increment.setOnClickListener(this)
        decrement.setOnClickListener(this)
        addbutton.setOnClickListener(this)
        populateGroceryList()
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                var pk = viewHolder.itemView.getTag() as Int
                dbhandler.deleteItem(pk)
            }

            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun updateText(){

        textView.text = numberOfItem.toString()
    }

    fun populateGroceryList(){

        var dbhandler = DataBaseHandler(this)
        var groceryList = dbhandler.itemList()
        var adaptor = GroceryAdaptor(groceryList)
        recyclerView.adapter = adaptor
    }

}
