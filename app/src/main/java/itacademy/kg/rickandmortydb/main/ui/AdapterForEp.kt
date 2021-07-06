package itacademy.kg.rickandmortydb.main.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import itacademy.kg.rickandmortydb.data.ResultForEp
import itacademy.kg.rickandmortydb.databinding.ItemEpisodesBinding

class AdapterForEp (
    private val context: Context,
    private val EpList: List<ResultForEp>,
    val listener: OnEpListener
) : RecyclerView.Adapter<AdapterForEp.EpHolder>() {

    inner class EpHolder(val binding2: ItemEpisodesBinding) : RecyclerView.ViewHolder(binding2.root) {
        init {
            itemView.setOnClickListener{

                listener.onDescClick(adapterPosition, EpList)


            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpHolder {
        val binding2 = ItemEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpHolder(binding2)
    }


    interface OnEpListener {
        fun onDescClick(position: Int, movie: List<ResultForEp>)
    }

    override fun onBindViewHolder(holder: EpHolder, position: Int) {
        with(holder) {
            //forCharacters
            binding2.apply {
                binding2.nameOfChar.text = EpList[position].name
                binding2.episode.text = EpList[position].episode
                binding2.idOfChar.text = EpList[position].id
                binding2.airDate.text = EpList[position].air_date

            }




        }    }

    override fun getItemCount(): Int {
        return  EpList.size
    }
}