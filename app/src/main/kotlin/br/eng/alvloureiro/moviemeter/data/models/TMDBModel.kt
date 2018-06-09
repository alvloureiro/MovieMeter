package br.eng.alvloureiro.moviemeter.data.models

import br.eng.alvloureiro.moviemeter.api.TMDBApi
import br.eng.alvloureiro.moviemeter.data.vos.GenresResult
import br.eng.alvloureiro.moviemeter.data.vos.Result
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TMDBModel @Inject constructor(private val mApi: TMDBApi){

    fun genres(): Observable<GenresResult>? {
        return mApi.genres()
    }


    fun searchMovie(movieTitle: String, page: Int): Observable<Result>? =
            mApi.searchMovie(movieTitle, page)

    fun topRated() : Observable<Result>? = mApi.topRated()
}