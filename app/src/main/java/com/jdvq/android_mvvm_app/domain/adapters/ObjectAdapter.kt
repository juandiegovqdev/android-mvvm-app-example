package com.jdvq.android_mvvm_app.domain.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.jdvq.android_mvvm_app.R
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.domain.models.ObjectModel
import com.jdvq.android_mvvm_app.presentation.fragments.EditObjectFragment
import com.jdvq.android_mvvm_app.presentation.main.MainActivity
import com.jdvq.android_mvvm_app.presentation.main.MainViewModel

class ObjectAdapter(
    private val context: Context,
    private val viewModel: MainViewModel,
    private val activity: FragmentActivity?,
    private val onItemClick: ((ObjectModel) -> Unit)? = null
) : RecyclerView.Adapter<ObjectAdapter.ObjectViewHolder>(), Filterable {

    private var objectModels = mutableListOf<ObjectModel>()
    private var objectsFiltered: List<ObjectModel> = listOf()

    init {
        objectsFiltered = objectModels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_object, parent, false)
        return ObjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ObjectViewHolder, position: Int) {
        val obj = objectsFiltered[position]
        holder.bind(obj)
    }

    override fun getItemCount(): Int {
        return objectsFiltered.size
    }

    fun setObjects(objectModels: List<ObjectModel>) {
        this.objectModels = objectModels.toMutableList()
        this.objectsFiltered = objectModels
        notifyDataSetChanged()
    }

    private fun showPopupMenu(view: View, objectModel: ObjectModel) {
        val popup = PopupMenu(context, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_item_object, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_edit -> {
                    val bundle = Bundle().apply {
                        putParcelable("objectModel", objectModel)
                    }
                    (activity as MainActivity).loadFragment(EditObjectFragment(), bundle)

                    true
                }

                R.id.action_delete -> {
                    viewModel.deleteObjectById(objectModel)
                    viewModel.getAllObject()
                    this.objectModels = GlobalVariables.objects
                    notifyDataSetChanged()
                    true
                }

                else -> false
            }
        }
        popup.show()
    }

    inner class ObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val menuButton: ImageView = itemView.findViewById(R.id.menuButton)

        fun bind(obj: ObjectModel) {

            menuButton.setOnClickListener {
                showPopupMenu(it, obj)
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(obj)
            }
            nameTextView.text = obj.name
            descriptionTextView.text = obj.description
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence?.toString() ?: ""
                objectsFiltered = if (charString.isEmpty()) {
                    objectModels
                } else {
                    val filteredList = ArrayList<ObjectModel>()
                    for (obj in objectModels) {
                        if (obj.name.contains(charString, true) || obj.description.contains(
                                charString, true
                            )
                        ) {
                            filteredList.add(obj)
                        }
                    }
                    filteredList
                }
                return FilterResults().apply { values = objectsFiltered }
            }

            override fun publishResults(
                charSequence: CharSequence?, filterResults: FilterResults?
            ) {
                objectsFiltered = filterResults?.values as List<ObjectModel>
                notifyDataSetChanged()
            }
        }
    }
}