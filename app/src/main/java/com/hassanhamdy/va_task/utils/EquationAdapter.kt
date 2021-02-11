package com.hassanhamdy.va_task.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hassanhamdy.va_task.model.MathEquationModel
import com.hassanhamdy.va_task.R

class EquationAdapter(
    private val items: ArrayList<MathEquationModel>,
    private val context: Context
) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEquationItem.text = items.get(position).toString()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvEquationItem = view.findViewById<TextView>(R.id.tv_equation)
}