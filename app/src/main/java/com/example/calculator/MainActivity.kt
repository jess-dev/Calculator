package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var lastNumeric = false

    fun onDigit(view: View) {
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric = false
    }

    fun onOperator(view: View) {
        if (!isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        var negativeNumber = value.startsWith("-")
        return (value.contains("/") || value.contains("*")
                || value.contains("+") || (negativeNumber && (value.count { it.toString() == "-" }) > 1))
                || (!negativeNumber && (value.count { it.toString() == "-" }) == 1)
    }

    fun onDecimal(view: View) {
        if (!tvInput.text.contains('.')) {
            tvInput.append((view as Button).text)
        } else {
            Toast.makeText(applicationContext, "Only one decimal allowed", Toast.LENGTH_SHORT ).show()
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""

            if (tvValue.startsWith("-")) {
                tvValue = tvValue.substring(1)
                prefix = "-"
            }

            if (tvValue.contains("-")) {
                 tvInput.text = removeDecimal(doCalc(tvValue, "-", prefix))
            } else if (tvValue.contains("+")) {
                tvInput.text = removeDecimal(doCalc(tvValue, "+", prefix))
            } else if (tvValue.contains("/")) {
                tvInput.text = removeDecimal(doCalc(tvValue, "/", prefix))
            } else if (tvValue.contains("*")) {
                tvInput.text = removeDecimal(doCalc(tvValue, "*", prefix))
            }
        }
    }

    private fun doCalc(sum: String, operator: String, prefix: String ) :String {
        var calc = sum.split(operator).toMutableList()
        if (prefix.isNotEmpty()) {
            calc[0] = prefix + calc[0]
        }

        return when (operator) {
            "-" -> (calc[0].toDouble() - calc[1].toDouble()).toString()
            "+" -> (calc[0].toDouble() + calc[1].toDouble()).toString()
            "*" -> (calc[0].toDouble() * calc[1].toDouble()).toString()
            "/" -> (calc[0].toDouble() / calc[1].toDouble()).toString()
            else ->
                ""
        }
    }

    private fun removeDecimal(result: String): String {
        return if (result.endsWith(".0")) {
            result.substring(0, result.length - 2)
        } else {
            result
        }
    }

}