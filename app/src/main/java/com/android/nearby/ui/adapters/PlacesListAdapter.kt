package com.adyen.android.assignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.R
import com.adyen.android.assignment.data.model.Category
import com.adyen.android.assignment.data.model.Places
import com.adyen.android.assignment.databinding.ItemPlacesBinding
import com.bumptech.glide.Glide
import javax.inject.Inject
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Adapter for displaying list of placeItems
 */

class PlacesListAdapter @Inject constructor() :
    RecyclerView.Adapter<PlacesListAdapter.PlacesViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<Places>() {
        override fun areItemsTheSame(oldItem: Places, newItem: Places): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Places, newItem: Places): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    class PlacesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemBinding = ItemPlacesBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        return PlacesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_places,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val placeItem = differ.currentList[position]
        holder.itemBinding.venueTitle.text = placeItem.name

        if (!placeItem.categories.isNullOrEmpty()) {
            holder.itemBinding.venueType.text = placeItem.categories[0].name
            val options: RequestOptions = RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            holder.itemView.apply {
                Glide.with(this)
                    .load(getIconPath(placeItem.categories[0]))
                    .apply(options)
                    .into(holder.itemBinding.venueImage)
            }
        }
        holder.itemBinding.venueAddress.text = placeItem.location.address

    }

    private fun getIconPath(category: Category): String {
        return "${category.icon.prefix}88${category.icon.suffix}"
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}
