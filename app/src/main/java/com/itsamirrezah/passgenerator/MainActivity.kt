package com.itsamirrezah.passgenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var passwordGenerator: PasswordGenerator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)


        btnGenerate.setOnClickListener {

            resetValues()

            passwordGenerator = PasswordGenerator(
                sliderLength.value.toInt(),
                sliderSymbols.isChecked,
                uppercase.isChecked,
                numbers.isChecked
            )

            passwordGenerator.requestPassword().map {
                tvPassword.append(it)
            }
        }

    }

    private fun resetValues() {
        tvPassword.text = ""
    }

}