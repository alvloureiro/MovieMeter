package br.eng.alvloureiro.moviemeter.api

import com.alvloureiro.moviemeter.data.valueobjects.GenresResult
import com.alvloureiro.moviemeter.data.valueobjects.Movie
import com.alvloureiro.moviemeter.data.valueobjects.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface TMDBApi {
    companion object {
        const val BASE_ENDPOINT = "/3"
        const val GET_GENRE_LIST = "/genre/movie/list"
        const val GET_SEARCH_MOVIE = "/search/movie"
        const val GET_TOP_RATED = "/movie/top_rated"
    }

    @GET(BASE_ENDPOINT + GET_GENRE_LIST)
    fun genres(): Observable<GenresResult>?

    @GET(BASE_ENDPOINT + GET_SEARCH_MOVIE)
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Observable<Result>?

    @GET(BASE_ENDPOINT + GET_TOP_RATED)
    fun topRated(): Observable<Movie>
}