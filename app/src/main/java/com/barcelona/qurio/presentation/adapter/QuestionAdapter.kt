package com.barcelona.qurio.presentation.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.R

class QuestionAdapter(
    val answers: List<String>,
    private val onAnswerClick: (Int) -> Unit
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    var selectedPosition: Int? = null
    var showCorrect: Boolean = false
    var correctAnswer: String? = null

    inner class ViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view) {
        val answerText: TextView = view.findViewById(R.id.answer1)
        val answerLayout: LinearLayout = view.findViewById(R.id.answer1Layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_answer, parent, false) as LinearLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val decodedText =
            Html.fromHtml(answers[position], Html.FROM_HTML_MODE_LEGACY).toString()
        holder.answerText.text = decodedText

        holder.answerLayout.setBackgroundResource(
            if (showCorrect && selectedPosition == position) {
                if (answers[position] == correctAnswer) R.drawable.correct_answer_bg
                else R.drawable.incorrect_answer_bg
            } else if (!showCorrect && selectedPosition == position) {
                R.drawable.answer_clicked_bg
            } else {
                R.drawable.quetion_text_bg
            }
        )

        holder.view.setOnClickListener {
            if (!showCorrect) {
                selectedPosition = position
                notifyDataSetChanged()
                onAnswerClick(position)
            }
        }
    }

    override fun getItemCount(): Int = answers.size
}