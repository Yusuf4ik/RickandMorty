package itacademy.kg.rickandmortydb.main.ui

import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import itacademy.kg.rickandmortydb.Model.Repository
import itacademy.kg.rickandmortydb.R
import itacademy.kg.rickandmortydb.data.*
import itacademy.kg.rickandmortydb.databinding.DescFragmentBinding
import itacademy.kg.rickandmortydb.databinding.FragmentEpisodesBinding
import itacademy.kg.rickandmortydb.databinding.FragmentLocationsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DescFragment : Fragment(), OnMovieClickListener {
    private lateinit var adapter: Adapter


    private lateinit var viewModel: DescViewModel
    lateinit var CharPath: List<String>
    private var _binding: DescFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DescFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root     }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DescViewModel::class.java)
        val bundle = this.arguments
        try {
            val person = bundle?.getSerializable(Utils.KEY_PERSON) as Result
            binding.constraintPerson.visibility = View.VISIBLE
            binding.namePersonDesc.text = person.name
            binding.genderDesc.text = person.gender
            binding.statusDesc.text = person.status
            binding.speciesDesc.text = person.species
            binding.idDesc.text = person.id
            context?.let { Glide.with(it).load(person.image).into(binding.imageDesc) }
        } catch (e:NullPointerException){
            try {
                val location = bundle?.getSerializable(Utils.KEY_LOCATION) as ResultForLocations
                binding.constraintLocation.visibility = View.VISIBLE
                binding.nameLocationDesc.text = location.name
                binding.typeDescLoc.text = location.type
                binding.dimensionDesc.text = location.dimension
            }catch (e:NullPointerException){
                try {
                    val episode = bundle?.getSerializable(Utils.KEY_EPISODE) as ResultForEp

                    binding.constraintEpisode.visibility = View.VISIBLE
                    binding.nameEpisodeDesc.text = episode.name
                    binding.airdateDesc.text = episode.air_date
                    binding.episodesPersonDesc.text = episode.episode
                }catch (e:NullPointerException){}
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int, movie: List<Result>) {
        TODO("Not yet implemented")
    }


    }
