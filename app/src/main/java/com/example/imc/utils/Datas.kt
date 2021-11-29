package com.example.imc.utils

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

fun convertStringToLocalDate(brazilDate: String) : LocalDate {

    val dateFormatterFromBrazil = DateTimeFormatter
            .ofPattern("dd/MM/yyyy")

    val localDateFormat = LocalDate
            .parse(brazilDate, dateFormatterFromBrazil)

    return localDateFormat

}

fun calcularIdade (dataNascimento: String) : Int {

    val dataAtual = LocalDate.now()

    //converter data de nascimento em Localdate
    //extrair a data em array
    val nascimentoArray = dataNascimento.split("-").toTypedArray()

    val nascimento = LocalDate.of(
            nascimentoArray[0].toInt(),
            nascimentoArray[1].toInt(),
            nascimentoArray[2].toInt()
    )

    val idade = Period.between(nascimento, dataAtual).years

    return idade

}

fun getDataAtualBrasil(): String {

    val hoje = LocalDate.now()

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val dataBrasil = hoje.format(formatter)

    return dataBrasil
}