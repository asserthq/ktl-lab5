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
    private var mPrice: Int? = null
    private var mPagesCount: Int? = null
    private var mSaleInPercents: Int = 0
    private lateinit var mSaleView: TextView
    private lateinit var mCostView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mSaleView = findViewById(R.id.saleTextView)
        mCostView = findViewById(R.id.costTextView)

        mSaleView.text = getString(R.string.sale_text, 0)

        findViewById<EditText>(R.id.priceEditTextNumberDecimal).addTextChangedListener(
            object: TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    mPrice = try {
                        s.toString().toInt()
                    } catch (_: Exception) {
                        null
                    }
                    updateCost()
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })

        findViewById<EditText>(R.id.pagesCountEditTextNumberDecimal).addTextChangedListener(
            object: TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    mPagesCount = try {
                        s.toString().toInt()
                    } catch (_: Exception) {
                        null
                    }
                    updateCost()
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })

        findViewById<SeekBar>(R.id.saleSeekBar).setOnSeekBarChangeListener(
            object: OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    (progress * 5).let {
                        mSaleInPercents = it
                        mSaleView.text = getString(R.string.sale_text, it)
                    }
                    updateCost()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )
    }

    fun updateCost() {
        val price = mPrice
        val pagesCount = mPagesCount
        mCostView.text = if (price != null && pagesCount != null) {
            (price * pagesCount * (1 - mSaleInPercents / 100.0f)).roundToInt().toString()
        } else {
            getString(R.string.cost_default_text)
        }
    }
}