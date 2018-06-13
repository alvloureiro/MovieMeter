package br.eng.alvloureiro.moviemeter

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import br.eng.alvloureiro.moviemeter.di.components.AppComponent
import br.eng.alvloureiro.moviemeter.di.components.DaggerAppComponent
import br.eng.alvloureiro.moviemeter.di.modules.AppModule
import br.eng.alvloureiro.moviemeter.di.modules.NetworkModule


class Moviemeter: Application() {

    private companion object {
        const val PREF_FILE_NAME = "eng.br.alvloureiro.moviemeter.preferences"
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .networkModule(NetworkModule(this))
                .appModule(AppModule(this))
                .build()
    }

    val preferences: SharedPreferences by lazy {
        baseContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}