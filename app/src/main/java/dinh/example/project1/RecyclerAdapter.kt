package dinh.example.project1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(private val dataSet: MutableList<Information>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textView1: TextView
        val textView2: TextView
        val textView3: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.textViewInformation)
            textView1 = view.findViewById(R.id.textViewDateTime)
            textView2 = view.findViewById(R.id.textViewState)
            textView3 = view.findViewById(R.id.textViewType)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_view, viewGroup, false)

        val lp = view.getLayoutParams()
        lp.height = viewGroup.measuredHeight/5
        view.setLayoutParams(lp)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView3.text = dataSet[position].type.toString()
        viewHolder.textView.text = dataSet[position].event.toString()
        viewHolder.textView1.text = dataSet[position].dateTime.toString()
        viewHolder.textView2.text = dataSet[position].state.toString()
    }

    override fun getItemCount() = dataSet.size

}
