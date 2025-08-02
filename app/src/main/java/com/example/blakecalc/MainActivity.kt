package com.example.blakecalc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var display: EditText
    private var currentInput: String = ""
    private var firstValue: Double = 0.0
    private var currentOperator: String = ""
    private var isOperationPressed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        display = findViewById(R.id.result)

        // Цифры
        findViewById<Button>(R.id.btn_0).setOnClickListener { onDigitClick(it) }
        findViewById<Button>(R.id.btn_1).setOnClickListener { onDigitClick(it) }
        findViewById<Button>(R.id.btn_2).setOnClickListener { onDigitClick(it) }
        findViewById<Button>(R.id.btn_3).setOnClickListener { onDigitClick(it) }
        findViewById<Button>(R.id.btn_4).setOnClickListener { onDigitClick(it) }
        findViewById<Button>(R.id.btn_5).setOnClickListener { onDigitClick(it) }
        findViewById<Button>(R.id.btn_6).setOnClickListener { onDigitClick(it) }
        findViewById<Button>(R.id.btn_7).setOnClickListener { onDigitClick(it) }
        findViewById<Button>(R.id.btn_8).setOnClickListener { onDigitClick(it) }
        findViewById<Button>(R.id.btn_9).setOnClickListener { onDigitClick(it) }

        // Операции
        findViewById<Button>(R.id.btn_add).setOnClickListener { onOperationClick(it) }
        findViewById<Button>(R.id.btn_subtract).setOnClickListener { onOperationClick(it) }
        findViewById<Button>(R.id.btn_multiply).setOnClickListener { onOperationClick(it) }
        findViewById<Button>(R.id.btn_divide).setOnClickListener { onOperationClick(it) }

        // Доп. функции
        findViewById<Button>(R.id.btn_decimal).setOnClickListener { onDecimalClick() }
        findViewById<Button>(R.id.btn_clear).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btn_equals).setOnClickListener { onEqualsClick() }
    }

    private fun onDigitClick(view: View) {
        if (currentInput == "Error") onClearClick()

        val number = (view as Button).text.toString()
        currentInput += number
        display.setText(currentInput)
        isOperationPressed = false
    }

    private fun onDecimalClick() {
        if (currentInput == "Error") onClearClick()

        if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) currentInput = "0"
            currentInput += "."
            display.setText(currentInput)
        }
    }

    private fun onClearClick() {
        currentInput = ""
        firstValue = 0.0
        currentOperator = ""
        isOperationPressed = false
        display.setText("")
    }

    private fun onOperationClick(view: View) {
        if (currentInput.isEmpty() && isOperationPressed) {
            currentOperator = (view as Button).text.toString()
            return
        }

        if (currentInput.isNotEmpty()) {
            firstValue = currentInput.toDouble()
            currentOperator = (view as Button).text.toString()
            currentInput = ""
            isOperationPressed = true
        }
    }

    private fun onEqualsClick() {
        if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
            val secondValue = currentInput.toDouble()
            val result = when (currentOperator) {
                "+" -> firstValue + secondValue
                "-" -> firstValue - secondValue
                "×" -> firstValue * secondValue
                "/" -> if (secondValue != 0.0) firstValue / secondValue else Double.NaN
                else -> Double.NaN
            }

            currentInput = if (result.isNaN()) "Error" else result.toString()
            display.setText(currentInput)

            if (!result.isNaN()) {
                firstValue = result
            }

            isOperationPressed = true
        }
    }


}