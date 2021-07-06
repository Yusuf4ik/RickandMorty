package itacademy.kg.rickandmortydb.Model

import itacademy.kg.rickandmortydb.data.*
import retrofit2.Response

class Repository(private val networkUtils: NetworkUtils): RepoImp {
    override suspend fun getMovieWithRate(page: Int): Response<Characters> {
        return networkUtils.getCharacters(page)

    }
    override suspend fun findCharacter( name: String, gender: String, status: String): Response<Characters> {
        return networkUtils.getCharacter(name, status, gender)
    }


    override suspend fun getLocations(page: Int): Response<Locations> {
        return networkUtils.getLocations(page)
    }

    override suspend fun findLocations(

        name: String,
        type: String,
        dimension: String
    ): Response<Locations> {
        return networkUtils.findLocations(name, type, dimension)
    }

    override suspend fun getEpisodes(page: Int): Response<Episodes> {
        return networkUtils.getEpisodes(page)
    }

    override suspend fun findEpisodes(name: String): Response<Episodes> {
        return networkUtils.findEpisodes(name)
    }


}