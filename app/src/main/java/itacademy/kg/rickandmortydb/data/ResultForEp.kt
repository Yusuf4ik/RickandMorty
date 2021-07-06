package itacademy.kg.rickandmortydb.data

import java.io.Serializable

data class ResultForEp(

    val id: String,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>
): Serializable