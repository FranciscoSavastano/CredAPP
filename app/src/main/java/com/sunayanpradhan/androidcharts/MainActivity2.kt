package com.sunayanpradhan.androidcharts

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieEntry
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Paths


class MainActivity2 : AppCompatActivity() {


    lateinit var goPieChart: Button
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        //Verificação dos arquivos
        //Arquivo de inicio
        val dir = this.getDir("GastosAPP", Context.MODE_APPEND);
        val fileNamestart = "infostart.txt"
        val fileDirstart = dir.toString() + "/" + "infostart.txt"
        val infostartfile = File(fileDirstart)

        if (!infostartfile.exists()) {
            infostartfile.createNewFile()
            Log.d("Criado","File '$fileNamestart' created.")

        } else {
            println("File '$fileNamestart' already exists.")
            val text = infostartfile.readText()
            println("File content: $text")
            Log.d("Existe","File '$fileNamestart' Ja existe.")
        }
        //Arquivo de gastos
        val fileName = "gastos.txt"
        val fileDir = dir.toString() + "/" + "gastos.txt"
        val file = File(fileDir)

        if (!file.exists()) {
            file.createNewFile()
            Log.d("Criado","File '$fileName' created.")

        } else {
            println("File '$fileName' already exists.")
            val text = file.readText()
            println("File content: $text")
            Log.d("Existe","File '$fileName' Ja existe.")

        }

        val writer = FileWriter(file)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //Declaração e inicialização de variaveis
        var salario = intent.getFloatExtra("IntSalario", 0f)
        var limite = intent.getFloatExtra("IntLimite", 0f)
        var limitemudado = intent.getBooleanExtra("limitemudado", false)
        infostartfile.forEachLine { line ->
            val parts = line.split(" ")
            if (parts.size >= 3) {
                limite = parts[0].toFloat()
                salario = parts[1].toFloat()
                limitemudado = parts[2].toBoolean()

            }
        }
        val totallimite = findViewById<View>(R.id.totallimite) as TextView
        val atuallimite = findViewById<View>(R.id.atuallimite) as TextView
        val infogasto = findViewById<View>(R.id.info1) as TextView
        val infomudalimite = findViewById<View>(R.id.info2) as TextView
        var custo = 0f
        var writestring = ""
        var pessoa = ""
        var gastos: HashMap<String, Float> = HashMap<String, Float>()
        val fab: View = findViewById(R.id.fab)
        val inputgasto = findViewById<View>(R.id.addvalor) as EditText
        var suggestions = mutableListOf<String>()
        val adaptersugestion = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, suggestions)
        val inputpessoa = findViewById<View>(R.id.addpessoa) as AutoCompleteTextView
        //Texto do menu principal
        var TextWrittenMudaLimite = ""
        var TextWrittenSugestao = "Seu gasto maximo sugerido é de " + salario / 2
        infogasto.text = TextWrittenSugestao.toString()
        if (limitemudado) {
            TextWrittenMudaLimite =
                "Seu limite é maior que seu salario, logo foi refatorado para caber no seu bolso"
        }
 //Seta o texto de a cordo com as Strings passadas
        infomudalimite.text = TextWrittenMudaLimite.toString()
        totallimite.text = limite.toString()
        atuallimite.text = limite.toString() //TEMP, mudar para limite apos contas
        var limiteat = limite
        //Botão "+" para adicionar gastos na hashmap "gastos"
        inputpessoa.setAdapter(adaptersugestion)
        fab.setOnClickListener { view ->
            custo = inputgasto.text.toString().toFloat()
            pessoa = inputpessoa.text.toString().replace(" ", "")
            if(custo > limiteat){
                var response = false
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Atenção!")
                builder.setMessage("O valor inserido é maior que a quantidade de limite restante, se continuar seu limite ficara negativo!")
                    .setPositiveButton("Continuar",
                        DialogInterface.OnClickListener { dialog, id ->
                            response = true
                            gastos = addvalor(custo,atuallimite, gastos, inputpessoa)
                            suggestions.add(pessoa)
                            limiteat = limite - custo
                            atuallimite.text = limiteat.toString()

                        })
                    .setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, id ->
                            response = false
                        })
                val alert = builder.create()
                alert.show()
                if(response == false){
                    return@setOnClickListener
                }
            }
            val pw = PrintWriter(FileWriter(file, true))
            gastos = addvalor(custo,atuallimite, gastos, inputpessoa)
            suggestions.add(pessoa)
            limiteat = limite - custo
            atuallimite.text = limiteat.toString()
            writestring = custo.toString() +" "+ pessoa
            pw.use{
                out->
                out.println(writestring)
            }

            Log.d("Escrito","Linha '$writestring' Escrita em $fileDir.")

        }
        adaptersugestion.notifyDataSetChanged()

        goPieChart = findViewById(R.id.go_pie_chart)
        goPieChart.setOnClickListener() {
            val intent = Intent(this, PieChartActivity::class.java)
            intent.putExtra("file", file)
            intent.putExtra("fileName", fileName)
            intent.putExtra("fileDir", fileDir)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun addvalor(custo: Float, atuallimite: TextView, gastos: HashMap<String, Float>, inputpessoa: EditText): HashMap<String, Float> {
        val pessoa = inputpessoa.text.toString().replace(" ", "")
        if (custo != 0f) {
            if (pessoa != "") {
                //gastos[pessoa] = custo
                val currentValue = gastos.getOrDefault(pessoa, 0f)
                if(gastos.isEmpty()){
                    gastos.put(pessoa,custo)
                }
                else{
                    try {
                        val keys = gastos.filterKeys{it == pessoa}.keys.first()
                        if (keys == pessoa){
                            val temp = currentValue + custo
                            gastos.put(pessoa, temp)
                        }
                    } catch (e: NoSuchElementException) {
                        gastos.put(pessoa,custo)
                    }
                }
                //atuallimite.text = gastos.toString() //TEMP, mudar para limite apos contas


            }
        }
    return gastos
    }
    fun showAlert(context: Context): Boolean {
        var response = false
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Mensagem de Alerta")
            .setPositiveButton("Continuar",
                DialogInterface.OnClickListener { dialog, id ->
                    response = true
                })
            .setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialog, id ->
                    response = false
                })
        val alert = builder.create()
        alert.show()
        return response
    }
}




