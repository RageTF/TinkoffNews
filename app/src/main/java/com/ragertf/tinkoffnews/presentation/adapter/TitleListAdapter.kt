package com.ragertf.tinkoffnews.presentation.adapter

import android.support.v4.text.HtmlCompat
import android.support.v4.text.HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ragertf.tinkoffnews.R
import com.ragertf.tinkoffnews.presentation.model.TitleModel
import com.ragertf.tinkoffnews.utils.toDateTimeFormat
import kotlinx.android.synthetic.main.item_title.view.*

class TitleListAdapter : RecyclerView.Adapter<TitleListAdapter.TitleViewHolder>() {

    var titleList: List<TitleModel> = emptyList()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(TitleDiffCalback(field, ArrayList<TitleModel>(value)))
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var listener: ((TitleModel) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TitleViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_title, p0, false)
        return TitleViewHolder(view)
    }

    override fun getItemCount(): Int = titleList.size

    override fun onBindViewHolder(p0: TitleViewHolder, p1: Int) {
        p0.bind(p1)
    }


    inner class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal fun bind(position: Int) {
            val titleModel = titleList[position]
            itemView.title.text = HtmlCompat.fromHtml(titleModel.text,TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
            itemView.date.text = titleModel.publicationDate.toDateTimeFormat()
            itemView.setOnClickListener {
                listener?.invoke(titleModel)
            }
        }

    }

    internal class TitleDiffCalback(private val oldList: List<TitleModel>, private val newList: List<TitleModel>) : DiffUtil.Callback() {

        override fun areItemsTheSame(p0: Int, p1: Int): Boolean = oldList[p0].id == newList[p1].id

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(p0: Int, p1: Int): Boolean = true

    }

}