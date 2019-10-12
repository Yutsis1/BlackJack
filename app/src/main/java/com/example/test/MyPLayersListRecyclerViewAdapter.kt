package com.example.test

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.test.PLayersListFragment.chosePlayer
import com.example.test.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.fragment_playerslist.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [chosePlayer].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPLayersListRecyclerViewAdapter(
    private val mValues: ArrayList<PlayerData>,
    private val mListener: chosePlayer?
) : RecyclerView.Adapter<MyPLayersListRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as PlayerData
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onPlayerSlecet(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_playerslist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.idView.text = item.id.toString()
        holder.nameView.text = item.name
        holder.scoreView.text = item.score.toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val idView: TextView = mView.item_number
        val nameView: TextView = mView.name
        val scoreView: TextView = mView.score

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }
}
