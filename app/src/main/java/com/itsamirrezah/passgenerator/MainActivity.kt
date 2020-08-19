package com.itsamirrezah.passgenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val generatedPassword = mutableListOf<String>()
    val characters = mutableListOf<String>()
    val symbols = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        //make char with their ascii code
        makeCharacters()
        makeSymbols()

        btnGenerate.setOnClickListener {

            resetValues()

            //how many options (switches) are active
            var options = uppercase.isChecked.toInt() + sliderSymbols.isChecked.toInt() +
                    numbers.isChecked.toInt() + 1
            //read password length from password length slider
            var pwLength = sliderLength.value.toInt()

            //generate n random number (n based on active options)
            //f.e: if we have 3 active switches we'll get a list<Int> with 4 values
            val optionQuantity = setupOptionQuantity(pwLength, options)
            //and later on, we generate final password based on these 4 values
            //f.e: if we get a list<Int> with these numbers [4,2,6,3]
            //final password would have 4 symbols, 2 uppercase letter, 6 numbers and 3 lowercase letter

            var index = 0
            if (sliderSymbols.isChecked) {
                //generate symbols
                for (x in 0 until optionQuantity[index++]) {
                    val randomInt = Random.nextInt(symbols.size)
                    generatedPassword.add(symbols[randomInt])
                }
            }

            if (uppercase.isChecked) {
                //generate uppercase letters
                for (x in 0 until optionQuantity[index++]) {
                    val randomInt = Random.nextInt(characters.size)
                    generatedPassword.add(characters[randomInt].toUpperCase())
                }
            }

            if (numbers.isChecked) {
                //generate numbers
                for (x in 0 until optionQuantity[index++]) {
                    val randomInt = Random.nextInt(9)
                    generatedPassword.add(randomInt.toString())
                }
            }

            for (x in 0 until optionQuantity[index]) {
                //generate lowercase letters
                val randomInt = Random.nextInt(characters.size)
                generatedPassword.add(characters[randomInt])
            }

            //shuffle to create final password
            generatedPassword.shuffle()

            //display final password on the textView
            generatedPassword.map { tvPassword.append(it) }

        }

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

    private fun setupOptionQuantity(passwordLength: Int, optionCount: Int): List<Int> {

        val countsByOptions = mutableListOf<Int>()

        var remain: Int
        for (x in 1 until optionCount) {
            val optionLeft = optionCount - x
            remain = passwordLength - optionLeft - countsByOptions.sum()
            val randomInt = Random.nextInt(1, remain)
            countsByOptions.add(randomInt)
        }
        countsByOptions.add(passwordLength - countsByOptions.sum())

        countsByOptions.shuffle()

        return countsByOptions.toList()
    }

    fun resetValues() {
        generatedPassword.clear()
        tvPassword.text = ""

    }


    //an extension function to convert boolean values to integer
    fun Boolean.toInt() = if (this) 1 else 0


}