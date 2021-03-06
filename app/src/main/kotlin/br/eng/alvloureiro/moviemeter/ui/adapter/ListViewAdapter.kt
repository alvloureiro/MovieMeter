package br.eng.alvloureiro.moviemeter.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import br.eng.alvloureiro.moviemeter.R
import br.eng.alvloureiro.moviemeter.data.vos.Movie
import br.eng.alvloureiro.moviemeter.ext.convertVoteAverageToRating
import br.eng.alvloureiro.moviemeter.ext.inflate
import br.eng.alvloureiro.moviemeter.ext.loadPosterFromUrl
import br.eng.alvloureiro.moviemeter.ext.toDisplayDate
import kotlinx.android.synthetic.main.carditem.view.*


class ListViewAdapter: RecyclerView.Adapter<ListViewAdapter.MViewHolder> {

    private var mMovies: ArrayList<Movie>? = arrayListOf()
    private val mListener: (Movie) -> Unit

    constructor(listener: (Movie) -> Unit) : super() {
        mListener = listener
    }

    fun setMovies(movies: ArrayList<Movie>) {
        mMovies = movies
        notifyItemRangeChanged(0, mMovies?.size!!)
    }

    class MViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Movie, listener: (Movie) -> Unit) = with(itemView) {
            moviePoster.loadPosterFromUrl(url = item.poster_path)

            movieName.text = context?.getString(R.string.movie_title_text, item.title)

            movieReleaseDate.text = context?.getString(R.string.movie_release_date_text, "(${item.release_date?.toDisplayDate()})")

            ratingBar.rating = item.vote_average?.convertVoteAverageToRating()!!

            voteScore.text = context?.getString(R.string.movie_vote_scored_text, item.vote_average)

            if (item.genres?.isNotEmpty() as Boolean) {
                movieGenres.text = context?.getString(
                        R.string.label_movie_genres_text,
                        item.genres?.map { genre -> " ${genre.name} " }
                )
            } else {
                movieGenres.text = context.getString(R.string.label_movie_genres_text, "")
            }

            setOnClickListener{
                listener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder(parent?.inflate(R.layout.carditem)!!)
    }

    override fun getItemCount(): Int = mMovies?.size!!

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(mMovies?.get(position)!!, mListener)
    }
}
