package com.example.afsal.todolistapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
        println("Clicked")
        when(v){
            increment -> {
                println("increment button clicked")
                numberOfItem ++
                updateText()

            }
            decrement -> {
                println("Decrement button clicked")
                if (numberOfItem > 0){
                    numberOfItem --
                    updateText()
                }
            }
            addbutton -> {
                println("Add button clicked")

                if (editText.text.length >0 && numberOfItem >0){


                    var item = GroceryObject()
                    item.count = numberOfItem
                    item.name = editText.text.toString()
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
//                    var userlist = dbhandler.itemList()
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
    }

    fun updateText(){
        textView.text = numberOfItem.toString()
    }

    fun populateGroceryList(){
        println("****************************************")

        var groceryList = dbhandler.itemList()
        groceryList.forEach { println(it.name) }
        var adaptor = GroceryAdaptor(groceryList)
        recyclerView.adapter = adaptor
        println("---------------------------------------------")
    }
}
