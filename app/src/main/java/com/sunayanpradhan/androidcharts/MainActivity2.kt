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
import java.lang.Exception
import java.nio.file.Paths


class MainActivity2 : AppCompatActivity() {
    companion object {
        val pessoas : ArrayList<String> = ArrayList()
        val custos : ArrayList<Float> = ArrayList()
    }



    lateinit var goPieChart: Button

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        //Verificação dos arquivos
        //Arquivo de inicio
        var salario = intent.getFloatExtra("IntSalario", 0f)
        var limite = intent.getFloatExtra("IntLimite", 0f)
        var limitemudado = intent.getBooleanExtra("limitemudado", false)
        val dir = this.getDir("GastosAPP", Context.MODE_APPEND);
        val fileNamestart = "infostart.txt"
        val fileDirstart = dir.toString() + "/" + "infostart.txt"
        val infostartfile = File(fileDirstart)

        if (!infostartfile.exists()) {
            infostartfile.createNewFile()
            Log.d("Criado", "File '$fileNamestart' created.")

        } else {
            println("File '$fileNamestart' already exists.")
            val text = infostartfile.readText()
            println("File content: $text")
            Log.d("Existe", "File '$fileNamestart' Ja existe.")
        }

        infostartfile.forEachLine { line ->
            val parts = line.split(" ")
            if (parts.size >= 3) {
                limite = parts[0].toFloat()
                salario = parts[1].toFloat()
                limitemudado = parts[2].toBoolean()

            }
        }
        var limiteat = limite


        //Arquivo de gastos
        val fileName = "gastos.txt"
        val fileDir = dir.toString() + "/" + "gastos.txt"
        val file = File(fileDir)

        if (!file.exists()) {
            file.createNewFile()
            Log.d("Criado", "File '$fileName' created.")

        } else {
            println("File '$fileName' already exists.")
            val text = file.readText()
            println("File content: $text")
            Log.d("Existe", "File '$fileName' Ja existe.")
            Log.d("TEXTO", "Contem $text")
            file.forEachLine { line ->
                val parts = line.split(" ")
                if (parts.size >= 2) {
                    try{
                        val string1 = parts[0].toFloat()
                        Log.d("ANTIGOSAVE", "Subtraindo $string1 de $limite")
                        limiteat -= string1
                        Log.d("SUBTRAIDO", "result $limiteat")
                        Log.d("limiteat", "$limiteat")
                    }
                    catch(e:Exception){
                        Log.d("OPEN ERROR", "Erro tentando subtrair valor existente")
                    }
                }
            }

        }

        val writer = FileWriter(file)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //Declaração e inicialização de variaveis
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
        Log.d("limiteat", "$limiteat")
        if(limiteat == limite){
            atuallimite.text = limite.toString()
            Log.d("LIMITETXT", "$limiteat = $limite é igual ao inicial, sem mudanças")
        }
        else{
            atuallimite.text = limiteat.toString()
            Log.d("LIMITETXT", "$limiteat != $limite é diferente do inicial mudado")
        }
        var custo = 0f
        var writestring = ""
        var pessoa = ""
        var sessioncusto = ArrayList<String>()
        var gastos: HashMap<String, Float> = HashMap<String, Float>()
        val fab: View = findViewById(R.id.fab)
        val inputgasto = findViewById<View>(R.id.addvalor) as EditText
        var suggestions = mutableListOf<String>()
        val adaptersugestion =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, suggestions)
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
        
        //Botão "+" para adicionar gastos na hashmap "gastos"
        inputpessoa.setAdapter(adaptersugestion)
        fab.setOnClickListener { view ->
            custo = inputgasto.text.toString().toFloat()
            pessoa = inputpessoa.text.toString().replace(" ", "")
            if (custo > limiteat) {
                var response = false
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Atenção!")
                builder.setMessage("O valor inserido é maior que a quantidade de limite restante, se continuar seu limite ficara negativo!")
                    .setPositiveButton("Continuar",
                        DialogInterface.OnClickListener { dialog, id ->
                            response = true
                            val pw = PrintWriter(FileWriter(file, true))
                            suggestions.add(pessoa)
                            limiteat -= custo
                            atuallimite.text = limiteat.toString()
                            writestring = custo.toString() + " " + pessoa
                            sessioncusto.add(writestring)


                        })
                    .setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, id ->
                            response = false
                        })
                val alert = builder.create()
                alert.show()
                if (response == false) {
                    return@setOnClickListener
                }
            }
            val pw = PrintWriter(FileWriter(file, true))
            suggestions.add(pessoa)
            Log.d("SUB", "$limite menos $custo")
            limiteat -= custo
            Log.d("RESULTADO", "é igual a $limiteat")

            atuallimite.text = limiteat.toString()
            writestring = custo.toString() + " " + pessoa
            sessioncusto.add(writestring)
            pessoas.add(pessoa)
            custos.add(custo)
            Log.d("CUSTOS", "$custos, $pessoas")



            Log.d("Escrito", "Linha '$writestring' Escrita em $fileDir.")

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
//TODO PROCURAR FORMA PARA GUARDAR OS DADOS DE FORMA QUE NAO SEJAM APAGADOS APOS USUARIO FECHAR O APP
    protected override fun onPause() {
        super.onPause()
        val dir = this.getDir("GastosAPP", Context.MODE_APPEND);
        val fileName = "gastos.txt"
        var writestring = ""
        val fileDir = dir.toString() + "/" + "gastos.txt"
        val file = File(fileDir)
        try {
            var pw = PrintWriter(FileWriter(file, true))
            Log.d("SALVANDO", "Salvando $custos e $pessoas")
            for (i in custos.indices) {
                writestring = custos[i].toString() + " " + pessoas[i]
                Log.d("LOOPSAVE", writestring)
                pw.use { out ->
                    out.println(writestring)
                }
                pw = PrintWriter(FileWriter(file, true))
            }
            pw.close()
            Log.d("SAVE", "Arquivo salvo com sucesso")
        } catch (e: Exception) {
            val trace = e.printStackTrace()
            Log.d("ERRO FATAL SAVE", "$trace")
        }
    }
}



