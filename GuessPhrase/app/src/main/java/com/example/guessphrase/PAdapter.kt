package com.example.guessphrase

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class PAdapter(val context: Context, val message: ArrayList<String>) :
    RecyclerView.Adapter<PAdapter.MessageViewHolder>() {
    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = message[position]

        holder.itemView.apply {
            itemText.text = message
             when {
                message.startsWith("Found") -> {
                    itemText.setTextColor(Color.BLUE)
                }
                message.startsWith("No") || message.startsWith("Wrong")->{
                    itemText.setTextColor(Color.RED)
                }
                else->{
                    itemText.setTextColor(Color.BLACK)

                }

            }

        }
    }

    override fun getItemCount() = message.size

}
