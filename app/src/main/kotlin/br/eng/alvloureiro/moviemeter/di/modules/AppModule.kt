package br.eng.alvloureiro.moviemeter.di.modules

import br.eng.alvloureiro.moviemeter.Moviemeter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app: Moviemeter) {
    @Provides @Singleton
    fun providesApplication() = app
}