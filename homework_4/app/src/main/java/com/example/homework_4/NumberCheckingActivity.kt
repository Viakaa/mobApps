package com.example.homework_4

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import com.example.homework_4.viewmodel.NumberCheckingViewModel
import com.example.homework_4.viewmodel.UiState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class NumberCheckingActivity : ComponentActivity() {

    private val viewModel: NumberCheckingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(48)
            setBackgroundColor(Color.parseColor("#60B5FF"))
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val editTextPhone = EditText(this).apply {
            hint = "Phone number ;)"
            textSize = 16f
            setTextColor(Color.parseColor("#333333"))
            setHintTextColor(Color.parseColor("#AA333333"))
            setBackgroundColor(Color.parseColor("#E0F7FA"))
            setPadding(32)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 32
            }
        }

        val buttonCheck = Button(this).apply {
            text = "Check Number :0"
            textSize = 16f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#9EC6F3"))
            setPadding(24, 16, 24, 16)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                bottomMargin = 32
            }
        }

        val textResult = TextView(this).apply {
            textSize = 18f
            gravity = Gravity.CENTER
            setTextColor(Color.parseColor("#333333"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 16
            }
        }

        layout.apply {
            addView(editTextPhone)
            addView(buttonCheck)
            addView(textResult)
        }

        setContentView(layout)

        buttonCheck.setOnClickListener {
            val number = editTextPhone.text.toString().trim()
            viewModel.checkNumber(number)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> textResult.text = "Checking number..."
                    is UiState.Success -> {
                        val response = state.response
                        textResult.text = buildString {
                            appendLine("Valid: ${response.valid}")
                            appendLine("Country: ${response.country}")
                            response.location
                                ?.takeIf { it.isNotEmpty() && it != response.country }
                                ?.let { appendLine("Location: $it") }
                            appendLine("Country Code: ${response.country_code}")
                        }
                    }
                    is UiState.Error -> textResult.text = "Error: ${state.message}"
                    null -> textResult.text = ""
                }
            }
        }
    }
}
