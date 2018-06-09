package br.eng.alvloureiro.moviemeter.di.components

import br.eng.alvloureiro.moviemeter.Moviemeter
import br.eng.alvloureiro.moviemeter.di.modules.AppModule
import br.eng.alvloureiro.moviemeter.di.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(app: Moviemeter)
    fun plus(module: NetworkModule): NetworkComponent
}

