package com.example.roomdatabase.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.model.User

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList: List<User> = emptyList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val crId: TextView = itemView.findViewById(R.id.crId)
        val crFirstName: TextView = itemView.findViewById(R.id.crName)
        val crLastName: TextView = itemView.findViewById(R.id.crFamily)
        val crAge: TextView = itemView.findViewById(R.id.crAge)
        val crRoot: ConstraintLayout = itemView.findViewById(R.id.crRoot)
        val counter: TextView = itemView.findViewById(R.id.counter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.crId.text = currentItem.id.toString()
        holder.crFirstName.text = currentItem.firstname
        holder.crLastName.text = currentItem.lastName
        holder.crAge.text = currentItem.age.toString()
        holder.counter.text = "${userList.size - position}"

        holder.crRoot.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

}