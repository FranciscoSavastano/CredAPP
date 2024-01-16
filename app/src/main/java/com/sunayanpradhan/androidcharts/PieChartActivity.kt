package com.sunayanpradhan.androidcharts

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

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

        list.add(PieEntry(100f,"100"))
        list.add(PieEntry(101f,"101"))
        list.add(PieEntry(102f,"102"))
        list.add(PieEntry(103f,"103"))
        list.add(PieEntry(104f,"104"))

        val pieDataSet= PieDataSet(list,"Gastos")

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        pieDataSet.valueTextColor= Color.BLACK
        pieDataSet.valueTextSize=15f

        val pieData= PieData(pieDataSet)

        pieChart.data= pieData

        pieChart.description.text= "Pie Chart"

        pieChart.centerText="List"

        pieChart.animateY(2000)




    }
}