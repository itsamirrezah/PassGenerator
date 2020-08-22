package com.itsamirrezah.passgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.service.quicksettings.TileService
import android.widget.Toast
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class PassGeneratorTile : TileService() {

    private lateinit var prefManager: PrefManager
    private lateinit var passwordGenerator: PasswordGenerator
    private lateinit var clipboardManager: ClipboardManager

    override fun onCreate() {
        super.onCreate()
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        prefManager = PrefManager.getInstance(this)
    }

    override fun onClick() {
        super.onClick()


        passwordGenerator = PasswordGenerator(
            prefManager.passwordLength,
            prefManager.isSymbolUsing,
            prefManager.isUppercaseUsing,
            prefManager.isNumberUsing
        )

        val password = passwordGenerator.requestPassword()
        pushOnClipboard(password)

        Toast.makeText(
            applicationContext,
            "Copied to clipboard!",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun pushOnClipboard(password: String) {
        val data = ClipData.newPlainText("PassGenerator", password)
        clipboardManager.setPrimaryClip(data)
    }

}