package itacademy.kg.rickandmortydb.main.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import itacademy.kg.rickandmortydb.Model.Repository
import itacademy.kg.rickandmortydb.data.NetworkUtils
import itacademy.kg.rickandmortydb.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DescViewModel : ViewModel() {
    val liveDataCharactersPg1 = MutableLiveData<List<Result>>()
    private val repository = Repository(NetworkUtils.getInstance())

    init {
        getCharacter()
    }





    private fun getCharacter() {

        viewModelScope.launch(Dispatchers.IO) {

//            val response = repository.getCurrentChar()
//            if (response.isSuccessful) {
//                val characters = response.body()
//                Log.i("MyTag", characters.toString())
//                withContext(Dispatchers.Main) {
//
//                    liveDataCharactersPg1.postValue(characters!!.results)
//                }
//
//            }
        }

    }
}
