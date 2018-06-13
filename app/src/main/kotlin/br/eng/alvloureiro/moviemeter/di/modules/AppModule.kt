package br.eng.alvloureiro.moviemeter.di.modules

import br.eng.alvloureiro.moviemeter.Moviemeter
import br.eng.alvloureiro.moviemeter.di.scopes.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val app: Moviemeter) {
    @Provides @PerApplication
    internal fun providesApplication() = app
}