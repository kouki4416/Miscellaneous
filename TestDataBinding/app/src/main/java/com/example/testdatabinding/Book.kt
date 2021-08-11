package com.example.testdatabinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField

class Book(title: String, genre: String) : BaseObservable() {
    var title = ObservableField<String>(title)
    var genre = ObservableField<String>(genre)
}