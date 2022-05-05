package dinh.example.project1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RenderActivity : AppCompatActivity() {
    companion object RenderActivityData {
        var informationTemp:MutableList<Information> = mutableListOf<Information>()
    }

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_render)

        viewManager = LinearLayoutManager(this)
        recyclerAdapter = RecyclerAdapter(TimeActivity.information)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = recyclerAdapter  }

        recyclerView.adapter = RecyclerAdapter(TimeActivity.information)
        recyclerAdapter.notifyDataSetChanged()

    }

    fun onClick(view: View) {
        informationTemp.clear()
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun removeLastButton(view: View) {
        val prefsEditor = getSharedPreferences("SharedPref", Context.MODE_PRIVATE).edit()
        TimeActivity.information.removeLast()
        var lastString1 = informationTemp.removeLast()
        recyclerView.adapter = RecyclerAdapter(informationTemp)
        recyclerAdapter.notifyDataSetChanged()
        prefsEditor.remove(lastString1.toString())
        prefsEditor.apply()
    }
    fun removeAllButton(view: View) {
        val prefsEditor = getSharedPreferences("SharedPref", Context.MODE_PRIVATE).edit()
        TimeActivity.information.clear()
        informationTemp.clear()
        recyclerView.adapter = RecyclerAdapter(informationTemp)
        recyclerAdapter.notifyDataSetChanged()
        prefsEditor.clear()
        prefsEditor.apply()
    }

    override fun onResume() {
        super.onResume()

            getSharedPreferences("SharedPref", Context.MODE_PRIVATE).all.forEach {

                var data: String? = this.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
                    .getString(it.key.toString(), "")

                var type: String = ((data!!.substringAfter("type=")).substringBefore(","))
                var event: String = ((data.substringAfter("event=")).substringBefore(","))
                var dateTime: String = ((data.substringAfter("dateTime=")).substringBefore(","))
                var state: String = ((data.substringAfter("state=")).substringBefore(")"))

                informationTemp.add(Information(type, event, dateTime, state))
                recyclerView.adapter = RecyclerAdapter(informationTemp)
                recyclerAdapter.notifyDataSetChanged()

            }
        val toast = Toast.makeText(applicationContext, "All Your Event Was Listed Here", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP and  Gravity.CENTER_HORIZONTAL,0,0)
        toast.show()

    }

   /* override fun onPause() {
        val prefsEditor = getSharedPreferences("SharedPref", Context.MODE_PRIVATE).edit()
        for (info in TimeActivity.information)
        {
            prefsEditor.putString(TimeActivity.count.toString(), info.toString())
        }
        prefsEditor.apply()
        super.onPause()
    }*/
}