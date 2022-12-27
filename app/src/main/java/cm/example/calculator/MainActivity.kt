package cm.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import cm.fms.calculator.R

class MainActivity : AppCompatActivity() {
    private var tvAnswer : TextView ?= null
    var lastNumeric : Boolean = false // flag for numeric input
    var lastDot : Boolean = false // flag for decimal input

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAnswer = findViewById(R.id.tvAnswer)
    }

    fun onClickBtn(view : View){
        tvAnswer?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view : View){
        tvAnswer?.text = ""
    }

    fun onClickDot(view: View){
        if (lastNumeric && !lastDot){
            tvAnswer?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onClickOperator(view: View){
        tvAnswer?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())){
                tvAnswer?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    /**
     * Calculate the output
     */
    fun onEqual(view: View) {
        // If the last input is a number only, solution can be found.
        if (lastNumeric) {
            // Read the textView value
            var tvValue = tvAnswer?.text.toString()
            var prefix = ""
            try {

                // Here if the value starts with '-' then we will separate it and perform the calculation with value.
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1);
                }

                // If the inputValue contains the Division operator
                when {
                    tvValue.contains("/") -> {
                        // Will split the inputValue using Division operator
                        val splitedValue = tvValue.split("/")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /*Here as the value one and two will be calculated based on the operator and
                                if the result contains the zero after decimal point will remove it.
                                And display the result to TextView*/
                        tvAnswer?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        // If the inputValue contains the Multiplication operator
                        // Will split the inputValue using Multiplication operator
                        val splitedValue = tvValue.split("*")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /** Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        tvAnswer?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    tvValue.contains("-") -> {

                        // If the inputValue contains the Subtraction operator
                        // Will split the inputValue using Subtraction operator
                        val splitedValue = tvValue.split("-")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /** Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        tvAnswer?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        // If the inputValue contains the Addition operator
                        // Will split the inputValue using Addition operator
                        val splitedValue = tvValue.split("+")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /**Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        tvAnswer?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Remove the zero after decimal point
     */
    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }


    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") ||
                    value.contains("*") ||
                    value.contains("+") ||
                    value.contains("-")

        }
    }
}