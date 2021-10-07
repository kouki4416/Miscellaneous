package com.example.dagger

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

//SharedPrefModuleとMainActivityをつなげるComponent

@Singleton
@Component(modules = [SharedPrefModule::class])
public interface MyComponent {
    fun inject(activity: MainActivity)
}