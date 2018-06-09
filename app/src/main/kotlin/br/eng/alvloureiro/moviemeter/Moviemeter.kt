package br.eng.alvloureiro.moviemeter

import android.app.Application
import br.eng.alvloureiro.moviemeter.di.components.AppComponent
import br.eng.alvloureiro.moviemeter.di.components.DaggerAppComponent
import br.eng.alvloureiro.moviemeter.di.modules.AppModule


class Moviemeter: Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}