package com.trifingzw.emodule.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.trifingzw.emodule.helper.Project
import com.trifingzw.emodule.helper.projects
import com.trifingzw.emodule.helper.saveProject


class ProjectAdapter(val onClick: (Project) -> Unit) : RecyclerView.Adapter<ProjectAdapter.ProjectHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(com.trifingzw.emodule.R.layout.item_project, parent, false)
        return ProjectHolder(itemView)
    }

    override fun getItemCount(): Int {
        return projects.count()
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val data = projects[position]
        holder.title.text = data.name
        holder.description.text = data.description
        holder.workPath.text = data.workPath
        holder.delete.setOnClickListener {
            remove(holder.adapterPosition)
        }
        holder.start.setOnClickListener {
            onClick(projects[holder.adapterPosition])
        }
    }

    fun add(project: Project) {
        projects.add(project)
        notifyItemInserted(projects.size - 1)
        saveProject()
    }

    fun remove(index: Int) {
        projects.removeAt(index)
        notifyItemRemoved(index)
        saveProject()
    }

    class ProjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val description: TextView
        val workPath: TextView
        val delete: Button
        val start: Button

        init {
            title = itemView.findViewById(com.trifingzw.emodule.R.id.title)
            description = itemView.findViewById(com.trifingzw.emodule.R.id.description)
            workPath = itemView.findViewById(com.trifingzw.emodule.R.id.work_path)
            delete = itemView.findViewById(com.trifingzw.emodule.R.id.delete)
            start = itemView.findViewById(com.trifingzw.emodule.R.id.start)
        }
    }
}


class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // 如果你想在第一个项目上方也添加一些空间
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        }
    }
}