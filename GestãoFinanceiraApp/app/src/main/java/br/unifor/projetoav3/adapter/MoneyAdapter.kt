package br.unifor.projetoav3.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.unifor.projetoav3.R
import br.unifor.projetoav3.model.Money

class MoneyAdapter(val moneys: List<Money>): RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder>() {
    class MoneyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val moneyName: TextView = itemView.findViewById(R.id.item_money_textview_name)
        val moneyValue: TextView = itemView.findViewById(R.id.item_money_textview_value)
        val color:View = itemView.findViewById(R.id.editcolor_quadrado)
        val sinal: TextView = itemView.findViewById(R.id.sinal_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_money,parent,false)
        val holder = MoneyViewHolder(itemView)
        return holder;
    }

    override fun getItemCount(): Int {
        return moneys.size
    }

    override fun onBindViewHolder(holder: MoneyViewHolder, position: Int) {
        holder.moneyName.text = moneys[position].name
        holder.moneyValue.text = moneys[position].valor.toString()

        if(moneys[position].isReceita){
            holder.color.setBackgroundColor(Color.GREEN)
            holder.sinal.text = "+"
        }
        else {
            holder.color.setBackgroundColor(Color.RED)
            holder.sinal.text = "-"
        }
    }
}