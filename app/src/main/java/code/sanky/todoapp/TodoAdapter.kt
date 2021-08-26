package code.sanky.todoapp

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.TodoModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TodoAdapter(val list: ArrayList<TodoModel>, val context: Context) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    inner class TodoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var txtShowTitle : TextView = itemView.findViewById(R.id.txtShowTitle)
        var txtShowTask : TextView = itemView.findViewById(R.id.txtShowTask)
        var txtShowCategory : TextView = itemView.findViewById(R.id.txtShowCategory)
        var txtShowDate : TextView = itemView.findViewById(R.id.txtShowDate)
        var viewColorTag : View = itemView.findViewById(R.id.viewColorTag)
        var txtShowTime : TextView = itemView.findViewById(R.id.txtShowTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo , parent , false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        holder.viewColorTag.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.txtShowCategory.text = list[position].category
        holder.txtShowTask.text = list[position].description
        holder.txtShowTitle.text = list[position].title
        holder.txtShowTime.text = updateTime(list[position].time)
        holder.txtShowDate.text = updateDate(list[position].date)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FullTask::class.java)
            intent.putExtra("TITLE" , list[position].title)
            intent.putExtra("DESC" , list[position].description)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    private fun updateTime(time: Long) : String {
        //Mon, 5 Jan 2020
        val myformat = "h:mm a"
        val sdf = SimpleDateFormat(myformat)
       val time = sdf.format(Date(time))
        return time

    }
    private fun updateDate(time: Long): String {
        //Mon, 5 Jan 2020
        val myformat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myformat)
        val date = sdf.format(Date(time))
        return date
    }
}