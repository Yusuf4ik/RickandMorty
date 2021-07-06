package itacademy.kg.rickandmortydb.main.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import itacademy.kg.rickandmortydb.Model.Repository
import itacademy.kg.rickandmortydb.data.NetworkUtils
import itacademy.kg.rickandmortydb.data.ResultForEp
import itacademy.kg.rickandmortydb.data.ResultForLocations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class EpViewModel: ViewModel() {
    val liveDatEp= MutableLiveData<List<ResultForEp>>()
    private val repository = Repository(NetworkUtils.getInstance())

    init {
        getEpisode()
    }

    fun getNewCharacters(page: Int, data: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getEpisodes(page)

                if (response.isSuccessful) {
                    val episodes = response.body()
                    withContext(Dispatchers.Main) {
                        if (data == 1) {
                            liveDatEp.postValue(episodes!!.results)
                        } else {
                            liveDatEp.postValue(episodes!!.results)
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.i("MYTAG", e.message().toString())
            } catch (t: Throwable) {
                Log.i("MYTAG", t.message.toString())
            }
        }

    }



    private fun getEpisode() {

        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.getEpisodes(page=1)
            if (response.isSuccessful) {
                val episodes = response.body()
                Log.i("MyTag", episodes.toString())
                withContext(Dispatchers.Main) {

                    liveDatEp.postValue(episodes!!.results)
                }

            }
        }

    }
}
