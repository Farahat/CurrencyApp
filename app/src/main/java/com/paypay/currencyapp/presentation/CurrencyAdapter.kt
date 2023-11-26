package com.paypay.currencyapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paypay.currencyapp.data.models.CurrencyPresentation
import com.paypay.currencyapp.data.models.RatePresentation
import com.paypay.currencyapp.databinding.ItemCurrencyBinding

class CurrencyAdapter(
    private var helperMessages: MutableList<RatePresentation>,
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    fun setItems(tasksList: List<RatePresentation>) {
        helperMessages.clear()
        helperMessages.addAll(tasksList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = ItemCurrencyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(helperMessages[position])
    }

    override fun getItemCount(): Int {
        return helperMessages.size
    }

    inner class ViewHolder(private val convertView: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(convertView.root) {
        fun bind(rate: RatePresentation) = with(convertView) {
            with(rate){
                tvAmount.text = currencyRate.toString()
                tvCountryIdentifier.text = countryIdentifier
            }
        }
    }
}
