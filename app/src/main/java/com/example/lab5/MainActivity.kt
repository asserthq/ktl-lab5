package com.example.lab5

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private var mPrice: Int = 0
    private var mPagesCount: Int = 0
    private var mSaleInPercents: Int = 0
    private var mCostLabel: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mCostLabel = findViewById(R.id.costLabelTextView)

        findViewById<EditText>(R.id.priceEditTextNumberDecimal).addTextChangedListener(
            object: TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    mPrice = s.toString().toInt()
                    updateCost()
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })

        findViewById<EditText>(R.id.pagesCountEditTextNumberDecimal).addTextChangedListener(
            object: TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    mPagesCount = s.toString().toInt()
                    updateCost()
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })

        findViewById<SeekBar>(R.id.saleSeekBar).setOnSeekBarChangeListener(
            object: OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    mSaleInPercents = progress
                    updateCost()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )
    }

    fun updateCost() {
        mCostLabel?.text = (mPrice * mPagesCount * (mSaleInPercents / 100.0f)).roundToInt().toString()
    }
}