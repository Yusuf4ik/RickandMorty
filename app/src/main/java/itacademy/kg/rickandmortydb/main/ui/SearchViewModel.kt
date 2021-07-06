package itacademy.kg.rickandmortydb.main.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import itacademy.kg.rickandmortydb.Model.Repository
import itacademy.kg.rickandmortydb.data.NetworkUtils
import itacademy.kg.rickandmortydb.data.Result
import itacademy.kg.rickandmortydb.data.ResultForEp
import itacademy.kg.rickandmortydb.data.ResultForLocations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchViewModel: ViewModel() {
    val liveDataFindChar= MutableLiveData<List<Result>>()
    val liveDataFindLoc= MutableLiveData<List<ResultForLocations>>()
    val liveDataFindEp= MutableLiveData<List<ResultForEp>>()
    private val repository = Repository(NetworkUtils.getInstance())



     fun findCharacter( name:String, gender:String, status: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.findCharacter(name,gender, status)
            if (response.isSuccessful) {
                val character = response.body()
                Log.i("MySwag", character.toString())
                withContext(Dispatchers.Main) {

                    liveDataFindChar.postValue(character!!.results)
                }

            }
        }

    }

    fun findLocations( name:String, type:String, dimension: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.findLocations(name,type, dimension)
            if (response.isSuccessful) {
                val character = response.body()
                Log.i("MySwag", character.toString())
                withContext(Dispatchers.Main) {

                    liveDataFindLoc.postValue(character!!.results)
                }

            }
        }

    }
    fun findEp( name:String) {

        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.findEpisodes(name)
            if (response.isSuccessful) {
                val character = response.body()
                Log.i("MySwag", character.toString())
                withContext(Dispatchers.Main) {

                    liveDataFindEp.postValue(character!!.results)
                }

            }
        }

    }






}
