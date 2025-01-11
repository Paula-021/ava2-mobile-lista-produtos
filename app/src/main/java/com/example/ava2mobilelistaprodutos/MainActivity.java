package com.example.ava2mobilelistaprodutos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button btnCadastrar;
    ListView lista;
    Intent intent;
    private ProdutoDao produtoDao;
    private String[] produtos;
    private Long[] idProdutos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnCadastrar = findViewById(R.id.btn_cadastrar);
        lista = findViewById(R.id.lista);
        produtoDao = new ProdutoDao(this);
        produtoDao.open();
        lista.setOnItemClickListener((AdapterView.OnItemClickListener) this);

    }
    public void cadastrar(View v){
        intent = new Intent(getApplicationContext(), GerenciarProduto.class);
        intent.putExtra("acao", -1);
        intent.putExtra("id", 0L);
        startActivity(intent);
    }
    public void sair(View v){
        finish();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Long id = idProdutos[i];
        intent = new Intent(getApplicationContext(), GerenciarProduto.class);
        intent.putExtra("acao", 0);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        produtoDao.open();
        ArrayList<Produto> listaProdutos = produtoDao.buscarTodos();
        produtos = new String[listaProdutos.size()];
        idProdutos = new Long[listaProdutos.size()];
        //iterar
        Iterator<Produto> iterator = listaProdutos.iterator();

        int i = 0;
        while (iterator.hasNext()){
            Produto produto = iterator.next();
            produtos[i] = produto.getTexto();
            idProdutos[i] = produto.getId();
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , produtos);
        lista.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        produtoDao.close();
    }
}