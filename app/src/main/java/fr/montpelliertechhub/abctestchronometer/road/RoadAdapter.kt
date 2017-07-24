package fr.montpelliertechhub.abctestchronometer.road

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.models.ABTestContainer
import fr.montpelliertechhub.abctestchronometer.utils.inflate

class RoadAdapter(val abTestContainer: ABTestContainer) : RecyclerView.Adapter<RoadAdapter.RoadViewHolder>() {

    val ITEM_RESUME = 0
    val ITEM_DETAIL = 1

    abstract class RoadViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind()
    }

    class ResumeRoadViewHolder(itemView: View) : RoadViewHolder(itemView) {

        val textView: TextView by lazy { itemView.findViewById<TextView>(R.id.textview1) }

        override fun bind() {
            textView.text = "Meilleur chemin"
        }
    }

    class DetailRoadViewHolder(itemView: View) : RoadViewHolder(itemView) {

        val destinationTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.destination_textview) }
        val roadTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.road_textview) }

        override fun bind() {
            // Nothing specific here
        }

        fun bind(abTest: ABTest) {
            bind()
            destinationTextView.text = abTest.to
            roadTextView.text = abTest.title
        }
    }

    override fun onBindViewHolder(holder: RoadViewHolder?, position: Int) {
        if(holder is DetailRoadViewHolder){
            holder.bind(abTestContainer.abtests[position - 1])
        } else {
            holder?.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when (viewType) {
                ITEM_RESUME -> ResumeRoadViewHolder(parent inflate R.layout.road_resume_item)
                ITEM_DETAIL -> DetailRoadViewHolder(parent inflate R.layout.road_item_view)
                else -> throw IllegalStateException("Not managed view type")
            }

    override fun getItemCount() = abTestContainer.abtests.size +1

    override fun getItemViewType(position: Int): Int =
            when (position) {
                0 -> ITEM_RESUME
                else -> ITEM_DETAIL
            }

}