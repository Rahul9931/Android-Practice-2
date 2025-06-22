package com.example.android_practice_2.Dynamic_View_Practice.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.android_practice_2.R

class DynamicActivity : AppCompatActivity() {

    private val spinnerData = listOf("a", "b", "c", "d")
    private var isFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)

        val btnAddSpinner = findViewById<Button>(R.id.btnAddSpinner)
        val spinnerContainer = findViewById<LinearLayout>(R.id.spinnerContainer)
        val middleBtn = findViewById<Button>(R.id.middleButton)

        btnAddSpinner.setOnClickListener {
            val spinner = Spinner(this)

            // Set up adapter
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerData)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            // Set default value to "a"
            spinner.setSelection(spinnerData.indexOf("a"))

            // Listener for selection
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: android.view.View,
                    position: Int,
                    id: Long
                ) {
                    if (!isFirstTime){
                        val selectedItem = spinnerData[position]
                        Log.d("SpinnerSelected", "Selected item: $selectedItem")
                    }
                    isFirstTime = false
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            // Add spinner to container
            spinnerContainer.addView(spinner)
            isFirstTime = true
        }

        middleBtn.setOnClickListener {
            spinnerContainer.removeAllViews()
        }
    }
}
