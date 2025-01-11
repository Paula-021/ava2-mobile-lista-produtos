package com.example.ava2mobilelistaprodutos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProdutoSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABELA = "Produto";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME_PRODUTO = "Nome";
    public static final String COLUNA_PRECO_PRODUTO = "Preco";
    public static final String COLUNA_DESCRICAO_PRODUTO = "Descriacao";
    public static final String COLUNA_CATEGORIA_PRODUTO = "Categoria";
    public static final String COLUNA_ESTOQUE_PRODUTO = "Estoque";

    private static final String DATABASE_NAME = "GerenciamentoProdutos.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_TABELA = "CREATE TABLE " + TABELA + "(" + COLUNA_ID + " Integer primary key autoincrement, " + COLUNA_NOME_PRODUTO + " Text not null, " + COLUNA_PRECO_PRODUTO + " Double not null, " + COLUNA_DESCRICAO_PRODUTO + " Text not null, " + COLUNA_CATEGORIA_PRODUTO + " Text not null, " + COLUNA_ESTOQUE_PRODUTO + " Integer not null);";

    public ProdutoSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(SQL_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("drop table if exists " + TABELA);
    onCreate(sqLiteDatabase);
    }
}
