package ciulog.miguel.digimind

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import ciulog.miguel.digimind.ui.Task
import ciulog.miguel.digimind.ui.home.HomeFragment
import com.google.gson.Gson

class AdaptadorTareas: BaseAdapter {
    lateinit var context: Context
    var tasks: ArrayList<Task> = ArrayList()

    constructor(context: Context, tasks: ArrayList<Task>){
        this.context = context
        this.tasks = tasks
    }

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(p0: Int): Any {
       return  tasks[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.task_view, null)

        var task = tasks[p0]
        val tv_titulo: TextView = vista.findViewById(R.id.tv_title)
        val tv_tiempo: TextView = vista.findViewById(R.id.tv_time)
        val tv_dia: TextView = vista.findViewById(R.id.tv_days)

        tv_tiempo.setText(task.time)
        tv_titulo.setText(task.title)
        tv_dia.setText(task.day)

        vista.setOnClickListener{
            delete(task)
        }

        return vista
    }

    fun delete(task: Task){
        val alertDialog: AlertDialog? = context?.let{
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok_button,
                    DialogInterface.OnClickListener { dialog, id ->
                        HomeFragment.tasks.remove(task)
                        guardarJson()
                        HomeFragment.adaptador.notifyDataSetChanged()
                        Toast.makeText(context, R.string.msg_deleted, Toast.LENGTH_SHORT).show()
                    })
                setNegativeButton(R.string.cancel_button,
                    DialogInterface.OnClickListener { dialog, id -> }
                )
            }
            builder?.setMessage(R.string.msg)
                .setTitle(R.string.title)
            builder.create()
        }
        alertDialog?.show()
    }

    fun guardarJson(){
        val preferences = context?.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        val gson = Gson()

        var json = gson.toJson(HomeFragment.tasks)
        editor?.putString("tareas", json)
        editor?.apply()
    }

}