package com.example.testdatabinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SimpleModel : BaseObservable() {
    @get:Bindable
    var number: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.number)// BRはcompile時に生成される
            notifyPropertyChanged(BR.numberAsStr)
        }

    // Xml内で呼び出す時はgetが外れる
    @Bindable
    fun getNumberAsStr(): String{
        return number.toString()
    }

    fun incrementOnTap() {
        this.number += 1
    }
}