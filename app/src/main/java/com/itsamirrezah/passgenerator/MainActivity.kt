package com.itsamirrezah.passgenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val characters = mutableListOf<String>()
    val symbols = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        //make char with their ascii code
        makeCharacters()
        makeSymbols()
    }


    /**
     * ASCII codes:
     * ** characters: (a->z) => (97->122)
     * ** symbols: (33->47), (58->64), (91->96), (123->126)
     */

    fun makeCharacters() {

        for (x in 97..122) {
            characters.add(x.toChar().toString())
        }
    }

    fun makeSymbols() {

        for (x in 33..47) {
            symbols.add(x.toChar().toString())
        }

        for (x in 58..64) {
            symbols.add(x.toChar().toString())
        }

        for (x in 91..96) {
            symbols.add(x.toChar().toString())
        }

        for (x in 123..126) {
            symbols.add(x.toChar().toString())
        }
    }

}