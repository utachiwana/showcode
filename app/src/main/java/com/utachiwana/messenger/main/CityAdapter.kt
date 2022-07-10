package com.utachiwana.messenger.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.utachiwana.messenger.main.pojo.CityClassItem

class CityAdapter : BaseAdapter(), Filterable {

    private val cities = ArrayList<CityClassItem>(10)


    override fun getCount(): Int {
        return cities.size
    }

    override fun getItem(p0: Int): Any {
        return cities[p0].name
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view =
            LayoutInflater.from(p2?.context).inflate(android.R.layout.simple_list_item_1, p2, false)
        view.findViewById<TextView>(android.R.id.text1).text = cities[p0].name
        return view
    }

    fun clear() {
        cities.clear()
    }

    fun addAll(list: List<CityClassItem>?) {
        if (list != null)
            cities.addAll(list)
    }

    fun getCity(pos : Int) = cities[pos]

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(text: CharSequence?): FilterResults {
                return FilterResults().apply { values = cities }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

}