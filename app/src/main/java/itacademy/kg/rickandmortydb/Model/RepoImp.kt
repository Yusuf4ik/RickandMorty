package itacademy.kg.rickandmortydb.Model

import itacademy.kg.rickandmortydb.data.Characters
import itacademy.kg.rickandmortydb.data.Episodes
import itacademy.kg.rickandmortydb.data.Locations
import itacademy.kg.rickandmortydb.data.Result
import retrofit2.Response
import retrofit2.http.Query

interface RepoImp {
    suspend fun getMovieWithRate(page:Int): Response<Characters>
    suspend fun findCharacter( name: String, gender: String, status: String): Response<Characters>
    suspend fun getLocations(page:Int): Response<Locations>
    suspend fun findLocations( name: String, type: String, dimension: String): Response<Locations>
    suspend fun getEpisodes(page:Int): Response<Episodes>
    suspend fun findEpisodes(name: String): Response<Episodes>
}