package br.eng.alvloureiro.moviemeter.di.components

import br.eng.alvloureiro.moviemeter.Moviemeter
import br.eng.alvloureiro.moviemeter.data.models.NetworkDataModel
import br.eng.alvloureiro.moviemeter.di.modules.AppModule
import br.eng.alvloureiro.moviemeter.di.modules.NetworkModule
import br.eng.alvloureiro.moviemeter.di.scopes.PerApplication
import br.eng.alvloureiro.moviemeter.ui.activity.MainActivity
import com.google.gson.Gson
import dagger.Component
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit


@PerApplication
@Component(modules = [(AppModule::class), (NetworkModule::class)])
interface AppComponent {
    fun inject(app: Moviemeter)
    fun inject(mainActivity: MainActivity)

    fun retrofit(): Retrofit
    fun okhttp(): OkHttpClient
    fun gson(): Gson
    fun cache(): Cache

    fun movieDataModel(): NetworkDataModel
}

