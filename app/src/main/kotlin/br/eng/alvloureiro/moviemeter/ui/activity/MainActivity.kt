package br.eng.alvloureiro.moviemeter.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import br.eng.alvloureiro.moviemeter.R
import br.eng.alvloureiro.moviemeter.data.vos.Movie
import br.eng.alvloureiro.moviemeter.ext.*
import br.eng.alvloureiro.moviemeter.ui.adapter.ListViewAdapter
import br.eng.alvloureiro.moviemeter.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val MOVIE_MODEL = "MODEL"
    }

    @Inject
    lateinit var mViewModel: MainActivityViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        app.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            .plus(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        setActionBar(mainToolbar)
        actionBar.title = getString(R.string.app_name)

        progressBar.show()

        mViewModel.runGetMoviesData(success, fail)

        withListView<RecyclerView> {
            layoutManager = mLayoutManager
            adapter = mMovieAdapter
        }

        btnRefetch?.setOnClickListener {
            progressBar.show()

            mViewModel.runGetMoviesData(success, fail)

            it.hide()
        }
    }

    override fun onDestroy() {
        mViewModel.cancelAllAsync()
        mViewModel.cancelAllCoroutines()
        super.onDestroy()
    }
}
