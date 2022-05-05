package dinh.example.project1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class TimeActivity : AppCompatActivity() {

    companion object TimeActivityData {
        var information:MutableList<Information> = mutableListOf<Information>()
        var event: String = ""
        var date: String = ""
        var time: String = ""
        var type: String = ""
        var state: String = ""
        var count = 0
    }

    lateinit var textView: TextView
    lateinit var calendarView: CalendarView
    lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)
        textView = findViewById(R.id.textViewEvent)
        calendarView = findViewById(R.id.calendarView)
        timePicker = findViewById(R.id.timePicker1)

        textView.text = event
        OnClickTime()

        calendarView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener
        {
            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int)
            {
             date = "$p3.${p2 + 1}.$p1"
            }
        })

    }


    fun onClick(view: View) {
        var dateTime = "${date} ${time}"
        information.add(Information(type,event,dateTime, state))

        var countTemp:Int = getSharedPreferences("SharedPref", Context.MODE_PRIVATE).all.count()
        count = countTemp

        val prefsEditor = getSharedPreferences("SharedPref", Context.MODE_PRIVATE).edit()
        for (info in information)
        {
            prefsEditor.putString(count.toString(), info.toString())
        }
        prefsEditor.apply()

        RenderActivity.informationTemp.clear()

        count = count + 1

        val intent: Intent = Intent(this, RenderActivity::class.java)
        startActivity(intent)
    }

    private fun OnClickTime() {

        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour
            var am_pm = ""
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
                val hour1 = if (hour < 10) "0" + hour else hour
                val min1 = if (minute < 10) "0" + minute else minute
                time = "$hour1 : $min1 $am_pm"
        }
    }

}