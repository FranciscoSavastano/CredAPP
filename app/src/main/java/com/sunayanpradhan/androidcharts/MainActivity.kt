package com.sunayanpradhan.androidcharts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    lateinit var goPieChart:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goPieChart=findViewById(R.id.go_pie_chart)
        goPieChart.setOnClickListener() {


            val TextLimite = findViewById<View>(R.id.entrylimite) as EditText
            val TextSalario = findViewById<View>(R.id.entrysalario) as EditText
            val IntLimite = TextLimite.text.toString().toFloat()
            val IntSalario = TextSalario.text.toString().toFloat()
            
            val textView = findViewById<View>(R.id.testeview) as TextView
            val TextWritten = TextLimite.text.toString().toFloat()
            val intent = Intent(this,MainActivity2::class.java)
            textView.text = TextWritten.toString()
            intent.putExtra("IntLimite", IntLimite)
            intent.putExtra("IntSalario", IntSalario)
            startActivity(intent)


        }

    }
}