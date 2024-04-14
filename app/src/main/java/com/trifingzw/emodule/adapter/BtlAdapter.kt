package com.trifingzw.emodule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.trifingzw.emodule.helper.FileData


class BtlAdapter(private var btlList: ArrayList<FileData>, val onClick: (FileData) -> Unit) : RecyclerView.Adapter<BtlAdapter.ProjectHolder>() {

    fun refresh(newBtlList: List<FileData>) {
        val diffCallback = FileDataDiffCallback(this.btlList, newBtlList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.btlList.clear()
        this.btlList.addAll(newBtlList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return btlList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(com.trifingzw.emodule.R.layout.item_btl, parent, false)
        return ProjectHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val data = btlList[position]
        holder.card.setOnClickListener { onClick(btlList[holder.adapterPosition]) }
        holder.title.text = data.name
        holder.description.text = data.size
    }

    class ProjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView
        val title: TextView
        val description: TextView

        init {
            card = itemView.findViewById(com.trifingzw.emodule.R.id.card)
            title = itemView.findViewById(com.trifingzw.emodule.R.id.title)
            description = itemView.findViewById(com.trifingzw.emodule.R.id.description)
        }
    }

    private class FileDataDiffCallback(
        private val oldList: List<FileData>,
        private val newList: List<FileData>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // 比较两个项目是否代表同一个实体
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // 比较两个项目的内容是否一致
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}