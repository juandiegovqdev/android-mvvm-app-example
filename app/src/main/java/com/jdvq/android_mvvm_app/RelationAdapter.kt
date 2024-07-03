package com.jdvq.android_mvvm_app


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.domain.entities.RelationEntity
import com.jdvq.android_mvvm_app.domain.models.ObjectModel
import com.jdvq.android_mvvm_app.presentation.main.MainViewModel

class RelationAdapter(
    private val viewModel: MainViewModel,
    private val objectModel: ObjectModel,
    private val relations: MutableList<RelationEntity>,
    private val onDeleteClicked: (RelationEntity) -> Unit
) : RecyclerView.Adapter<RelationAdapter.RelationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_relation, parent, false)
        return RelationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RelationViewHolder, position: Int) {
        holder.bind(relations[position])
    }

    override fun getItemCount(): Int = relations.size

    fun updateRelations(newRelations: List<RelationEntity>) {
        relations.clear()
        relations.addAll(newRelations)
        notifyDataSetChanged()
    }


    inner class RelationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val relationTextView: TextView = itemView.findViewById(R.id.relationTextView)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(relation: RelationEntity) {
            relationTextView.text = "Parent ID: ${relation.parentId}, Child ID: ${relation.childId}"
            deleteButton.setOnClickListener {
                onDeleteClicked(relation)
                viewModel.deleteRelation(relation)
                viewModel.getRelation(objectModel)
                updateRelations(GlobalVariables.relations)
                notifyDataSetChanged()
            }
        }
    }
}
