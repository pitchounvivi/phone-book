package com.example.annuaire

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


//Création de la base de donnée et des modification de structure lors des évolutions
//la partie dans le SQLiteOpenHelper fixe les paramètres, c'est pourquoi on les enlève de DbInit
class DbInit(context: Context?) : SQLiteOpenHelper(context, "Annuaire", null, 1) {


    //Cette méthode créé la base de donnée seulement à la première execution
    //override il s'agit de surcharger les méthodes parentes
    override fun onCreate(db: SQLiteDatabase?) {

        // le ? signifie qu'il ne sera jamais null
        // le !! signifie qu'il peut être null


        //création d'une table définissant les index, relation, ...
            var sql = """
                CREATE TABLE contacts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    tel TEXT)"""
                .trimIndent()

            db?.execSQL (sql)

    }




    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}