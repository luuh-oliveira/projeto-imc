package com.example.imc.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import com.example.imc.R
import com.example.imc.model.Usuario
import com.example.imc.utils.convertBitmapToBase64
import com.example.imc.utils.convertStringToLocalDate
import java.time.LocalDate
import java.util.*

const val CODE_IMAGE = 100

class NovoUsuarioActivity : AppCompatActivity() {

    lateinit var editEmail: EditText
    lateinit var editSenha: EditText
    lateinit var editNome: EditText
    lateinit var editAltura: EditText
    lateinit var editDataNascimento: EditText
    lateinit var editProfissao: EditText
    lateinit var radioF: RadioButton
    lateinit var radioM: RadioButton
    lateinit var tvTrocarFoto: TextView
    lateinit var ivFotoPerfil: ImageView
    lateinit var imageBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_usuario)

        editEmail = findViewById(R.id.edit_email)
        editSenha = findViewById(R.id.edit_senha)
        editNome = findViewById(R.id.edit_nome)
        editAltura = findViewById(R.id.edit_altura)
        editDataNascimento = findViewById(R.id.edit_data_nascimento)
        editProfissao = findViewById(R.id.edit_profissao)
        radioF = findViewById(R.id.radio_feminino)
        radioM = findViewById(R.id.radio_masculino)
        tvTrocarFoto = findViewById(R.id.tv_trocar_foto)
        ivFotoPerfil = findViewById(R.id.iv_foto_perfil)

        //carregar bitmap padrão caso o usuário não escolha uma foto
        //em caso de foto:
        imageBitmap = BitmapFactory.decodeResource(resources, R.drawable.perfil)
        //em caso de vector asset
//        imageBitmap = resources.getDrawable(R.drawable.person_24).toBitmap()

        supportActionBar!!.title = "Novo usuário"

        // Abrir a galeria de fotos para escolher uma foto para o perfil
        tvTrocarFoto.setOnClickListener {
            abrirGaleria()
        }

        // Criar o calendário
        val calendario = Calendar.getInstance()
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        editDataNascimento.setOnClickListener {
            val dpd = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, _ano, _mes, _dia ->

                        var diaFinal = _dia
                        var mesFinal = _mes + 1

                        var mesString = "$mesFinal"
                        var diaString = "$diaFinal"

                        if (mesFinal < 10) {
                            mesString = "0$mesFinal"
                        }

                        if (diaFinal < 10) {
                            diaString = "0$diaFinal"
                        }

                        Log.i("xpto", _dia.toString())
                        Log.i("xpto", _mes.toString())

                        editDataNascimento.setText("$diaString/$mesString/$_ano")
                    }, ano, mes, dia
            )
            dpd.show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imagem: Intent?) {
        super.onActivityResult(requestCode, resultCode, imagem)

        if (requestCode == CODE_IMAGE && resultCode == -1){
            // Recuperar a imagem do stream
            val fluxoImagem = contentResolver.openInputStream(imagem!!.data!!)

            // Converter os bits em um bitmap
            imageBitmap = BitmapFactory.decodeStream(fluxoImagem)

            // Colocar o bitmap no ImageView
            ivFotoPerfil.setImageBitmap(imageBitmap)

        }

    }

    private fun abrirGaleria() {

        // Abrir a galeria de imagens do dispositivo
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        // Abrir a Activity responsável por exibir as imagens
        // Esta Activity retornará o conteúdo selecionado
        // para o nosso app
        startActivityForResult(
                Intent.createChooser(intent,
                        "Escolha uma foto"),
                CODE_IMAGE
        )

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_novo_usuario, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (validarCampos()) {
            // Criar o objeto usuario
            val nascimento = convertStringToLocalDate(editDataNascimento.text.toString())

            val usuario = Usuario(
                    1,
                    editNome.text.toString(),
                    editEmail.text.toString(),
                    editSenha.text.toString(),
                    0,
                    editAltura.text.toString().toDouble(),
                    LocalDate.of(
                            nascimento.year,
                            nascimento.monthValue,
                            nascimento.dayOfMonth
                    ),
                    editProfissao.text.toString(),
                    if (radioF.isChecked) 'F' else 'M',
                    convertBitmapToBase64(imageBitmap!!)

            )


            // Salvar o registro
            // Em um SharedPreferences

            // A instrução abaixo irá criar um
            // arquivo SharedPreferences se não existir
            // Se existir ele será aberto para edição
            val dados = getSharedPreferences(
                    "usuario", Context.MODE_PRIVATE)

            // Vamos criar o objeto que permitirá a
            // edição dos dados do arquivo SharedPreferences
            val editor = dados.edit()
            editor.putInt("id", usuario.id)
            editor.putString("nome", usuario.nome)
            editor.putString("email", usuario.email)
            editor.putString("senha", usuario.senha)
            editor.putInt("peso", usuario.peso)
            editor.putFloat("altura", usuario.altura.toFloat())
            editor.putString("dataNascimento", usuario.dataNascimento.toString())
            editor.putString("profissao", usuario.profissao)
            editor.putString("sexo", usuario.sexo.toString())
            editor.putString("fotoPerfil", usuario.fotoPerfil)
            editor.apply()

        }

        Toast.makeText(this, "Usuário cadastrado!!", Toast.LENGTH_SHORT).show()

        return true
    }

    fun validarCampos(): Boolean {
        var valido = true

        if (editEmail.text.isEmpty()) {
            editEmail.error = "E-mail é obrigatório!"
            valido = false
        }

        if (editSenha.text.isEmpty()) {
            editSenha.error = "Senha é obrigatório!"
            valido = false
        }

        return valido
    }
}