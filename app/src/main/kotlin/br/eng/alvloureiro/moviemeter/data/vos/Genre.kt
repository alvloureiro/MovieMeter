package br.eng.alvloureiro.moviemeter.data.vos

import com.google.gson.annotations.Expose


data class Genre(
        @Expose
        var id: Int? = null,

        @Expose
        var name: String? = null
): VO
