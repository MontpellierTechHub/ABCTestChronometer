package fr.montpelliertechhub.abctestchronometer.abtestdetail

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.models.AB
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.utils.inflate

// TODO : move string to res
class RoadAdapter(private val listener: ABItemListener) : RecyclerView.Adapter<RoadAdapter.RoadViewHolder>() {

    val TAG: String = RoadAdapter::class.java.simpleName

    val ITEM_RESUME = 0
    val ITEM_DETAIL = 1

    var abTest: ABTest? = null

    abstract class RoadViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind()
    }

    class ResumeRoadViewHolder(itemView: View) : RoadViewHolder(itemView) {

        val textView: TextView by lazy { itemView.findViewById<TextView>(R.id.textview1) }

        override fun bind() {
            // Nothing specific here
        }

        fun bind(ab: AB?) {
            if (ab == null) {
                textView.text = "Aucune mesure"
            } else {
                textView.text = "Le meilleur chemin est \n" + ab.title
            }
        }
    }

    class DetailRoadViewHolder(itemView: View) : RoadViewHolder(itemView) {

        val destinationTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.destination_textview) }
        val roadTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.road_textview) }
        val triesTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tries_textview) }

        override fun bind() {
            // Nothing specific here
        }

        fun bind(ab: AB, listener: ABItemListener) {
            bind()
            roadTextView.text = ab.title
            destinationTextView.text = "De " + ab.from + " jusqu'à " + ab.to
            triesTextView.text = ab.tries.size.toString() + " mesure(s)"

            itemView.setOnClickListener { listener.onABClick(ab) }
        }
    }

    override fun onBindViewHolder(holder: RoadViewHolder, position: Int) {
        if (abTest == null) return
        when (holder) {
            is DetailRoadViewHolder -> holder.bind(abTest!!.ab[position - 1], listener)
            is ResumeRoadViewHolder -> holder.bind(abTest!!.getBestWay())
            else                    -> Log.w(TAG, "Case not managed /!\\")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when (viewType) {
                ITEM_RESUME -> ResumeRoadViewHolder(parent inflate R.layout.road_resume_item)
                ITEM_DETAIL -> DetailRoadViewHolder(parent inflate R.layout.road_item_view)
                else        -> throw IllegalStateException("Not managed view type")
            }

    override fun getItemCount() = if (abTest == null) 0 else abTest!!.ab.size + 1

    override fun getItemViewType(position: Int): Int =
            when (position) {
                0    -> ITEM_RESUME
                else -> ITEM_DETAIL
            }

    fun setData(data: ABTest) {
        abTest = data
        notifyDataSetChanged()
    }
}


interface ABItemListener {
    fun onABClick(clickedAB: AB)
}