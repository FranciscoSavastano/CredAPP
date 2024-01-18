package com.sunayanpradhan.androidcharts

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.io.File
import java.io.FileWriter

class PieChartActivity : AppCompatActivity() {

    lateinit var pieChart:PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)
        pieChart=findViewById(R.id.pie_chart)
        //Limite gasto não pode ultrapassar 50% do seu salario logo divir o salario por 2 para configurar novo limite
        //Se limite for maior que 50% resetar para este, se não for continuar com limite inserido


        val textsugestao = findViewById<View>(R.id.textlimitesugestao) as TextView
        val textmudalimite = findViewById<View>(R.id.textmudalimite) as TextView
        val intent = Intent(this,PieChartActivity::class.java)
        val list:ArrayList<PieEntry> = ArrayList()
        val dir = this.getDir("GastosAPP", Context.MODE_PRIVATE);
        val fileDir = dir.toString() + "/" + "gastos.txt"
        val file = File(fileDir)
        Log.d("LEITURA", "${file.readLines()}")
        Log.d("File", "$fileDir, no dir $dir , com nome $file")
        file.forEachLine { line ->
            val parts = line.split(" ")
            if (parts.size >= 2) {
                val string1 = parts[0]
                val string2 = parts[1]
                list.add(PieEntry(string1.toFloat(), string2))
            }
        }
        val pieDataSet= PieDataSet(list,"Gastos")

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        pieDataSet.valueTextColor= Color.BLACK
        pieDataSet.valueTextSize=15f

        val pieData= PieData(pieDataSet)

        pieChart.data= pieData

        pieChart.description.text= "Pie Chart"

        pieChart.centerText="Gastos"

        pieChart.animateY(2000)




    }
}
