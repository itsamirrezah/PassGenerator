package com.itsamirrezah.passgenerator

import java.security.SecureRandom


class PasswordGenerator(
    private var pwLength: Int,
    private var isUsingSymbols: Boolean,
    private var isUsingUppercase: Boolean,
    private var isUsingNumbers: Boolean
) {
    private val isUsingLowercase: Boolean = true
    private val password = mutableListOf<String>()
    private val letters: List<String> = makeLetters()
    private val symbols: List<String> = makeSymbols()
    private val numbers: List<String> = makeNumbers()

    fun requestPassword(): String {
        //How many options (switches) are active?
        val options = isUsingUppercase.toInt() + isUsingSymbols.toInt() + isUsingNumbers.toInt() +
                isUsingLowercase.toInt()

        val optionsQuantity = setQuantity(options)
        generatePassword(optionsQuantity)

        password.shuffle()
        return password.joinToString("")
    }

    private fun generatePassword(optionsQuantity: List<Int>) {
        var index = 0

        if (isUsingUppercase)
            generatePassword(
                isUppercase = true,
                isSymbols = false,
                isNumber = false,
                quantity = optionsQuantity[index++]
            )

        if (isUsingLowercase)
            generatePassword(
                isUppercase = false,
                isSymbols = false,
                isNumber = false,
                quantity = optionsQuantity[index++]
            )

        if (isUsingSymbols)
            generatePassword(
                isUppercase = false,
                isSymbols = true,
                isNumber = false,
                quantity = optionsQuantity[index++]
            )

        if (isUsingNumbers)
            generatePassword(
                isUppercase = false,
                isSymbols = false,
                isNumber = true,
                quantity = optionsQuantity[index]
            )

    }

    private fun generatePassword(
        isUppercase: Boolean,
        isSymbols: Boolean,
        isNumber: Boolean,
        quantity: Int
    ) {
        val list = if (isSymbols) symbols else if (isNumber) numbers else letters

        val secureRandom = SecureRandom()

        for (x in 0 until quantity) {
            val randomInt = secureRandom.nextInt(list.size)
            var element = list[randomInt]
            if (isUppercase)
                element = element.toUpperCase()
            //put that element into final password
            password.add(element)
        }


    }

    //Generate N random value (N= number of active options + 1)
    //f.e: if we have (3) active options, => then we'll get a list<Int> with (4) values
    //and later on, we generate the final password based on these 4 values
    //f.e: if we get a list<Int> with these numbers: [4,2,6,3]
    //the final password should have 4 symbols, 2 uppercase letter, 6 numbers and 3 lowercase letter
    private fun setQuantity(options: Int): List<Int> {

        val secureRandom = SecureRandom()
        val optionsQuantity = mutableListOf<Int>()
        var limit: Int
        for (x in 1 until options) {
            val opLeft = options - x
            limit = pwLength - opLeft - optionsQuantity.sum()
            val randomInt = secureRandom.nextInt(limit) + 1
            optionsQuantity.add(randomInt)
        }
        //put last value into the list
        optionsQuantity.add(pwLength - optionsQuantity.sum())

        optionsQuantity.shuffle()
        return optionsQuantity
    }

    private fun Boolean.toInt() = if (this) 1 else 0

    /** ASCII:
     * ** letters: (a->z) => (97->122)
     * ** symbols: (33->47), (58->64), (91->96), (123->126) */
    companion object {
        private fun makeLetters(): List<String> {
            val letters = mutableListOf<String>()
            for (x in 97..122) {
                letters.add(x.toChar().toString())
            }
            return letters.toList()
        }

        private fun makeSymbols(): List<String> {
            val symbols = mutableListOf<String>()
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

            return symbols

        }

        private fun makeNumbers(): List<String> {
            val numbers = mutableListOf<String>()
            for (x in 0..9) numbers.add(x.toString())
            return numbers

        }
    }

}