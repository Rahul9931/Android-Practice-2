package com.example.android_practice_2.di.dagger2.mvvm.activity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.android_practice_2.R
import com.example.android_practice_2.di.dagger2.mvvm.db.FakerDB
import com.example.android_practice_2.di.dagger2.mvvm.viewmodels.MainViewModel
import com.example.android_practice_2.di.dagger2.mvvm.viewmodels.MainViewModelFactory
import javax.inject.Inject

class Daggar2Practice2Activity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var fakerDB1 : FakerDB

    @Inject
    lateinit var fakerDB2 : FakerDB

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val products: TextView
        get() = findViewById(R.id.txt_product)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daggar2_practice2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        (application as FakerApplication).applicationComponent.inject(this)

        mainViewModel = ViewModelProvider(this,mainViewModelFactory).get(MainViewModel::class.java)

        mainViewModel.productsLiveData.observe(this,{
            products.text = it.joinToString { x-> x.title + "\n\n" }
        })
    }
}