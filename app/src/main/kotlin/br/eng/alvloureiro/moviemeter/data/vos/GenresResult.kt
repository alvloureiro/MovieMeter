package br.eng.alvloureiro.moviemeter.data.vos

import com.google.gson.annotations.Expose


data class GenresResult(
        @Expose
        var genres: List<Genre>? = null
): VO
