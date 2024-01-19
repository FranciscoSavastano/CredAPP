package com.sunayanpradhan.androidcharts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter


class MainActivity : AppCompatActivity() {

    lateinit var goPieChart:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goPieChart=findViewById(R.id.go_pie_chart)
        val dir = this.getDir("GastosAPP", Context.MODE_APPEND);
        val intent = Intent(this,MainActivity2::class.java)
        val fileName = "infostart.txt"
        val fileDir = dir.toString() + "/" + "infostart.txt"
        val file = File(fileDir)

        if (!file.exists()) {
            file.createNewFile()
            Log.d("Criado","File '$fileName' created.")

        } else {
            println("File '$fileName' already exists.")
            val text = file.readText()
            println("File content: $text")
            Log.d("Existe","File '$fileName' Ja existe.")
            startActivity(intent)
        }
        goPieChart.setOnClickListener() {
            val intent = Intent(this,MainActivity2::class.java)
            val dir = this.getDir("GastosAPP", Context.MODE_APPEND);
            val TextLimite = findViewById<View>(R.id.entrylimite) as EditText
            val TextSalario = findViewById<View>(R.id.entrysalario) as EditText
            var IntLimite = TextLimite.text.toString().toFloat()
            var IntSalario = TextSalario.text.toString().toFloat()
            
            val textView = findViewById<View>(R.id.testeview) as TextView
            val TextWritten = TextLimite.text.toString().toFloat()
            var limitemudado: Boolean = false

            if(IntLimite > IntSalario){
                IntLimite = IntSalario / 2
                limitemudado = true
            }
            val pw = PrintWriter(FileWriter(file, true))
            textView.text = TextWritten.toString()
            intent.putExtra("IntLimite", IntLimite)
            intent.putExtra("IntSalario", IntSalario)
            intent.putExtra("limitemudado", limitemudado)

            val writestring = IntLimite.toString() + " " + IntSalario.toString() + " " + limitemudado.toString()
            pw.use{
                    out->
                out.println(writestring)
            }
            startActivity(intent)


        }

    }
}