package br.eng.alvloureiro.moviemeter.ext

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import br.eng.alvloureiro.moviemeter.Moviemeter


val AppCompatActivity.app: Moviemeter get() = application as Moviemeter

val Context.app get() = applicationContext as Moviemeter

val Context.connectivityManager get() = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.isNetworkAvailable(): Boolean {
    val networkInfo = connectivityManager.activeNetworkInfo

    return networkInfo != null && networkInfo.isConnected
}
