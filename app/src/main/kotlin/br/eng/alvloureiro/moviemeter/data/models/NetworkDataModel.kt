package br.eng.alvloureiro.moviemeter.data.models

import br.eng.alvloureiro.moviemeter.api.Api
import br.eng.alvloureiro.moviemeter.di.scopes.PerApplication
import javax.inject.Inject
import javax.inject.Singleton

@PerApplication
class NetworkDataModel @Inject constructor(private val mApi: Api){

    fun genres() = mApi.genres()

    fun searchMovie(movieTitle: String, page: Int) = mApi.searchMovie(movieTitle, page)

    fun topRated() = mApi.topRated()
}