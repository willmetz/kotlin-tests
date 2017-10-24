package com.slapshotapps.myfirstkotlinproject.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slapshotapps.myfirstkotlinproject.R
import com.slapshotapps.myfirstkotlinproject.databinding.RelatedPagesListItemViewBinding
import com.slapshotapps.myfirstkotlinproject.viewmodels.RelatedPagesItem
import com.slapshotapps.network.response.RelatedPagesModel


class RelatedPagesAdapter(
    var relatedPages: List<RelatedPagesItem>, val context : Context) : RecyclerView.Adapter<RelatedPagesAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return relatedPages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = relatedPages.get(position)
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = RelatedPagesListItemViewBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    class ViewHolder(val binding :RelatedPagesListItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}