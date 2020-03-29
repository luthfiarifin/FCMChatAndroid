package com.laam.firebasechatfcm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laam.firebasechatfcm.R
import com.laam.firebasechatfcm.id
import com.laam.firebasechatfcm.response.HeaderResponse
import com.laam.firebasechatfcm.ui.DetailChatActivity
import kotlinx.android.synthetic.main.item_header.view.*

class HeaderAdapter(
    private val list: List<HeaderResponse>
) : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val headerResponse = list[position]
        if (headerResponse.user1.id == id) {
            holder.itemView.tv_header_name.text = headerResponse.user2.name
        } else {
            holder.itemView.tv_header_name.text = headerResponse.user1.name
        }
        holder.itemView.tv_header_text.text = headerResponse.lastMessage?.message ?: "N/A"

        holder.itemView.setOnClickListener {
            Intent(holder.itemView.context, DetailChatActivity::class.java).apply {
                putExtra("header", headerResponse.id)
                holder.itemView.context.startActivity(this)
            }
        }
    }
}