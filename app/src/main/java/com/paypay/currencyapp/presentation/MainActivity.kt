package com.paypay.currencyapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.paypay.currencyapp.R
import com.paypay.currencyapp.data.models.RatePresentation
import com.paypay.currencyapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RatesViewModel by viewModels()

    private val currencyAdapter by lazy { CurrencyAdapter(mutableListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    private fun initViews() {
        val layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = currencyAdapter

        binding.edtAmount.doAfterTextChanged {
            if (it.isNullOrEmpty().not()) {
                binding.recyclerView.isVisible = true
                val amount = binding.edtAmount.text.toString()
                calculate(amount)
            } else {
                viewModel.resetCalculations()
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.conversion.collect { event ->
                    when (event) {
                        is RatesState.Success -> {
                            binding.progressBar.isVisible = false
                            setSpinnerCurrencies(event.currencies)
                            setRatesValue(event.rates)
                        }

                        is RatesState.Failure -> {
                            Toast.makeText(
                                this@MainActivity,
                                R.string.no_internet_connection,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.progressBar.isVisible = false
                        }

                        is RatesState.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setSpinnerCurrencies(currencies: List<String>) {
        binding.acCurrency.setText(viewModel.lastSelectedCurrency)
        val marketsArrayAdapter = ArrayAdapter(
            this, R.layout.layout_drop_down_item, currencies
        )
        binding.acCurrency.setAdapter(marketsArrayAdapter)
        binding.acCurrency.setOnItemClickListener { _, _, position, _ ->
            viewModel.lastSelectedCurrency = currencies[position]
            if (binding.edtAmount.text.isNullOrEmpty().not()) {
                val amount = binding.edtAmount.text.toString()
                calculate(amount)
            } else {
                viewModel.resetCalculations()
            }
        }
    }

    private fun calculate(amount: String) {
        viewModel.onChange(amount)
    }

    private fun setRatesValue(rates: List<RatePresentation>) {
        binding.tvNote.visibility = View.VISIBLE
        if (binding.edtAmount.text.isNullOrEmpty()) {
            binding.tvNote.text = getString(
                R.string.empty_amount_note, "1", binding.acCurrency.text.toString()
            )
        } else {
            binding.tvNote.text = getString(
                R.string.empty_amount_note,
                binding.edtAmount.text.toString(),
                binding.acCurrency.text.toString()
            )
        }
        currencyAdapter.setItems(rates)
    }
}