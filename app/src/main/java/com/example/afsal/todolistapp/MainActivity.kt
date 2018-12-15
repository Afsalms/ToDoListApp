package com.example.afsal.todolistapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var numberOfItem = 0


    override fun onClick(v: View?) {
        println("Clicked")
        when(v){
            increment -> {
                println("increment button clicked")
                numberOfItem ++
                textView.text = numberOfItem.toString()

            }
            decrement -> {
                println("Decrement button clicked")
                if (numberOfItem > 0){
                    numberOfItem --
                    textView.text = numberOfItem.toString()
                }
            }
            addbutton -> {
                println("Add button clicked")
                numberOfItem = 0
                editText.text = null
                textView.text = numberOfItem.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        increment.setOnClickListener(this)
        decrement.setOnClickListener(this)
        addbutton.setOnClickListener(this)




    }
}
