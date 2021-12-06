package com.example.imc.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.imc.R
import com.example.imc.repository.PesagemRepository
import com.example.imc.utils.calcularIdade
import com.example.imc.utils.convertBase64ToBitmap
import kotlin.math.log

class DashBoardActivity : AppCompatActivity() {

    lateinit var tvNome: TextView
    lateinit var tvProfissao: TextView
    lateinit var tvImc: TextView
    lateinit var tvPeso: TextView
    lateinit var tvIdade: TextView
    lateinit var tvAltura: TextView
    lateinit var ivFotoPerfil: ImageView
    lateinit var cvPesar: CardView
    lateinit var cvHistorico: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvNome = findViewById(R.id.tv_nome)
        tvProfissao = findViewById(R.id.tv_profissao)
        tvImc = findViewById(R.id.tv_imc)
        tvPeso = findViewById(R.id.tv_peso)
        tvIdade = findViewById(R.id.tv_dash_idade)
        tvAltura = findViewById(R.id.tv_altura)
        ivFotoPerfil = findViewById(R.id.iv_dashboard_foto)
        cvPesar = findViewById(R.id.cv_pesar)
        cvHistorico = findViewById(R.id.cv_historico)

        carregarDashboard()

        cvPesar.setOnClickListener {
            val intent = Intent(this, PesagemActivity::class.java)
            startActivity(intent)
        }

        cvHistorico.setOnClickListener {

            val intent = Intent(this, PesagemRepository::class.java)
            startActivity(intent)

//            val pesagemRepository = PesagemRepository(this)
//            val listaPesagem = pesagemRepository.getListaPesagem()
//
//            for (p in listaPesagem){
//                Log.i("xpto", "${p.dataPesagem} - ${p.peso}")
//            }

        }


    }

    private fun carregarDashboard () {

        val arquivo = getSharedPreferences("usuario", Context.MODE_PRIVATE)

        tvNome.text = arquivo.getString("nome", "")
        tvProfissao.text = arquivo.getString("profissao", "")
        tvAltura.text = arquivo.getFloat("altura", 0.0f).toString()
        tvIdade.text = calcularIdade(arquivo.getString("dataNascimento", "")!!).toString()

        val bitmap = convertBase64ToBitmap(arquivo.getString("fotoPerfil", "")!!)
        ivFotoPerfil.setImageBitmap(bitmap)

    }



}