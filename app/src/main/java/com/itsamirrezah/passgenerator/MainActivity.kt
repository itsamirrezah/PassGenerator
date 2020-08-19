package com.itsamirrezah.passgenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var passwordGenerator: PasswordGenerator
    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        loadPrefManager()

        btnGenerate.setOnClickListener {

            setPrefManager()

            passwordGenerator = PasswordGenerator(
                prefManager.passwordLength,
                prefManager.isSymbolUsing,
                prefManager.isUppercaseUsing,
                prefManager.isNumberUsing
            )

            tvPassword.text = passwordGenerator.requestPassword()
        }
    }

    private fun setPrefManager() {
        prefManager.passwordLength = sliderLength.value.toInt()
        prefManager.isSymbolUsing = sliderSymbols.isChecked
        prefManager.isUppercaseUsing = uppercase.isChecked
        prefManager.isNumberUsing = numbers.isChecked
    }

    private fun loadPrefManager() {
        prefManager = PrefManager.getInstance(this)
        sliderLength.value = prefManager.passwordLength.toFloat()
        sliderSymbols.isChecked = prefManager.isSymbolUsing
        uppercase.isChecked = prefManager.isUppercaseUsing
        numbers.isChecked = prefManager.isNumberUsing
    }

    private fun resetValues() {
        tvPassword.text = ""
    }

}