package com.example.imc.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.imc.R
import com.example.imc.utils.calcularIdade
import com.example.imc.utils.convertBase64ToBitmap
import com.example.imc.utils.getDataAtualBrasil
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PesagemActivity : AppCompatActivity() {

    lateinit var ivFotoPerfil: ImageView
    lateinit var etNovoPeso: EditText
    lateinit var buttonSalvar: Button
    lateinit var spinnerNiveis: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesagem)

        supportActionBar!!.hide()

        ivFotoPerfil = findViewById(R.id.iv_pesagem_foto)
        etNovoPeso = findViewById(R.id.et_novo_peso)
        buttonSalvar = findViewById(R.id.button_salvar)
        spinnerNiveis = findViewById(R.id.spinner_niveis)



        val tvData = findViewById<TextView>(R.id.et_data)
        tvData.text = getDataAtualBrasil()

        carregarFotoPerfil()

        buttonSalvar.setOnClickListener {

            val arquivo = getSharedPreferences("usuario", MODE_PRIVATE)
            val pesagem = arquivo.getString("pesagem","")
            val dataPesagem = arquivo.getString("data_pesagem","")
            val nivel = arquivo.getString("nivel","")

            val editor = arquivo.edit()
            editor.putString("pesagem", "$pesagem;${etNovoPeso.text.toString()}")
            editor.putString("data_pesagem", "$dataPesagem;${LocalDate.now().toString()}")
            editor.putString("nivel", "$nivel;${spinnerNiveis.selectedItemPosition.toString()}")
            editor.apply()

            Toast.makeText(this, "Peso registrado com sucesso!", Toast.LENGTH_SHORT).show()

        }

    }

    private fun carregarFotoPerfil () {

        val arquivo = getSharedPreferences("usuario", Context.MODE_PRIVATE)

        val bitmap = convertBase64ToBitmap(arquivo.getString("fotoPerfil", "")!!)
        ivFotoPerfil.setImageBitmap(bitmap)

    }



}