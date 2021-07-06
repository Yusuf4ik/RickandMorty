package itacademy.kg.rickandmortydb.data

import java.io.Serializable

data class ResultForLocations (

    val id: String,
    val name: String,
    val type: String,
    val dimension: String,
    val url: String
): Serializable