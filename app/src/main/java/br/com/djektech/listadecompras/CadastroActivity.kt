package br.com.djektech.listadecompras

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btn_inserir.setOnClickListener {
            val produto = ed_txt_produto.text.toString()

            if(produto.isNotEmpty()){
                //enviando produto para a lista
                //produtosAdapter.add(produto)
                //limpando acaixa de texto
                ed_txt_produto.text.clear()
            }else{
                ed_txt_produto.error = "Digite um produto"
            }

        }
    }
}