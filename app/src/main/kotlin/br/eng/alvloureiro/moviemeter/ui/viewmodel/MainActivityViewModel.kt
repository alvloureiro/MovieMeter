package br.eng.alvloureiro.moviemeter.ui.viewmodel

import android.util.Log
import br.eng.alvloureiro.moviemeter.data.models.NetworkDataModel
import br.eng.alvloureiro.moviemeter.data.vos.Movie
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val model: NetworkDataModel): BaseViewModel() {

    companion object {
        const val TAG = "MainActivityViewModel"
    }

    fun runGetMoviesData(success: (ArrayList<Movie>) -> Unit, fail: (Throwable) -> Unit) {
        Log.d(TAG, "runGetMoviesData")
        executeOnUITryCatch(
                {
                    val genres = asyncAwait {
                        model.genres()?.genres
                    }
                    val movies = asyncAwait {
                        model.topRated()?.results!!
                    }

                    movies.map {
                        movie -> movie.genre_ids?.map {
                            id -> genres
                                ?.filter { genre -> genre.id == id }
                                ?.map {
                                    genre ->
                                        Log.d(TAG, "add ${genre.name}")
                                        movie.genres?.add(genre)
                            }
                        }
                    }

                    success(movies)
                },
                {
                    fail(it)
                }
        )
    }
}
