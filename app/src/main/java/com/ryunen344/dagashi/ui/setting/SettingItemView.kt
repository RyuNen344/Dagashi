package com.ryunen344.dagashi.ui.setting

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.ViewSettingItemBinding

class SettingItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewSettingItemBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    var isChecked: Boolean
        get() = binding.viewSwitch.isChecked
        set(value) {
            binding.viewSwitch.isChecked = value
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SettingItemView,
            0,
            0
        ).run {
            try {
                binding.apply {
                    textTitle.text = getString(R.styleable.SettingItemView_text)
                    viewSwitch.isVisible = getBoolean(R.styleable.SettingItemView_isSwitchVisible, true)
                }
            } finally {
                recycle()
            }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.viewSurface.setOnClickListener(l)
    }
}
