package com.example.annuaire

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //on déclare la variable qui sera initialisée plus tard avec le lateinit
    lateinit var db: SQLiteDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //pour accéder à la base de donnée
        //on initialise la base de donnée
        var dbInit: DbInit = DbInit(this)

        //on écrit la variable de la base de donnée
        db = dbInit.writableDatabase


    }

    fun save(view: View) {
        //on récupère les données présentes à l'écran
        var id: String = etId.text.toString()
        var name: String = etName.text.toString()
        var tel: String = etTel.text.toString()


        //on créé les variables qui vont être insérées dans la demande SQL
        var idVal: String = "id = '" + id + "'"
        var nameVal: String = "name = '" + name + "'"
        var telVal: String = "tel = '" + tel + "'"


        //on vérifie si l'id est rempli
        if (id != null) { //si id renseigné on remplit la table


            //si id n'est pas null on enregistre l'id
            var sql = """INSERT INTO contacts (name,tel) VALUES ('$name','$tel') """
                .trimIndent()

            db.execSQL (sql)

            //au lieu de faire la requête SQL en "dure"
            //il est possible de faire un équivalent avec
            //db.insert("contacts",null,values)
            //en utilisant la même façon de faire que dans le else qui suit

        }

        else { //si l'id n'est pas remplit on le met à jour et on remplit la table

            //on crée une variable values qu'on remplit
            var values: ContentValues = ContentValues()
            values.put("id",idVal)
            values.put("name",nameVal)
            values.put("tel",telVal)

            //on donne un chiffre à l'id
            var where: String = "id = '" + etId.text.toString() + "'"

            db.update("contacts", values, where,null)
        }

    }


    //pour vider les champs
    fun clear(view: View) {
        etId.text.clear()
        etName.text.clear()
        etTel.text.clear()

        etSearch.text.clear()
    }


    fun search(view: View) {
        //on crée une variable vide
        var where: String = ""

        //on créé un tableau qui contient les noms des champs
        var values: Array<String> = arrayOf("id","name","tel")

        //on récupère la valeur entrée dans le champs de recherche
        //var search: String = etSearch.text.toString()

        //on associe la valeur saisie dans le champs de recherche au WHERE de la requête
        where = "name = '" + etSearch.text.toString() + "'"

        //on fait une requête qui compare la valeur du nom saisi à l"annuaire
        //ATTENTION: il est indispensable de remplir tous les paramètres non recherché par null
        //et on récupère le résultat dans un curseur
        var cursor: Cursor = db.query("contacts",values,where,null,null,null,null)


        //en fonction du résultat de la requête (le curseur fait toutes les lignes une à une et revient au début, c'est le movetoFirst)
        if (cursor.moveToFirst()) {//on a un résultat
            //on affiche les etId, etName, etTel trouvé dans l'annuaire
            etId.setText(cursor.getString(0)) // le chiffre donne le numéro de la colonne que l'on souhaite récupérer
            etName.setText(cursor.getString(1))
            etTel.setText(cursor.getString(2))
        }
        else {//la recherche ne correspond à rien on affiche le message d'erreur
            Toast.makeText(this,"erreur", Toast.LENGTH_SHORT).show()

        }
    }


    fun delete(view: View) {

        //on donne l'indice à supprimer
        var where = "id = '" + etId.text.toString() + "'"

        //on supprime la ligne
        db.delete("contacts", where, null)

        //on est obligé de créer une "fausse" page
        clear(View(this))

    }


}
