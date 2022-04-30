package com.an_mikhaylov.animalshandbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.an_mikhaylov.animalshandbook.databinding.AnimalItemBinding

class AnimalAdapter: RecyclerView.Adapter<AnimalAdapter.AnimalHolder>() {
    val animalList = ArrayList<Animal>()
    class AnimalHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = AnimalItemBinding.bind(item)
        fun  bind(animal: Animal) = with(binding) {
            im.setImageResource(animal.imageId)
            tvTitle.text = animal.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.animal_item, parent, false)
        return  AnimalHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalHolder, position: Int) {
        holder.bind(animalList[position])
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    fun addAnimal(animal: Animal) {
        animalList.add(animal)
        notifyDataSetChanged()
    }
}