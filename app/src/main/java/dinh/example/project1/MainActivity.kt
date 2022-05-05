package dinh.example.project1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    companion object MainActivityData {
        var event1: String = ""
        var type1: String = ""
        var state1: String = ""
        var dateTime1 : String = ""
    }

    lateinit var editText: EditText
    lateinit var spinner: Spinner
    lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editTextActivity)
        spinner = findViewById(R.id.spinner)
        radioGroup = findViewById(R.id.radioGroup)

    }
    fun onClickSetting(view: View) {
        spinner.onItemSelectedListener = this

        TimeActivity.event = editText.text.toString()
        TimeActivity.type =  spinner.selectedItem.toString()


        var returnValue: String? = ""
        var checkRadio: Int =  radioGroup.checkedRadioButtonId
        when (checkRadio)
        {
            R.id.radioFinish -> returnValue = "Finish"
            R.id.radioPlan -> returnValue = "Plan"
            R.id.radioPending -> returnValue = "Pending"
        }
        TimeActivity.state = returnValue.toString()

        val intent: Intent = Intent(this, TimeActivity::class.java)
        startActivity(intent)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this, "Nothing Selected", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()

        getSharedPreferences("SharedPref", Context.MODE_PRIVATE).all.forEach{

            var data: String? = this.getSharedPreferences("SharedPref", Context.MODE_PRIVATE).getString(it.key.toString(),"")

            type1 = ((data!!.substringAfter("type=")).substringBefore(","))
            event1 = ((data.substringAfter("event=")).substringBefore(","))
            dateTime1 = ((data.substringAfter("dateTime=")).substringBefore(","))
            state1 = ((data.substringAfter("state=")).substringBefore(")"))
        }
        editText.setText(event1)
        checkIndexSpinner(type1)
        checkIndexRadio(state1)
    }

    fun checkIndexSpinner (type: String)
    {

        var returnvalue: Int = 0
        when (type)
        {
            "Physical-Outside" -> returnvalue = 0
            "Physical-Inside" -> returnvalue = 1
            "Memo" -> returnvalue = 2
        }
        spinner.setSelection(returnvalue)
    }
    fun checkIndexRadio (State: String)
    {
        when (State)
        {
            "Finish" -> radioGroup.check(R.id.radioFinish)
            "Pending" -> radioGroup.check(R.id.radioPending)
            "Plan" -> radioGroup.check(R.id.radioPlan)
        }
    }

    fun onClickrecorded(view: View) {

        val intent: Intent = Intent(this, RenderActivity::class.java)
        startActivity(intent)
    }

}