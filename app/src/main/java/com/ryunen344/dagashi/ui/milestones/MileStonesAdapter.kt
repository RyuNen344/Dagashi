package com.ryunen344.dagashi.ui.milestones

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.ItemMilestoneBinding
import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.util.EquatableContentsItem
import com.ryunen344.dagashi.util.ext.dp2px
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import org.threeten.bp.format.DateTimeFormatter
import ru.ldralighieri.corbind.view.clicks

class MileStonesAdapter : GroupAdapter<GroupieViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _clickedMileStone: MutableSharedFlow<MileStone> = MutableSharedFlow()
    val clickedMileStone: SharedFlow<MileStone>
        get() = _clickedMileStone

    fun setData(data: List<MileStone>) {
        updateAsync(
            data.mapIndexed { index, mileStone ->
                MileStoneItem(mileStone, index == data.lastIndex)
            }
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        scope.cancel()
    }

    private inner class MileStoneItem(private val model: MileStone, private val isLast: Boolean) :
        EquatableContentsItem<ItemMilestoneBinding>(model.id.hashCode().toLong()) {
        override fun bind(viewBinding: ItemMilestoneBinding, position: Int) {
            viewBinding.apply {
                textTitle.text = model.title
                textDescription.text = model.description
                textDate.text = model.closedAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                layoutContainer.clicks().debounce(500L).onEach {
                    _clickedMileStone.emit(model)
                }.launchIn(scope)
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

        override fun providerEquatableContents(): Array<*> = arrayOf(model, isLast)
    }
}
