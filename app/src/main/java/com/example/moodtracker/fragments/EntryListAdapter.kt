package com.example.moodtracker.fragments

import android.content.Context
import android.graphics.Typeface
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.R
import com.example.moodtracker.data.Entry
import java.security.KeyStore

// This entire page is from: https://tutorialwing.com/android-expandablelistview-using-kotlin-example/
//class EntryListAdapter internal constructor(private val context: Context, private val titleList: List<String>, private var dataList: HashMap<String, List<String>>): BaseExpandableListAdapter() {
class EntryListAdapter internal constructor(private val context: Context): BaseExpandableListAdapter() {

    var dataList: HashMap<String, List<String>> = HashMap()
    var titleList: List<String> = emptyList()
    var imageList: List<Int> = emptyList()

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.entry_list_row_dropdown, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.entries_tv_journalDropDown)
        expandedListTextView.text = expandedListText
        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
    }

    fun getImageGroup(listPosition: Int): Any {
        return this.imageList[listPosition]
    }

    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        val imageId = getImageGroup(listPosition) as Int
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.entry_list_row, null)
        }
        val listImageView: ImageView = convertView!!.findViewById(R.id.entries_img_emoji)
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.entries_tv_header)
        listImageView.setImageResource(imageId)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

    fun setData(entries: HashMap<String, List<String>>, names: List<String>, images: List<Int>) {
        this.dataList = entries
        this.titleList = names
        this.imageList = images
        notifyDataSetChanged()
    }
}