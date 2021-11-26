package br.com.djektech.listadecompras

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.text.NumberFormat
import java.util.*

val produtosGlobal = mutableListOf<Produto>()

//funcão de extensão para formatar números Double no formato moeda
fun Double.moeda(): String{

    val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))

    return f.format(this)

}

//transforma Bitmap em ByteArray
fun Bitmap.toByteArray(): ByteArray{
    val stream = ByteArrayOutputStream()
    this.compress(android.graphics.Bitmap.CompressFormat.PNG,0, stream)
    return stream.toByteArray()
}

//transforma ByteArray em Bitmap
fun ByteArray.toBitmap(): Bitmap{
    return BitmapFactory.decodeByteArray(this, 0 , this.size)
}