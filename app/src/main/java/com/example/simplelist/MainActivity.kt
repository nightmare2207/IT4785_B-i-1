package com.example.simplelist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var radioEven: RadioButton
    private lateinit var radioOdd: RadioButton
    private lateinit var radioSquare: RadioButton
    private lateinit var buttonShow: Button
    private lateinit var listView: ListView
    private lateinit var textViewError: TextView

    private val numbersList = mutableListOf<Int>()
    private lateinit var adapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các view
        initializeViews()

        // Khởi tạo Adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbersList)
        listView.adapter = adapter

        // Xử lý sự kiện click nút Show
        buttonShow.setOnClickListener {
            processInput()
        }
    }

    private fun initializeViews() {
        editText = findViewById(R.id.editText)
        radioEven = findViewById(R.id.radioEven)
        radioOdd = findViewById(R.id.radioOdd)
        radioSquare = findViewById(R.id.radioSquare)
        buttonShow = findViewById(R.id.buttonShow)
        listView = findViewById(R.id.listView)
        textViewError = findViewById(R.id.textViewError)
    }

    private fun processInput() {
        // Xóa thông báo lỗi cũ
        textViewError.text = ""

        // Lấy và kiểm tra input
        val input = editText.text.toString().trim()
        if (input.isEmpty()) {
            showError("Vui lòng nhập số n")
            return
        }

        try {
            val n = input.toInt()
            if (n < 0) {
                showError("Vui lòng nhập số nguyên dương")
                return
            }

            // Xóa danh sách cũ
            numbersList.clear()

            // Xử lý theo loại số được chọn
            when {
                radioEven.isChecked -> generateEvenNumbers(n)
                radioOdd.isChecked -> generateOddNumbers(n)
                radioSquare.isChecked -> generateSquareNumbers(n)
                else -> {
                    showError("Vui lòng chọn loại số")
                    return
                }
            }

            // Cập nhật ListView
            adapter.notifyDataSetChanged()

        } catch (e: NumberFormatException) {
            showError("Vui lòng nhập số hợp lệ")
        }
    }

    private fun generateEvenNumbers(n: Int) {
        numbersList.addAll((0..n).filter { it % 2 == 0 })
    }

    private fun generateOddNumbers(n: Int) {
        numbersList.addAll((1..n).filter { it % 2 != 0 })
    }

    private fun generateSquareNumbers(n: Int) {
        numbersList.addAll((0..n).filter {
            kotlin.math.sqrt(it.toDouble()).let { sqrt ->
                sqrt == kotlin.math.floor(sqrt)
            }
        })
    }

    private fun showError(message: String) {
        textViewError.text = message
        numbersList.clear()
        adapter.notifyDataSetChanged()
    }
}