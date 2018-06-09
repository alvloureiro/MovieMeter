package com.alvloureiro.moviemeter.data.valueobjects

import br.eng.alvloureiro.moviemeter.data.vos.VO
import com.google.gson.annotations.Expose


data class Genre(
        @Expose
        var id: Int? = null,

        @Expose
        var name: String? = null
): VO
