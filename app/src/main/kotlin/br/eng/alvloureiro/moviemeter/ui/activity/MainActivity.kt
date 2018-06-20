package br.eng.alvloureiro.moviemeter.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.WindowManager
import br.eng.alvloureiro.moviemeter.R
import br.eng.alvloureiro.moviemeter.data.vos.Genre
import br.eng.alvloureiro.moviemeter.data.vos.Movie
import br.eng.alvloureiro.moviemeter.ext.*
import br.eng.alvloureiro.moviemeter.ui.adapter.ListViewAdapter
import br.eng.alvloureiro.moviemeter.ui.viewmodel.TMDBViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val IS_GENRES_FETCHED = "IS_GENRES_FETCHED"
        const val MOVIE_MODEL = "MODEL"
    }

    @Inject
    lateinit var mViewModel: TMDBViewModel

    private val mLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val mMovieAdapter = ListViewAdapter {
        launch<MovieDetailActivity> {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(MOVIE_MODEL, it)
        }

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private val success: (ArrayList<Movie>) -> Unit = { movies ->
        progressBar.hide()
        mMovieAdapter.setMovies(movies)
    }

    private val fail: (Throwable) -> Unit = {
        progressBar.hide()
        btnRefetch.show()
        movieTitle.text = getString(R.string.movie_title_text, it.message)
    }

    private val fetchGenresSuccess: (List<Genre>) -> Unit = { genres ->
        genres.forEach { genre ->
            app.preferences.edit().putString(genre.id?.toString(), genre.name).apply()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        app.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.show()

        mViewModel.runGetTopRatedMovies(success, fail)

        withListView<RecyclerView> {
            layoutManager = mLayoutManager
            adapter = mMovieAdapter
        }

        if (!app.preferences.contains(IS_GENRES_FETCHED)) {
            //mViewModel.movieGenres(fetchGenresSuccess)
        }

        btnRefetch?.setOnClickListener {
            progressBar.show()

            if (!app.preferences.contains(IS_GENRES_FETCHED)) {
                //mViewModel.movieGenres(fetchGenresSuccess)
            }

            mViewModel.runGetTopRatedMovies(success, fail)

            it.hide()
        }
    }

    override fun onDestroy() {
        mViewModel.cancelAllAsync()
        mViewModel.cancelAllCoroutines()
        super.onDestroy()
    }

}
