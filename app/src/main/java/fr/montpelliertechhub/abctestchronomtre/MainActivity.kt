package fr.montpelliertechhub.abctestchronomtre

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main2.*


infix fun ViewGroup.inflate(@LayoutRes res: Int): View =
        LayoutInflater.from(this.context).inflate(res, this, false)

class ABCAdapter : RecyclerView.Adapter<ABCAdapter.ABCViewHolder>() {


    class ABCViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(abTest: ABTestContainer) {
            itemView.findViewById<TextView>(R.id.destination_textview).text = abTest.title
            itemView.findViewById<TextView>(R.id.road_textview).setText(
                    """${abTest.tries.size} petits chemin${ if (abTest.tries.size > 1) "s" else ""}
                        | dont ${abTest.tries.filter { it.tries.size > 0 }.size} déjà pris
                    """.trimMargin())
        }
    }

    override fun onBindViewHolder(holder: ABCViewHolder?, position: Int) {
        holder?.bind(ABTestRepository.abTestList.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = ABCViewHolder(parent inflate R.layout.abc_item_view)

    override fun getItemCount() = ABTestRepository.abTestList.size

}
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val mRecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = ABCAdapter()


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
