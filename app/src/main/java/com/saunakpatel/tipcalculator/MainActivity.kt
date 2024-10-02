package com.saunakpatel.tipcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val billEditText = findViewById<EditText>(R.id.billEditText)
        val tipSlider = findViewById<SeekBar>(R.id.tipSlider)

        val tip1Value = findViewById<TextView>(R.id.tip1Val)
        val tip10Value = findViewById<TextView>(R.id.tip10Val)
        val tip25Value = findViewById<TextView>(R.id.tipSeekVal)

        val total1Value = findViewById<TextView>(R.id.total1Val)
        val total10Value = findViewById<TextView>(R.id.total10Val)
        val total25Value = findViewById<TextView>(R.id.totalSeekVal)

        val customTip = findViewById<TextView>(R.id.customTip)


        billEditText.setOnKeyListener(View.OnKeyListener {v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                update(billEditText, tipSlider, tip1Value, tip10Value, tip25Value, total1Value, total10Value, total25Value, customTip)
                Log.i("Sameer","pressed Enter")
                return@OnKeyListener true
            }
            false
        })

        tipSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i("Sameer","changed Tip Percent")
                update(billEditText, tipSlider, tip1Value, tip10Value, tip25Value, total1Value, total10Value, total25Value, customTip)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })




    }
    fun update(
        billEditText: EditText,
        tipSlider: SeekBar,
        tip1Value: TextView,
        tip10Value: TextView,
        tip25Value: TextView,
        total1Value: TextView,
        total10Value: TextView,
        total25Value: TextView,
        customTip : TextView
    ){
        try {
            val billAmount = parseNum(billEditText)

            // Percentages
            val tip1 = billAmount * 0.01
            val tip10 = billAmount * 0.10
            val tip25 = billAmount * (tipSlider.progress / 100.0)

            // Totals
            val total1 = billAmount + tip1
            val total10 = billAmount + tip10
            val total25 = billAmount + tip25

            // TextViews

            tip1Value.text = String.format("%.2f", tip1)
            tip10Value.text = String.format("%.2f", tip10)
            tip25Value.text = String.format("%.2f", tip25)

            total1Value.text = String.format("%.2f", total1)
            total10Value.text = String.format("%.2f", total10)
            total25Value.text = String.format("%.2f", total25)

            customTip.text = String.format("%.2f %", tipSlider.progress)

        } catch (e: Exception) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
        }

    }

    private fun parseNum(et: EditText): Double {
        return et.text.toString().toDoubleOrNull() ?: 0.0
    }
}