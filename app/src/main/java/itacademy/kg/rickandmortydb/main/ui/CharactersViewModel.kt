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

class CharactersViewModel: ViewModel() {
    val liveDataCharactersPg1 = MutableLiveData<List<Result>>()
    val liveDataCharactersPg2 = MutableLiveData<List<Result>>()
    private val repository = Repository(NetworkUtils.getInstance())

    init {
        getCharacter()
    }

    fun getNewCharacters(page: Int, data: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getMovieWithRate(page)

                if (response.isSuccessful) {
                    val characters = response.body()
                    withContext(Dispatchers.Main) {
                        if (data == 1) {
                            liveDataCharactersPg1.postValue(characters!!.results)
                        } else {
                            liveDataCharactersPg1.postValue(characters!!.results)
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

                val response = repository.getMovieWithRate(page=1)
                if (response.isSuccessful) {
                    val characters = response.body()
                    Log.i("MyTag", characters.toString())
                    withContext(Dispatchers.Main) {

                        liveDataCharactersPg1.postValue(characters!!.results)
                    }

                }
            }

        }
    }
