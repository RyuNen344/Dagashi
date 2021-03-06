package com.ryunen344.dagashi.ui.issues

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.google.android.material.chip.Chip
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.ItemIssueBinding
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.Label
import com.ryunen344.dagashi.util.EquatableContentsItem
import com.ryunen344.dagashi.util.TextViewClickMovement
import com.ryunen344.dagashi.util.ext.dp2px
import com.ryunen344.dagashi.util.ext.setDebouncingOnClickListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class IssuesAdapter(
    private val onLabelClickListener: (label: Label) -> Unit,
    private val onIssueClickListener: (url: String) -> Unit,
    private val onStashClickListener: (issue: Issue) -> Unit,
    private val onTextViewClickMovementListener: TextViewClickMovement.OnTextViewClickMovementListener
) : GroupAdapter<GroupieViewHolder>() {

    fun setData(list: List<Issue>) {
        updateAsync(
            list.mapIndexed { index, issue ->
                IssueItem(issue, index == list.lastIndex)
            }
        )
    }

    private inner class IssueItem(private val model: Issue, private val isLast: Boolean) :
        EquatableContentsItem<ItemIssueBinding>(model.url.hashCode().toLong()) {
        override fun bind(viewBinding: ItemIssueBinding, position: Int) {
            val context = viewBinding.root.context
            viewBinding.apply {
                textTitle.text = model.title

                layoutLabelContainer.apply {
                    removeAllViews()
                    if (model.labels.isNotEmpty()) {
                        viewScrollLabel.isVisible = true
                        model.labels.forEach { label ->
                            addView(
                                Chip(context).apply {
                                    text = label.name
                                    chipBackgroundColor = ColorStateList.valueOf(label.tipColorInt)
                                    setTextColor(context.getColor(label.tipTextColorResId))
                                    setDebouncingOnClickListener {
                                        onLabelClickListener(label)
                                    }
                                }
                            )
                        }
                    } else {
                        viewScrollLabel.isVisible = false
                    }
                }

                textBody.text = model.body
                textBody.movementMethod = TextViewClickMovement(context, onTextViewClickMovementListener)

                layoutCommentContainer.apply {
                    removeAllViews()
                    if (model.comments.isNotEmpty()) {
                        viewCommentDivider.isVisible = true
                        isVisible = true
                        model.comments.forEach { comment ->
                            addView(
                                CommentView(context).apply {
                                    bind(comment, onTextViewClickMovementListener)
                                }
                            )
                        }
                    } else {
                        viewCommentDivider.isVisible = false
                        isVisible = false
                    }
                }

                checkboxStash.isChecked = model.isStashed

                checkboxStash.setDebouncingOnClickListener {
                    onStashClickListener(model)
                }

                buttonGithub.setDebouncingOnClickListener {
                    onIssueClickListener(model.url)
                }

                root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    setMargins(
                        leftMargin,
                        topMargin,
                        rightMargin,
                        context.dp2px(
                            if (isLast) 18 else 0
                        )
                    )
                }
            }
        }

        override fun bind(viewBinding: ItemIssueBinding, position: Int, payloads: MutableList<Any>) {
            if (payloads.isEmpty()) {
                super.bind(viewBinding, position, payloads)
            } else {
                payloads.distinct().forEach { payload ->
                    when (payload) {
                        is Payload.IsStashed -> {
                            viewBinding.checkboxStash.isChecked = payload.isStashed
                        }

                        is Payload.IsLast -> {
                            viewBinding.root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                                setMargins(
                                    leftMargin,
                                    topMargin,
                                    rightMargin,
                                    viewBinding.root.context.dp2px(
                                        if (isLast) 18 else 0
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        override fun getLayout(): Int = R.layout.item_issue

        override fun initializeViewBinding(view: View): ItemIssueBinding = ItemIssueBinding.bind(view)

        override fun providerEquatableContents(): Array<*> = arrayOf(model, isLast)

        override fun getChangePayload(newItem: Item<*>): Any? {
            return when {
                newItem !is IssueItem -> null
                model.isStashed != newItem.model.isStashed -> Payload.IsStashed(newItem.model.isStashed)
                isLast != newItem.isLast -> Payload.IsLast(newItem.isLast)
                else -> null
            }
        }
    }

    private sealed class Payload {
        data class IsStashed(val isStashed: Boolean) : Payload()
        data class IsLast(val isLast: Boolean) : Payload()
    }
}
