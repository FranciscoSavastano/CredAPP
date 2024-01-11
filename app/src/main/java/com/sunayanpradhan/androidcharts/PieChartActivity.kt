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
        val salario = intent.getFloatExtra("IntSalario", 0f)
        var limite = intent.getFloatExtra("IntLimite", 0f)
        //Limite gasto não pode ultrapassar 50% do seu salario logo divir o salario por 2 para configurar novo limite
        //Se limite for maior que 50% resetar para este, se não for continuar com limite inserido


        val textsugestao = findViewById<View>(R.id.textlimitesugestao) as TextView
        val textmudalimite = findViewById<View>(R.id.textmudalimite) as TextView
        val TextWrittenSugestao = "Seu gasto maximo sugerido é de " + salario / 2
        val TextWrittenMudaLimite = "Seu limite é maior que seu salario, logo foi refatorado para caber no seu bolso"
        val intent = Intent(this,PieChartActivity::class.java)
        if(limite > salario){
            limite = salario / 2
            textmudalimite.text = TextWrittenMudaLimite.toString()
        }
        textsugestao.text = TextWrittenSugestao.toString()

        val list:ArrayList<PieEntry> = ArrayList()

        list.add(PieEntry(salario,"Salario"))
        list.add(PieEntry(limite,"Limite"))
        list.add(PieEntry(102f,"102"))
        list.add(PieEntry(103f,"103"))
        list.add(PieEntry(104f,"104"))

        val pieDataSet= PieDataSet(list,"List")

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