package br.eng.alvloureiro.moviemeter.ui.viewmodel

import android.util.Log
import br.eng.alvloureiro.moviemeter.data.models.TMDBModel
import br.eng.alvloureiro.moviemeter.data.vos.Genre
import br.eng.alvloureiro.moviemeter.data.vos.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class TMDBViewModel @Inject constructor(private val model: TMDBModel) {

    companion object {
        const val TAG = "TMDBViewModel"
    }

    private val mDisposable = CompositeDisposable()

    fun topRatedMovies(success: (results: ArrayList<Movie>) -> Unit, fail: (Throwable) -> Unit) {
        mDisposable.addAll(
                model.topRated()
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribeOn(Schedulers.io())
                        ?.subscribe(
                                {
                                    tops ->
                                        Log.d(TAG, tops?.total_results?.toString())
                                        tops?.results?.let {
                                            success(it)
                                        }

                                },
                                {
                                    error -> fail(error)
                                }
                        )
        )
    }

    fun movieGenres(success: (List<Genre>) -> Unit) {
        model.genres()
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe { genres -> genres?.genres?.let { success(it) } }
    }

    fun clearDisposables() {
        mDisposable.clear()
    }
}