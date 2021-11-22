package com.example.imc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.imc.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PesagemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesagem)

        supportActionBar!!.hide()

        val tvData = findViewById<TextView>(R.id.et_data)

        val dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        tvData.text = dataAtual.toString()

    }
}