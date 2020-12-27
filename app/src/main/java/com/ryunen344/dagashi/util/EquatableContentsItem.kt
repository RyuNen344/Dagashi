package com.ryunen344.dagashi.util

import androidx.viewbinding.ViewBinding
import com.xwray.groupie.viewbinding.BindableItem

abstract class EquatableContentsItem<T : ViewBinding>(id: Long) : BindableItem<T>(id) {

    abstract fun providerEquatableContents(): Array<*>

    override fun equals(other: Any?): Boolean {
        return isSameContents(other)
    }

    override fun hashCode(): Int {
        return contentsHash()
    }

    private fun isSameContents(other: Any?): Boolean {
        other ?: return false
        if (other !is EquatableContentsItem<*>) return false
        if (other::class != this::class) return false
        return other.providerEquatableContents().contentDeepEquals(this.providerEquatableContents())
    }

    private fun contentsHash(): Int {
        return arrayOf(this::class, this.providerEquatableContents()).contentDeepHashCode()
    }
}
