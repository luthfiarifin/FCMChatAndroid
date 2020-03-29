package com.laam.firebasechatfcm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laam.firebasechatfcm.R
import com.laam.firebasechatfcm.id
import com.laam.firebasechatfcm.response.DetailResponse
import com.laam.firebasechatfcm.response.HeaderResponse
import kotlinx.android.synthetic.main.item_detail_receiver.view.*
import kotlinx.android.synthetic.main.item_detail_sender.view.*
import kotlinx.android.synthetic.main.item_header.view.*

class DetailAdapter(
    private var list: List<DetailResponse>
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val VIEW_TYPE_MESSAGE_SENDER = 1
    private val VIEW_TYPE_MESSAGE_RECEIVER = 2

    override fun getItemViewType(position: Int): Int {
        val detailResponse = list[position]

        return if (detailResponse.sender.id == id) {
            VIEW_TYPE_MESSAGE_SENDER
        } else {
            VIEW_TYPE_MESSAGE_RECEIVER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        if (viewType == VIEW_TYPE_MESSAGE_SENDER) {
            ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_sender, parent, false)
            )
        } else {
            ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_receiver, parent, false)
            )
        }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val headerResponse = list[position]
        val item = holder.itemView

        if (holder.itemViewType == VIEW_TYPE_MESSAGE_SENDER) {
            item.tv_detail_sender_text.text = headerResponse.message
        } else {
            item.tv_detail_receiver_text.text = headerResponse.message

        }
    }

    fun updateList(list: List<DetailResponse>) {
        this.list = list
        notifyItemInserted(list.size - 1)
    }
}