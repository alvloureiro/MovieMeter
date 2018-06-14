package br.eng.alvloureiro.moviemeter.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import br.eng.alvloureiro.moviemeter.R
import br.eng.alvloureiro.moviemeter.data.vos.Movie
import br.eng.alvloureiro.moviemeter.ext.*
import kotlinx.android.synthetic.main.activity_detail.*


class MovieDetailActivity: AppCompatActivity() {

    private val mMovie by lazy {
        getParam<Movie>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        movieSinopse.movementMethod = ScrollingMovementMethod()

        initViewFromModel()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return true
    }

    private fun initViewFromModel() {
        mMovie.poster_path?.let {
            backdropImg.loadPosterFromUrl(it)
        }
        movieTitle.text = getString(R.string.movie_title_text, mMovie.title)
        premiereDate.text = getString(R.string.movie_release_date_text, "(${mMovie.release_date?.toDisplayDate()})")
        movieSinopse.text = getString(R.string.movie_sinopse_text, mMovie.overview)
        ratingBar.rating = mMovie.vote_average?.convertVoteAverageToRating()!!
        voteAverage.text = getString(R.string.movie_vote_scored_text, mMovie.vote_average.toString())
        voteMaxTotal.text = getString(R.string.movie_vote_max_total_text, "/10")
        voteTotalCount.text = getString(R.string.movie_vote_total_count_text, mMovie.vote_count.toString())
    }
}