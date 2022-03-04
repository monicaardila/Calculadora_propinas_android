package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AbsSeekBar
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import java.time.temporal.TemporalAmount
private  const val TAG= "MainActivity"
private const val INITIAL_TIP_PERCENT=15// el porcentaje iniciara en 15//
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount:EditText
    private lateinit var seekBarTip:SeekBar
    private lateinit var tvTipPercentLabel:TextView//porcentaje//
    private lateinit var tvTipAmount:TextView //cantidad propina//
    private lateinit var tvTotalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount=findViewById(R.id.etBaseAmount)
        seekBarTip=findViewById(R.id.seekBarTip)
        tvTipPercentLabel=findViewById(R.id.tvTipPercentLabel)
        tvTipAmount=findViewById(R.id.tvTipAmount)
        tvTotalAmount=findViewById(R.id.tvTotalAmount)

        seekBarTip.progress=INITIAL_TIP_PERCENT
        tvTipPercentLabel.text="$INITIAL_TIP_PERCENT%"//actualizar la etiqueta//
        // escuchamos me toco implementarlar //
        seekBarTip.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               Log.i(TAG, "onProgressChanged $progress")//con esto nos indica el indicador actual//
                tvTipPercentLabel.text="$progress%"
                computeTiAndTotal()//TOMARA LA BARRA DE BUSQUEDA
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        //CAMBIO DE EDICION//
        etBaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
               Log.i(TAG,"afterTextChanged $s")//LO QUE EL USUARIO ESCRIBE EN EL MOMENTO//
                computeTiAndTotal()//TOMARA EL TEXTO PARA ACUALIZAR EL DATO
            }

        })
    }
//FUNCION LOGICA LLEGAN LOS VALORES PRCENTAJE Y VALOR//
    private fun computeTiAndTotal() {
        if(etBaseAmount.text.isEmpty()){//VERIFICACION SI HAAY UN ESPACI0//
            tvTipAmount.text=""
            tvTotalAmount.text=""
            return
        }
        val baseAmount = etBaseAmount.text.toString().toDouble()//CREO VARIABLE NUEVAS//
        val tipPercent =seekBarTip.progress

        val tipAmount=baseAmount*tipPercent/100 //VALOR*PORENTAJE/100//
        val totalAmount=baseAmount+tipAmount//VALOR A PAGAR//

        tvTipAmount.text ="%.3f".format(tipAmount)
        tvTotalAmount.text="%.3f".format(totalAmount)
    }
}