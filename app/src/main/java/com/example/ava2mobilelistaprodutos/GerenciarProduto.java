package com.example.ava2mobilelistaprodutos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GerenciarProduto extends AppCompatActivity {
    EditText edProduto, edDescricao, edCategoria, edEstoque, edPreco;
    private Long id;
    private int acao;
    Button btnCadastrarAlterar, btnExcluir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_produto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edProduto = findViewById(R.id.ed_nome_produto);
        edDescricao = findViewById(R.id.ed_descricao);
        edCategoria = findViewById(R.id.ed_categoria);
        edEstoque = findViewById(R.id.ed_estoque);
        edPreco = findViewById(R.id.ed_preco);
        btnCadastrarAlterar = findViewById(R.id.btn_cadastrar_alterar);
        btnExcluir = findViewById(R.id.btn_excluir);

        acao = getIntent().getExtras().getInt("acao");
        id = getIntent().getExtras().getLong("id");
        if(acao == -1){
            setTitle("Cadastrar produto");
            btnCadastrarAlterar.setText("Cadastrar");
            btnExcluir.setEnabled(false);
        }else{
            setTitle("Alterar ou excluir produto");
            ProdutoDao produtoDao = new ProdutoDao(this);
            produtoDao.open();
            Produto produto = produtoDao.buscar(id);
            edProduto.setText(produto.getNome());
            edPreco.setText(String.format("%.2f",produto.getPreco()));
            edDescricao.setText(produto.getDescricao());
            edCategoria.setText(produto.getCategoria());
            edEstoque.setText(String.valueOf(produto.getEstoque()));
            produtoDao.close();
        }
    }
    public void cadastrarAlterar(View v){
        String nomeProduto = edProduto.getText().toString();
        String descricao = edDescricao.getText().toString();
        String categoria = edCategoria.getText().toString();
        int estoque = Integer.parseInt(edEstoque.getText().toString());
        double preco = Double.parseDouble(edPreco.getText().toString());

        Produto produto = new Produto(0, nomeProduto,preco,descricao,categoria,estoque);
        ProdutoDao produtoDao = new ProdutoDao(this);
        produtoDao.open();
        if(acao == -1){
            produtoDao.cadastrar(produto);
        }
        else {
            produtoDao.alterar(produto);
        }
        produtoDao.close();
        finish();
    }
    public void excluir(View v){
        if(acao == 0){
           ProdutoDao produtoDao = new ProdutoDao(this);
           produtoDao.open();
           produtoDao.excluir(id);
           produtoDao.close();
        }
    }
    public void voltar(View v){
        finish();
    }
}