package com.example.dagger

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.dagger.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var myComponent:MyComponent
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListener()
        initializeDaggerComponent()
    }

    private fun initializeDaggerComponent(){
        myComponent = DaggerMyComponent.builder().sharedPrefModule(SharedPrefModule(this)).build()
        myComponent.inject(this)
    }

    private fun setListener(){
        binding.saveBtn.setOnClickListener{
            saveToSharedPreference()
        }
        binding.getBtn.setOnClickListener {
            retrieveFromSharedPreferences()
        }
    }

    private fun saveToSharedPreference(){
        var editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("username", binding.usernameTxt.text.toString().trim())
        editor.putString("number", binding.numberTxt.text.toString().trim())
        editor.apply()
    }

    private fun retrieveFromSharedPreferences(){
        binding.usernameTxt.setText(sharedPreferences.getString("username", "Default"))
        binding.numberTxt.setText(sharedPreferences.getString("number", "12345"))
    }

}