package com.mirror83.advicegenerator

import android.app.Application
import com.mirror83.advicegenerator.data.AppContainer
import com.mirror83.advicegenerator.data.DefaultContainer

class AdviceGeneratorApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer()
    }
}