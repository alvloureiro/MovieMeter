package br.eng.alvloureiro.moviemeter.ext

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import br.eng.alvloureiro.moviemeter.BuildConfig
import br.eng.alvloureiro.moviemeter.Moviemeter
import br.eng.alvloureiro.moviemeter.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


val AppCompatActivity.app: Moviemeter get() = application as Moviemeter

val Context.app get() = applicationContext as Moviemeter

val Context.connectivityManager get() = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.isNetworkAvailable(): Boolean {
    val networkInfo = connectivityManager.activeNetworkInfo

    return networkInfo != null && networkInfo.isConnected
}

fun View.show() {
    context?.let {
        startAnimation(AnimationUtils.loadAnimation(it, R.anim.fade_in))
    }

    if (visibility == View.GONE)
        visibility = View.VISIBLE
}

fun View.hide() {
    context?.let {
        startAnimation(AnimationUtils.loadAnimation(it, R.anim.fade_out))
    }

    if (visibility == View.VISIBLE)
        visibility = View.GONE
}

inline fun<reified T: AppCompatActivity> AppCompatActivity.launch(init: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

inline fun<reified T:RecyclerView> AppCompatActivity.withListView(noinline init: T.() -> Unit) {
    val listView = findViewById<T>(R.id.movieList)
    listView.init()
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)

}

fun AppCompatImageView.loadPosterFromUrl(url: String?) {
    if (url != null) {
        val posterUrl = "${BuildConfig.BASE_IMG_URL}/w154$url"
        Picasso.with(context).load(posterUrl.toUri()).into(this)
    }
}

fun AppCompatImageView.loadBackdropImageFromUrl(url: String) {
    val backImgUrl = "${BuildConfig.BASE_IMG_URL}/w780$url"
    Picasso.with(context).load(backImgUrl.toUri()).into(this)
}

fun String.toUri(): Uri {
    return Uri.parse(this)
}

fun String.toDisplayDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val releaseDate = dateFormat.parse(this)
    val displayReleaseDate = SimpleDateFormat("yyyy")
    return displayReleaseDate.format(releaseDate)
}

fun Float.convertVoteAverageToRating(): Float {
    return (5 * this) / 10
}
