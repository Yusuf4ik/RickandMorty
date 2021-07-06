package itacademy.kg.rickandmortydb.main.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import itacademy.kg.rickandmortydb.Model.Repository
import itacademy.kg.rickandmortydb.data.NetworkUtils
import itacademy.kg.rickandmortydb.data.Result
import itacademy.kg.rickandmortydb.data.ResultForLocations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LocationViewModel:ViewModel() {
    val liveDatLocations= MutableLiveData<List<ResultForLocations>>()
    private val repository = Repository(NetworkUtils.getInstance())

    init {
        getCharacter()
    }

    fun getNewCharacters(page: Int, data: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getLocations(page)

                if (response.isSuccessful) {
                    val locations = response.body()
                    withContext(Dispatchers.Main) {
                        if (data == 1) {
                            liveDatLocations.postValue(locations!!.results)
                        } else {
                            liveDatLocations.postValue(locations!!.results)
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



    private fun getCharacter() {

        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.getLocations(page=1)
            if (response.isSuccessful) {
                val locations = response.body()
                Log.i("MyTag", locations.toString())
                withContext(Dispatchers.Main) {

                    liveDatLocations.postValue(locations!!.results)
                }

            }
        }

    }
}
