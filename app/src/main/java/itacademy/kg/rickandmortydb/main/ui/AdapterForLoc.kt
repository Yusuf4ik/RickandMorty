package itacademy.kg.rickandmortydb.main.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import itacademy.kg.rickandmortydb.data.Result
import itacademy.kg.rickandmortydb.data.ResultForLocations
import itacademy.kg.rickandmortydb.databinding.ItemLocationBinding

    class AdapterForLoc(
        private val context: Context,
        private val LocList: List<ResultForLocations>,
        val listener: OnLocClickListener
    ) : RecyclerView.Adapter<AdapterForLoc.LocHolder>() {

        inner class LocHolder(val binding2: ItemLocationBinding) : RecyclerView.ViewHolder(binding2.root) {
            init {
                itemView.setOnClickListener{

                    listener.OnItemLocClick(adapterPosition, LocList)


                }
            }
        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocHolder {
            val binding2 = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LocHolder(binding2)
        }


        interface OnLocClickListener {
            fun OnItemLocClick(position: Int, movie: List<ResultForLocations>)
        }

        override fun onBindViewHolder(holder: LocHolder, position: Int) {
            with(holder) {
                //forCharacters
                binding2.apply {
                    binding2.nameOfChar.text = LocList[position].name
                    binding2.dimension.text = LocList[position].dimension
                    binding2.idOfChar.text = LocList[position].id
                    binding2.typeOfLocation.text = LocList[position].type

                }




            }    }

        override fun getItemCount(): Int {
           return  LocList.size
        }
    }