package com.example.eserciziologin.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eserciziologin.ui.ComuniAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.eserciziologin.R
import com.example.eserciziologin.model.Comune

class ComuniAdapter(private val clickListener: OnClickListener) :
    ListAdapter<Comune, ComuniAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemText: TextView = itemView.findViewById(R.id.item_text)
        fun bind(comune: Comune) {
            itemText.text = comune.nome
        }
    }


    object DiffCallback : DiffUtil.ItemCallback<Comune>() {
        override fun areItemsTheSame(oldItem: Comune, newItem: Comune): Boolean {
            return oldItem.nome == newItem.nome
        }

        override fun areContentsTheSame(oldItem: Comune, newItem: Comune): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (comune: Comune) -> Unit) {
        fun onClick(comune: Comune) = clickListener(comune)
    }


}