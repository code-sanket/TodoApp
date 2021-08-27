package code.sanky.todoapp

import android.app.*
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.room.Room
import code.sanky.todoapp.databinding.ActivityTaskBinding
import com.example.todoapp.TodoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val DB_NAME = "todo.db"

class TaskActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityTaskBinding
    lateinit var myCalendar: Calendar
    lateinit var dateSetListener : DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener : TimePickerDialog.OnTimeSetListener

    var finalDate = 0L
    var finalTime = 0L

    private val label = arrayListOf("Personal" , "Business" , "Insurance" , "General")

    val db by lazy {
        AppDatabase.getDatabase(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTaskBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.dateEdt.setOnClickListener(this)
        binding.timeEdt.setOnClickListener(this)
        binding.saveBtn.setOnClickListener(this)
        setUpSpinner()
    }

    private fun setUpSpinner() {
        val adapter = ArrayAdapter(this , android.R.layout.simple_spinner_dropdown_item , label)
        label.sort()
        binding.spinnerCategory.adapter = adapter
    }

    override fun onClick(p0: View?) {
       when(p0!!.id){
            R.id.dateEdt ->{
                setListner()
            }
           R.id.timeEdt ->{
               setTimeListner()
           }
           R.id.saveBtn ->{
               saveTodo()
           }
        }
    }

    private fun saveTodo() {
        val category = binding.spinnerCategory.selectedItem.toString()
        val title = binding.titleInpLay.editText?.text.toString()
        val description = binding.taskInpLay.editText?.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
            val id = withContext(Dispatchers.IO) {
                return@withContext db.todoDao().insertTask(
                    TodoModel(
                        title,
                        description,
                        category,
                        finalDate,
                        finalTime
                    )
                )
            }
            finish()
        }
    }

    private fun setTimeListner() {
        myCalendar = Calendar.getInstance()
        timeSetListener = TimePickerDialog.OnTimeSetListener() { _: TimePicker, hoursOfDay : Int, min : Int->
            myCalendar.set(Calendar.HOUR_OF_DAY , hoursOfDay)
            myCalendar.set(Calendar.MINUTE , min)
            updateTime()
        }

        val timePickerDialog = TimePickerDialog(
            this , timeSetListener , myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE) , false
        )
        timePickerDialog.show()
    }

    private fun updateTime() {
        val myFormat = "h:mm a"
        val sdf = SimpleDateFormat(myFormat)
        finalTime = myCalendar.time.time
        binding.timeEdt.setText(sdf.format(myCalendar.time))

    }

    private fun setListner() {
        myCalendar = Calendar.getInstance()
        dateSetListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year : Int, month : Int, dayOfMonth : Int ->
            myCalendar.set(Calendar.YEAR , year)
            myCalendar.set(Calendar.MONTH , month)
            myCalendar.set(Calendar.DAY_OF_MONTH , dayOfMonth)
            updateDate()
        }

        val datePickerDialog = DatePickerDialog(
            this , dateSetListener , myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH) , myCalendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        val myFormat = "EEE , d MMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        finalDate = myCalendar.time.time
        binding.dateEdt.setText(sdf.format(myCalendar.time))
        binding.timeInptLay.visibility = View.VISIBLE
    }

}