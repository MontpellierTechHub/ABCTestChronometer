package fr.montpelliertechhub.abctestchronometer

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


inline fun <reified V : View> View.bindView(resId: Int): ReadOnlyProperty<View, V> = Delegate {
    this.findViewById<V>(resId)
}

inline fun <reified V : View> Activity.bindView(resId: Int): ReadOnlyProperty<Activity, V> = Delegate {
    this.findViewById<V>(resId)
}

class Delegate<in A, out V>(val funFindView: (A) -> V) : ReadOnlyProperty<A, V> {
    override fun getValue(thisRef: A, property: KProperty<*>): V {
        return funFindView(thisRef)
    }
}

class RoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_road)
    }
}


class RoadAdapter : RecyclerView.Adapter<RoadAdapter.RoadViewHolder>() {

    val ITEM_RESUME = 0
    val ITEM_DETAIL = 1


    abstract class RoadViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(abTest: ABTest)
    }

    class ResumeRoadViewHolder(itemView: View) : RoadViewHolder(itemView) {

        val textView by lazy { itemView.findViewById<TextView>(R.id.textview1) }

        override fun bind(abTest: ABTest) {

            itemView.setOnClickListener {
                it
            }
        }
    }

    class DetailRoadViewHolder(itemView: View?) : RoadViewHolder(itemView) {
        override fun bind(abTest: ABTest) {



        }
    }

    override fun onBindViewHolder(holder: RoadViewHolder?, position: Int) {
        holder?.bind(ABTestRepository.abTestContainer.abtests[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when(viewType) {
            ITEM_RESUME -> ResumeRoadViewHolder(parent inflate R.layout.road_resume_item)
            ITEM_DETAIL -> DetailRoadViewHolder(parent inflate R.layout.road_item_view)
            else -> throw IllegalStateException("Not managed view type")
        }

    override fun getItemCount() = ABTestRepository.abTestContainer.abtests.size

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> ITEM_RESUME
            else -> ITEM_DETAIL
        }


}