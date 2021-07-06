package itacademy.kg.rickandmortydb.main.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import itacademy.kg.rickandmortydb.data.Result
import itacademy.kg.rickandmortydb.data.ResultForLocations
import itacademy.kg.rickandmortydb.data.Utils
import itacademy.kg.rickandmortydb.databinding.ItemCharactersBinding

class Adapter(
    private val context: Context,
    private val CharList: List<Result>,
    val listener: OnMovieClickListener
) : RecyclerView.Adapter<Adapter.MovieHolder>() {

    inner class MovieHolder(val binding: ItemCharactersBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition, CharList)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ItemCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        with(holder) {
            //forCharacters
            binding.apply {
                Glide.with(context).load( CharList[position].image)
                    .into(binding.imageOfChar)
                binding.nameOfChar.text = CharList[position].name
                binding.idOfChar.text = CharList[position].id
                binding.speciesOfChar.text = CharList[position].species
                binding.genderOfChar.text = CharList[position].gender
                binding.statusOfChar.text = CharList[position].status


            }




        }

    }

    override fun getItemCount(): Int {
        return CharList.size
    }
}


interface OnMovieClickListener {
    fun onItemClick(position: Int, movie: List<Result>)
}