package com.alvloureiro.moviemeter.data.valueobjects

import br.eng.alvloureiro.moviemeter.data.vos.VO
import com.google.gson.annotations.Expose


data class GenresResult(
        @Expose
        var genres: List<Genre>? = null
): VO
