package itacademy.kg.rickandmortydb.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.Result

interface NetworkUtils {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<Characters>

    @GET("character")
    suspend fun getCharacter(
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("gender") gender: String
    ): Response<Characters>
    @GET("location")
    suspend fun getLocations(
        @Query("page") page: Int
    ): Response<Locations>
    @GET("location")
    suspend fun findLocations(
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String
    ): Response<Locations>
    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int
    ): Response<Episodes>
    @GET("episode")
    suspend fun findEpisodes(
        @Query("name") name: String
    ): Response<Episodes>
    companion object {
        private var retrofitService: NetworkUtils? = null
        fun getInstance(): NetworkUtils {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Utils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(NetworkUtils::class.java)
            }
            return retrofitService!!
        }
    }
}