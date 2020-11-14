package com.ryunen344.dagashi.ui.milestones

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.ItemMilestoneBinding
import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.util.ext.dp2px
import com.ryunen344.dagashi.util.ext.setDebouncingOnClickListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import org.threeten.bp.format.DateTimeFormatter

class MileStonesAdapter(
    private val onItemClickListener: (mileStone: MileStone) -> Unit
) : GroupAdapter<GroupieViewHolder>() {

    fun setData(data: List<MileStone>) {
        updateAsync(
            data.mapIndexed { index, mileStone ->
                MileStoneItem(mileStone, index == data.lastIndex)
            }
        )
    }

    private inner class MileStoneItem(private val model: MileStone, private val isLast: Boolean) :
        BindableItem<ItemMilestoneBinding>(model.id.hashCode().toLong()) {
        override fun bind(viewBinding: ItemMilestoneBinding, position: Int) {
            viewBinding.apply {
                textTitle.text = model.title
                textDescription.text = model.description
                textDate.text = model.closedAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                layoutContainer.setDebouncingOnClickListener {
                    onItemClickListener(model)
                }
                root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
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

        override fun getLayout(): Int = R.layout.item_milestone

        override fun initializeViewBinding(view: View): ItemMilestoneBinding = ItemMilestoneBinding.bind(view)
    }
}
