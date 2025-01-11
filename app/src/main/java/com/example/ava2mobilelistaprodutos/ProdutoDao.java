package com.example.ava2mobilelistaprodutos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProdutoDao {

    private SQLiteDatabase dataBase;

    private String[] colunas = {ProdutoSQLiteOpenHelper.COLUNA_ID,ProdutoSQLiteOpenHelper.COLUNA_NOME_PRODUTO,ProdutoSQLiteOpenHelper.COLUNA_PRECO_PRODUTO,ProdutoSQLiteOpenHelper.COLUNA_DESCRICAO_PRODUTO,ProdutoSQLiteOpenHelper.COLUNA_CATEGORIA_PRODUTO,ProdutoSQLiteOpenHelper.COLUNA_ESTOQUE_PRODUTO};
    private ProdutoSQLiteOpenHelper produtoSQLiteOpenHelper;

    public ProdutoDao(Context context) {
        produtoSQLiteOpenHelper = new ProdutoSQLiteOpenHelper(context);


    }
    public void open(){
        dataBase = produtoSQLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        produtoSQLiteOpenHelper.close();
    }

    public void cadastrar(Produto produto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(colunas[1], produto.getNome());
        contentValues.put(colunas[2], produto.getPreco());
        contentValues.put(colunas[3], produto.getDescricao());
        contentValues.put(colunas[4], produto.getCategoria());
        contentValues.put(colunas[5], produto.getEstoque());
        dataBase.insert(ProdutoSQLiteOpenHelper.TABELA, null, contentValues);
    }
    public void alterar(Produto produto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(colunas[1], produto.getNome());
        contentValues.put(colunas[2], produto.getPreco());
        contentValues.put(colunas[3], produto.getDescricao());
        contentValues.put(colunas[4], produto.getCategoria());
        contentValues.put(colunas[5], produto.getEstoque());
        dataBase.update(ProdutoSQLiteOpenHelper.TABELA, contentValues, colunas[0] + "=" + produto.getId(), null);
    }
    public void excluir(Long id){
        dataBase.delete(ProdutoSQLiteOpenHelper.TABELA, colunas[0] + "=" + id, null);

    }
    public Produto buscar(Long id){
        Cursor cursor = dataBase.query(ProdutoSQLiteOpenHelper.TABELA, colunas,colunas[0] + "=" + id, null,null, null,null);
        cursor.moveToFirst();

        Produto produto = new Produto(cursor.getLong(0),cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
        cursor.close();
        return produto;
    }

    public ArrayList<Produto> buscarTodos(){
        Cursor cursor = dataBase.query(ProdutoSQLiteOpenHelper.TABELA, colunas,null, null,null, null,null);
        cursor.moveToFirst();
                ArrayList<Produto> produtos = new ArrayList<>();
        while (! cursor.isAfterLast()){
            Produto produto = new Produto(cursor.getLong(0),cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
            produtos.add(produto);
            cursor.moveToNext();
        }

        cursor.close();
        return produtos;
    }

}
