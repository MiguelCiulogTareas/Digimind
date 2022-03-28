package ciulog.miguel.digimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ciulog.miguel.digimind.AdaptadorTareas
import ciulog.miguel.digimind.databinding.FragmentHomeBinding
import ciulog.miguel.digimind.ui.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    companion object{
        var tasks: ArrayList<Task> = ArrayList<Task>()
//        var first = true
        lateinit var adaptador: AdaptadorTareas
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val gridView: GridView = binding.gridview

//        if(first){
            readJson()
//            first = false
//        }

        adaptador = AdaptadorTareas(root.context, tasks)

        gridView.adapter = adaptador

        return root
    }

    fun fill_tasks(){
        tasks.add(Task("Tarea 1", "Lunes", "15:00"))
        tasks.add(Task("Tarea 2", "Martes", "16:00"))
        tasks.add(Task("Tarea 3", "Miercoles", "17:00"))
        tasks.add(Task("Tarea 4", "Jueves", "18:00"))
        tasks.add(Task("Tarea 5", "Viernes", "19:00"))

    }

    fun readJson(){
        val preferences = context?.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val gson = Gson()
        tasks = ArrayList<Task>()

        var json = preferences?.getString("tareas", null)
        val type = object :
            TypeToken<ArrayList<Task?>?>() {}.type
        if(json != null){
            tasks = gson.fromJson(json, type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}