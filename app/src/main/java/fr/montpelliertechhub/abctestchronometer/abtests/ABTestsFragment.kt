package fr.montpelliertechhub.abctestchronometer.abtests

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.abtestdetail.ABTestDetailActivity
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.utils.inflate

/**
 * Display the list of AB Tests in a RecyclerView
 *
 * Created by Hugo Gresse on 17/08/2018.
 */
class ABTestsFragment : Fragment(), ABTestsContract.View {
    override lateinit var presenter: ABTestsContract.Presenter
    override var isActive: Boolean = false
        get() = isAdded

    private var itemListener: ABTestItemListener = object : ABTestItemListener {
        override fun onABTestClick(clickedABTest: ABTest) {
            presenter.openABTest(clickedABTest)
        }
    }

    private val listAdapter = ABTestsAdapter(ArrayList(0), itemListener)
    private val layoutManager = LinearLayoutManager(context)
    private lateinit var abtestsView: RecyclerView
    private lateinit var noABTestsView: ViewGroup

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.abtests_fragment, container, false)

        // Set up tasks view
        with(root) {
            abtestsView = findViewById<RecyclerView>(R.id.recyclerView).apply { adapter = listAdapter }
            abtestsView.layoutManager = layoutManager
            noABTestsView = findViewById<LinearLayout>(R.id.noABTests)
        }

        // Set up floating action button
        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_abtest).apply {
            setImageResource(R.drawable.ic_add_24dp)
            setOnClickListener { presenter.addNewABTest() }
        }
        setHasOptionsMenu(true)

        return root
    }

    override fun showABTests(abtests: List<ABTest>) {
        listAdapter.setData(abtests)
        abtestsView.visibility = View.VISIBLE
        noABTestsView.visibility = View.GONE
    }

    override fun showNoABTests() {
        showNoABTestsViews()
    }

    override fun showAddABTest() {
        // TODO: support add new ab test
    }

    override fun showABTestDetails(abTest: ABTest) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
        val intent = Intent(context, ABTestDetailActivity::class.java).apply {
            putExtra(ABTestDetailActivity.INTENT_ABTEST_ID, abTest.id)
        }
        startActivity(intent)
    }

    private fun showNoABTestsViews() {
        abtestsView.visibility = View.GONE
        noABTestsView.visibility = View.VISIBLE
    }

    private class ABTestsAdapter(private var abTests: List<ABTest>, private val listener: ABTestItemListener) : RecyclerView.Adapter<ABTestsAdapter.ABTestsViewHolder>() {

        class ABTestsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            fun bind(abTest: ABTest,
                     listener: ABTestItemListener) {
                val pathsTaken: Int = abTest.ab.fold(0) { total, next ->
                    if (next.tries.isNotEmpty()) total + 1
                    else total
                }
                itemView.findViewById<TextView>(R.id.destination_textview).text = abTest.title
                itemView.findViewById<TextView>(R.id.road_textview).text =
                        """
                    |${abTest.ab.size} petits chemin${if (abTest.ab.size > 1) "s" else ""}
                    |dont ${pathsTaken} déjà pris
                """.trimMargin()
                itemView.setOnClickListener {
                    listener.onABTestClick(abTest)
                }
            }
        }

        fun setData(newData: List<ABTest>) {
            abTests = newData
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ABTestsViewHolder =
                ABTestsViewHolder(parent inflate R.layout.abc_item_view)

        override fun getItemCount(): Int = abTests.size

        override fun onBindViewHolder(holder: ABTestsViewHolder, position: Int) {
            holder.bind(abTests[position], listener)
        }
    }

    interface ABTestItemListener {
        fun onABTestClick(clickedABTest: ABTest)
    }

    companion object {
        fun newInstance() = ABTestsFragment()
    }

}