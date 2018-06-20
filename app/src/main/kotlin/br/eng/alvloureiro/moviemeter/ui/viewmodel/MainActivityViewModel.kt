package br.eng.alvloureiro.moviemeter.ui.viewmodel

import android.util.Log
import br.eng.alvloureiro.moviemeter.data.models.NetworkDataModel
import br.eng.alvloureiro.moviemeter.data.vos.Movie
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val model: NetworkDataModel): BaseViewModel() {

    companion object {
        const val TAG = "MainActivityViewModel"
    }

    fun runGetTopRatedMovies(success: (ArrayList<Movie>) -> Unit, fail: (Throwable) -> Unit) {
        Log.d(TAG, "runGetTopRatedMovies")
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