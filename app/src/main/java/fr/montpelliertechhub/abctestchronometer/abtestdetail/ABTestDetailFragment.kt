package fr.montpelliertechhub.abctestchronometer.abtestdetail

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.measure.MeasureActivity
import fr.montpelliertechhub.abctestchronometer.models.AB
import fr.montpelliertechhub.abctestchronometer.models.ABTest

/**
 * Display the list of AB Tests in a RecyclerView
 *
 * Created by Hugo Gresse on 17/08/2018.
 */
class ABTestDetailFragment : Fragment(), ABTestDetailContract.View {

    override lateinit var presenter: ABTestDetailContract.Presenter
    override var isActive: Boolean = false
        get() = isAdded

    private var itemListener: ABItemListener = object : ABItemListener {
        override fun onABClick(clickedAB: AB) {
            presenter.openABTestMeasure(clickedAB)
        }
    }

    private val listAdapter = RoadAdapter(itemListener)
    private val layoutManager = LinearLayoutManager(context)
    private lateinit var absView: RecyclerView
    private lateinit var noABsView: ViewGroup

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.abtestdetail_fragment, container, false)

        // Set up tasks view
        with(root) {
            absView = findViewById<RecyclerView>(R.id.recyclerView).apply { adapter = listAdapter }
            noABsView = findViewById<LinearLayout>(R.id.noABs)
        }

        absView.layoutManager = layoutManager

        // Set up floating action button
        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_abtest).apply {
            setImageResource(R.drawable.ic_add_24dp)
            setOnClickListener { presenter.addNewAB() }
        }
        setHasOptionsMenu(true)

        return root
    }

    override fun showResume(text: AB?) {
        // This is done is the adapter, probably not the good place to do it
    }

    override fun hideResume() {
        // This is done is the adapter, probably not the good place to do it
    }

    override fun showABs(abList: ABTest) {
        listAdapter.setData(abList)
        absView.visibility = View.VISIBLE
        noABsView.visibility = View.GONE
    }

    override fun showMissingABTest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoAB() {
        showNoABsViews()
    }

    override fun showAddAB() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showABMeasure(ab: AB) {
        startActivity(MeasureActivity.onNewIntent(
                context,
                ab.id))
    }

    private fun showNoABsViews() {
        absView.visibility = View.GONE
        noABsView.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance() = ABTestDetailFragment()
    }

}