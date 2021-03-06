package fr.montpelliertechhub.abctestchronometer

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository
import fr.montpelliertechhub.abctestchronometer.road.RoadActivity
import fr.montpelliertechhub.abctestchronometer.utils.inflate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


/**
 * TODO : architecture :
 * https://github.com/googlesamples/android-architecture/blob/todo-mvp-kotlin/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/BaseView.kt
 * https://github.com/googlesamples/android-architecture/blob/todo-mvp-clean/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/BaseView.java
 * TODO : input
 */
class ABCAdapter(val abTests: MutableList<ABTest>, val listener: (ABTest) -> Unit) : RecyclerView.Adapter<ABCAdapter.ABCViewHolder>() {

    class ABCViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(abTest: ABTest,
                 listener: (ABTest) -> Unit) {
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
                listener(abTest)
            }
        }
    }

    override fun onBindViewHolder(holder: ABCViewHolder, position: Int) {
        holder.bind(abTests[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ABCViewHolder(parent inflate R.layout.abc_item_view)

    override fun getItemCount() = abTests.size

}

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ABCAdapter(ABTestRepository.getInstance(applicationContext).getABTests()) {
            // We may use Anko here, see https://discuss.kotlinlang.org/t/java-interopt-android-intent/1450
            startActivity(RoadActivity.onNewIntent(this@MainActivity, ABTestRepository.getInstance(applicationContext).getABTests().indexOf(it)))
        }
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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
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
