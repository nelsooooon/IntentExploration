package com.example.intentexploration

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intentexploration.databinding.TemplateCardBinding

class TemplateAdapter(val templates: ArrayList<String>, val activity: Activity)
    : RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TemplateViewHolder {
        val binding = TemplateCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TemplateViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TemplateViewHolder,
        position: Int
    ) {
        holder.binding.txtTemplate.text = templates[position]
        holder.binding.btnPick.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Intent.EXTRA_TEXT, templates[position])
            activity.setResult(Activity.RESULT_OK, intent)
            activity.finish()
            }
        }

    override fun getItemCount() = templates.size

    class TemplateViewHolder(var binding: TemplateCardBinding)
            : RecyclerView.ViewHolder(binding.root) {
    }
}
