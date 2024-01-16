package com.sunayanpradhan.androidcharts

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity2 : AppCompatActivity() {


    lateinit var goPieChart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var salario = intent.getFloatExtra("IntSalario", 0f)
        var limite = intent.getFloatExtra("IntLimite", 0f)
        var limitemudado = intent.getBooleanExtra("limitemudado", false)
        val totallimite = findViewById<View>(R.id.totallimite) as TextView
        val atuallimite = findViewById<View>(R.id.atuallimite) as TextView
        val infogasto = findViewById<View>(R.id.info1) as TextView
        val infomudalimite = findViewById<View>(R.id.info2) as TextView
        var TextWrittenMudaLimite = ""
        var TextWrittenSugestao = "Seu gasto maximo sugerido é de " + salario / 2
        infogasto.text = TextWrittenSugestao.toString()
        if (limitemudado == true) {
            TextWrittenMudaLimite =
                "Seu limite é maior que seu salario, logo foi refatorado para caber no seu bolso"
        }
        var custo = 0f
        var pessoa = ""
        var gastos: HashMap<Float, String> = HashMap<Float, String>()
        val fab: View = findViewById(R.id.fab)
        val inputgasto = findViewById<View>(R.id.addvalor) as EditText
        val inputpessoa = findViewById<View>(R.id.addpessoa) as EditText
        //Teste spinner
        fab.setOnClickListener { view ->
            custo = inputgasto.text.toString().toFloat()
            pessoa = inputpessoa.text.toString()
            gastos[custo] = pessoa
            atuallimite.text = gastos.toString() //TEMP, mudar para limite apos contas
        }
        infomudalimite.text = TextWrittenMudaLimite.toString()
        totallimite.text = limite.toString()
        atuallimite.text = gastos.toString() //TEMP, mudar para limite apos contas
        goPieChart = findViewById(R.id.go_pie_chart)
        goPieChart.setOnClickListener() {
            val intent = Intent(this, PieChartActivity::class.java)
            startActivity(intent)


        }

    }

    private fun addValor(): HashMap<Float, String> {
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        var custo = 0f
        var pessoa = ""
        val dialogLayout = inflater.inflate(R.layout.addvalor_layout, null)
        val inputgasto = dialogLayout.findViewById<View>(R.id.addvalor) as EditText
        val inputpessoa = dialogLayout.findViewById<View>(R.id.addpessoa) as EditText
        with(builder) {
            setTitle("")
            setMessage("Deseja adicionar valores gastos no cartão?")
            setPositiveButton("Adicionar") { dialog, which ->
                custo = inputgasto.text.toString().toFloat()
                pessoa = inputpessoa.text.toString()
            }

            setNegativeButton("Cancelar") { dialog, which -> dialog.dismiss() }

            // User cancelled the dialog
            var gastos : HashMap<Float, String> = HashMap<Float, String>()
            gastos[custo] = pessoa
            // Create the AlertDialog object and return it
            val addDialog: AlertDialog = builder.create()
            addDialog.show()
            return gastos

        }
    }
}



