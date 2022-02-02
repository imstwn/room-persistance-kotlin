package com.example.android.app

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.WordListAdapter.WordViewHolder

class WordListAdapter
    : ListAdapter<Word, WordViewHolder>(WORDS_COMPARATOR) {
    lateinit var word: Word
    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            current.book,
            current.publisher,
            current.year,
            current.price
            )

        holder.itemView.setOnCreateContextMenuListener(object : View.OnCreateContextMenuListener{
            override fun onCreateContextMenu(
                menu: ContextMenu?,
                v: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
            ) {
                menu!!.setHeaderTitle(current.book)
                menu!!.add(0, 0, 0, "Edit")
                menu!!.add(0, 1, 1, "Delete")
                menu!!.add(0, 2, 2, "Delete All")

                word = current
                selectedPosition = holder.adapterPosition
            }
        })

    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookItemViewA: TextView = itemView.findViewById(R.id.textView1)
        private val bookItemViewB: TextView = itemView.findViewById(R.id.textView2)
        private val bookItemViewC: TextView = itemView.findViewById(R.id.textView3)
        private val bookItemViewD: TextView = itemView.findViewById(R.id.textView4)

        fun bind(name: String?, publisher: String?, year: String?, price: String?) {
            bookItemViewA.text = name
            bookItemViewB.text = publisher
            bookItemViewC.text = year
            bookItemViewD.text = price
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.book == newItem.book
            }
        }
    }

}
