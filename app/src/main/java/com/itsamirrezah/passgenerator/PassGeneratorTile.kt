package com.itsamirrezah.passgenerator

import android.os.Build
import android.service.quicksettings.TileService
import android.widget.Toast
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class PassGeneratorTile : TileService() {

    private lateinit var prefManager: PrefManager
    private lateinit var passwordGenerator: PasswordGenerator

    override fun onClick() {
        super.onClick()

        prefManager = PrefManager.getInstance(this)

        passwordGenerator = PasswordGenerator(
            prefManager.passwordLength,
            prefManager.isSymbolUsing,
            prefManager.isUppercaseUsing,
            prefManager.isNumberUsing
        )

        val password = passwordGenerator.requestPassword()

        Toast.makeText(
            applicationContext,
            "Copied to clipboard: $password",
            Toast.LENGTH_SHORT
        ).show()
    }
}