package br.eng.alvloureiro.moviemeter.ui.viewmodel

import br.eng.alvloureiro.moviemeter.data.models.NetworkDataModel
import br.eng.alvloureiro.moviemeter.data.vos.Movie
import javax.inject.Inject


class TMDBViewModel @Inject constructor(private val model: NetworkDataModel): BaseViewModel() {

    companion object {
        const val TAG = "TMDBViewModel"
    }

    fun runGetTopRatedMovies(success: (ArrayList<Movie>) -> Unit, fail: (Throwable) -> Unit) {
        executeOnUITryCatch(
                {
                    val movies = asyncAwait {
                        model.topRated()?.results!!
                    }
                    success(movies)
                },{
                    fail(it)
                })
    }
}