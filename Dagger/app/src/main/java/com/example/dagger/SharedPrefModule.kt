package com.example.dagger

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefModule(context: Context) {
    val context: Context = context

    @Singleton
    @Provides
    public fun Context(): Context{
        return context
    }

    @Singleton
    @Provides
    public fun provideSharedPreferences(context: Context): SharedPreferences{
        return androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
    }
}