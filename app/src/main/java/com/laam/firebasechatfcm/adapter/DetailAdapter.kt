package com.laam.firebasechatfcm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laam.firebasechatfcm.R
import com.laam.firebasechatfcm.URL_IMAGE
import com.laam.firebasechatfcm.id
import com.laam.firebasechatfcm.response.DetailResponse
import kotlinx.android.synthetic.main.item_detail_receiver.view.*
import kotlinx.android.synthetic.main.item_detail_receiver_image.view.*
import kotlinx.android.synthetic.main.item_detail_sender.view.*
import kotlinx.android.synthetic.main.item_detail_sender_image.view.*

class DetailAdapter(
    private var list: MutableList<DetailResponse>
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val VIEW_TYPE_MESSAGE_SENDER = 1
    private val VIEW_TYPE_MESSAGE_RECEIVER = 2
    private val VIEW_TYPE_MESSAGE_SENDER_IMAGE = 3
    private val VIEW_TYPE_MESSAGE_RECEIVER_IMAGE = 4

    override fun getItemViewType(position: Int): Int {
        val detailResponse = list[position]

        return if (detailResponse.sender.id == id) {
            if (detailResponse.image.isNullOrEmpty()) {
                VIEW_TYPE_MESSAGE_SENDER
            } else {
                VIEW_TYPE_MESSAGE_SENDER_IMAGE
            }
        } else {
            if (detailResponse.image.isNullOrEmpty()) {
                VIEW_TYPE_MESSAGE_RECEIVER
            } else {
                VIEW_TYPE_MESSAGE_RECEIVER_IMAGE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            VIEW_TYPE_MESSAGE_SENDER -> {
                ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_detail_sender, parent, false)
                )
            }
            VIEW_TYPE_MESSAGE_RECEIVER -> {
                ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_detail_receiver, parent, false)
                )
            }

            VIEW_TYPE_MESSAGE_SENDER_IMAGE -> {
                ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_detail_sender_image, parent, false)
                )
            }
            VIEW_TYPE_MESSAGE_RECEIVER_IMAGE -> {
                ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_detail_receiver_image, parent, false)
                )
            }
            else -> {
                ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_detail_sender, parent, false)
                )
            }
        }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val headerResponse = list[position]
        val item = holder.itemView

        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENDER -> {
                item.tv_detail_sender_text.text = headerResponse.message
            }
            VIEW_TYPE_MESSAGE_RECEIVER -> {
                item.tv_detail_receiver_text.text = headerResponse.message
            }

            VIEW_TYPE_MESSAGE_SENDER_IMAGE -> {
                Glide.with(context)
                    .load("$URL_IMAGE${headerResponse.image}")
                    .into(item.img_detail_sender_image)
            }
            VIEW_TYPE_MESSAGE_RECEIVER_IMAGE -> {
                Glide.with(context)
                    .load("$URL_IMAGE${headerResponse.image}")
                    .into(item.img_detail_receiver_image)
            }
        }
    }

    fun updateList(newList: List<DetailResponse>) {
        val curSize = list.size
        list.clear()
        list.addAll(newList)
        notifyItemRangeInserted(curSize, list.size)
    }

    fun addItem(newItem: DetailResponse) {
        val curSize = list.size
        list.add(newItem)
        notifyItemRangeInserted(curSize, list.size)
    }
}