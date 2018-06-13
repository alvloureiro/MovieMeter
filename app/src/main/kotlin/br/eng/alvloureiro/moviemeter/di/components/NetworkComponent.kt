package br.eng.alvloureiro.moviemeter.di.components

import br.eng.alvloureiro.moviemeter.ui.activity.MainActivity
import br.eng.alvloureiro.moviemeter.data.models.NetworkDataModel
import br.eng.alvloureiro.moviemeter.di.modules.NetworkModule
import com.google.gson.Gson
import dagger.Subcomponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Subcomponent(modules = [(NetworkModule::class)])
interface NetworkComponent {
    fun inject(mainActivity: MainActivity)

    fun retrofit(): Retrofit
    fun okhttp(): OkHttpClient
    fun gson(): Gson
    fun cache(): Cache

    fun movieDataModel(): NetworkDataModel
}