package br.com.djektech.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btn_inserir.setOnClickListener {

            //pegando os valores digitados pelo usuário
            val produto = ed_txt_produto.text.toString()
            val qtd = ed_txt_qtd.text.toString()
            val valor = ed_txt_valor.text.toString()

            if (produto.isNotEmpty() && qtd.isNotEmpty()  && valor.isNotEmpty())  {

                //enviado o item para a lista
                val prod = Produto(produto, qtd.toInt(), valor.toDouble(), imageBitMap)
                produtosGlobal.add(prod)

                ed_txt_produto.text.clear()
                ed_txt_qtd.text.clear()
                ed_txt_valor.text.clear()

                //limpando acaixa de texto
                ed_txt_produto.text.clear()
                ed_txt_qtd.text.clear()
                ed_txt_valor.text.clear()

            }else{
                ed_txt_produto.error = if (ed_txt_produto.text.isEmpty()) "Digite o nome do produto" else null
                ed_txt_qtd.error = if (ed_txt_qtd.text.isEmpty()) "Preencha a quantidade" else null
                ed_txt_valor.error = if (ed_txt_valor.text.isEmpty()) "Preencha o valor" else null
            }
        }

        img_foto_produto.setOnClickListener {
            abrirGaleria()
        }
    }

    fun abrirGaleria(){

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                //lendo a uri com a imagem
                val inputStream = data.getData()?.let { contentResolver.openInputStream(it) };

                //transformando o resultado em bitmap
                imageBitMap = BitmapFactory.decodeStream(inputStream)

                //Exibir a imagem no aplicativo
                img_foto_produto.setImageBitmap(imageBitMap)
            }
        }
    }
}