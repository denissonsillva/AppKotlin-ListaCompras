package br.com.djektech.listadecompras

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //implementação do adaptador
        val produtosAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        //definindo o adaptador da lista
        list_view_produtos.adapter = produtosAdapter

        btn_inserir.setOnClickListener {
            val produto = ed_txt_produto.text.toString()
            produtosAdapter.add(produto)
        }
    }
}