package itacademy.kg.rickandmortydb.data

import java.io.Serializable

data class Result (

    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val url: String
        ): Serializable