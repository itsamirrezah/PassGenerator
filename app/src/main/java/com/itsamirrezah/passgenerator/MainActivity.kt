package com.itsamirrezah.passgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var passwordGenerator: PasswordGenerator
    private lateinit var prefManager: PrefManager
    private lateinit var clipboardManager: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        loadPrefManager()
        setupClipboard()

        btnGenerate.setOnClickListener {

            setPrefManager()

            passwordGenerator = PasswordGenerator(
                prefManager.passwordLength,
                prefManager.isSymbolUsing,
                prefManager.isUppercaseUsing,
                prefManager.isNumberUsing
            )

            val password = passwordGenerator.requestPassword()
            tvPassword.text = password
            pushOnClipboard(password)
        }
    }

    private fun setupClipboard() {
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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

    private fun pushOnClipboard(password: String) {
        val data = ClipData.newPlainText("PassGenerator", password)
        clipboardManager.setPrimaryClip(data)
    }


}