package com.hassanhamdy.va_task

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.hassanhamdy.va_task.databinding.ActivityMainBinding
import com.hassanhamdy.va_task.model.DelayType
import com.hassanhamdy.va_task.model.MathEquationModel
import com.hassanhamdy.va_task.model.OperationType
import com.hassanhamdy.va_task.utils.EquationAdapter
import com.hassanhamdy.va_task.utils.SharedPref
import com.hassanhamdy.va_task.utils.Util

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var operations = OperationType.values().map { it.type }
    var delay = DelayType.values().map { it.type }
    var num1: Double = 0.0
    var num2: Double = 0.0
    var delayTime: Int = 0
    var delayType = ""
    var operationType = ""
    var pendingList: ArrayList<MathEquationModel> = ArrayList()
    var servedList: ArrayList<MathEquationModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        SharedPref.instance.initSharedPref(this)

        fillPendingAndServedLists()
        preparePendingRecycler()
        prepareServingRecycler()
        initSpinners()

        binding.btnClick.setOnClickListener {
            handleClickListener()
        }
    }

    private fun handleClickListener() {
        if (binding.etNum1.text.toString().isEmpty()) {
            binding.etNum1.error = "Enter number 1"
        } else if (binding.etNum2.text.toString().isEmpty()) {
            binding.etNum2.error = "Enter number 2"
        } else if (binding.etDelayTime.text.toString().isEmpty()) {
            binding.etDelayTime.error = "Enter DelayTime"
        } else {
            showProgress(true)
            num1 = binding.etNum1.text.toString().toDouble()
            num2 = binding.etNum2.text.toString().toDouble()
            delayTime = binding.etDelayTime.text.toString().toInt()
            pendingList.add(
                MathEquationModel(
                    id = (pendingList.size + 1),
                    num1 = num1,
                    num2 = num2,
                    operation = operationType,
                    timeInMillis = Util().adjustAlarm(
                        delayTime,
                        DelayType.valueOf(delayType)
                    ).timeInMillis
                )
            )
            val scheduleItem = Util().getMinMillis(pendingList)
            if (scheduleItem != null) {
                Util().startAlarm(scheduleItem, this)
                SharedPref.instance.putPendingListJson(Gson().toJson(pendingList))
                preparePendingRecycler()
            }
            showProgress(false)
        }
    }

    private fun fillPendingAndServedLists() {
        //get pending lists from sharedPref
        val pendingJson = SharedPref.instance.getPendingListJson()
        if (pendingJson != "") {
            pendingList.clear()
            pendingList.addAll(
                Gson().fromJson(pendingJson, Array<MathEquationModel>::class.java).asList()
            )
        }


        //get served lists from sharedPref
        val servedJson = SharedPref.instance.getServedListJson()
        if (servedJson != "") {
            servedList.clear()
            servedList.addAll(
                Gson().fromJson(servedJson, Array<MathEquationModel>::class.java).asList()
            )
        }
    }

    private fun initSpinners() {
        // operation spinner
        val operationAdapter = ArrayAdapter(
            this,
            R.layout.item_drop_down, operations
        )
        binding.spOperation.adapter = operationAdapter
        binding.spOperation.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                operationType = operations[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


        // delay spinner
        val delayAdapter = ArrayAdapter(
            this,
            R.layout.item_drop_down, delay
        )
        binding.spDelayTime.adapter = delayAdapter
        binding.spDelayTime.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                delayType = delay[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun prepareServingRecycler() {
        // recyclerview
        if (servedList.isNotEmpty()) {
            toggleServedRCV(true)
            binding.rcvServed.layoutManager = LinearLayoutManager(this)
            binding.rcvServed.adapter = EquationAdapter(servedList, this)
        } else {
            toggleServedRCV(false)
        }
    }

    private fun preparePendingRecycler() {
        // recyclerview
        if (pendingList.isNotEmpty()) {
            togglePendingRCV(true)
            binding.rcvPending.layoutManager = LinearLayoutManager(this)
            binding.rcvPending.adapter = EquationAdapter(pendingList, this)
        } else {
            togglePendingRCV(false)
        }
    }

    private fun togglePendingRCV(show: Boolean) {
        if (show) {
            binding.rcvPending.visibility = View.VISIBLE
            binding.txtNoPending.visibility = View.INVISIBLE
        } else {
            binding.rcvPending.visibility = View.INVISIBLE
            binding.txtNoPending.visibility = View.VISIBLE
        }
    }

    private fun toggleServedRCV(show: Boolean) {
        if (show) {
            binding.rcvServed.visibility = View.VISIBLE
            binding.txtNoServed.visibility = View.INVISIBLE
        } else {
            binding.rcvServed.visibility = View.INVISIBLE
            binding.txtNoServed.visibility = View.VISIBLE
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.btnClick.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnClick.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

}