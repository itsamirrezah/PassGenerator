package com.itsamirrezah.passgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var passwordGenerator: PasswordGenerator
    private lateinit var prefManager: PrefManager
    private lateinit var clipboardManager: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setSupportActionBar(toolbar)
        setupBgAnimation()
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
            Toast.makeText(applicationContext, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupBgAnimation() {
        val background = rootLayout.background as AnimationDrawable
        background.setEnterFadeDuration(10)
        background.setExitFadeDuration(3000)
        background.start()
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