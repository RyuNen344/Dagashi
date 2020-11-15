package com.ryunen344.dagashi.ui.issues

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.ryunen344.dagashi.databinding.ViewCommentBinding
import com.ryunen344.dagashi.model.Comment
import com.ryunen344.dagashi.util.TextViewClickMovement

class CommentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewCommentBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    fun bind(comment: Comment, linkClickListener: TextViewClickMovement.OnTextViewClickMovementListener) {
        binding.imageUser.load(comment.author.avatarUrl)
        binding.textAuthorLogin.text = comment.author.login
        binding.textBody.text = comment.body
        binding.textBody.movementMethod = TextViewClickMovement(context, linkClickListener)
    }
}
