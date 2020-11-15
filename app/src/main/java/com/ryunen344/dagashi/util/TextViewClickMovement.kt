package com.ryunen344.dagashi.util

import android.content.Context
import android.text.Layout
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView

/**
 * @see "https://stackoverflow.com/a/37205216"
 */
class TextViewClickMovement(
    context: Context,
    private val onTextViewClickMovementListener: OnTextViewClickMovementListener
) : LinkMovementMethod() {
    private val mGestureDetector: GestureDetector = GestureDetector(context, SimpleOnGestureListener())
    private var mWidget: TextView? = null
    private var mBuffer: Spannable? = null

    enum class LinkType {
        /** Indicates that phone link was clicked  */
        PHONE,

        /** Identifies that URL was clicked  */
        WEB_URL,

        /** Identifies that Email Address was clicked  */
        EMAIL_ADDRESS,

        /** Indicates that none of above mentioned were clicked  */
        NONE
    }

    /**
     * Interface used to handle Long clicks on the [TextView] and taps
     * on the phone, web, mail links inside of [TextView].
     */
    interface OnTextViewClickMovementListener {
        /**
         * This method will be invoked when user press and hold
         * finger on the [TextView]
         *
         * @param linkText Text which contains link on which user presses.
         * @param linkType Type of the link can be one of [LinkType] enumeration
         */
        fun onLinkClicked(linkText: String, linkType: LinkType)
    }

    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        mWidget = widget
        mBuffer = buffer
        mGestureDetector.onTouchEvent(event)
        return false
    }

    /**
     * Detects various gestures and events.
     * Notify users when a particular motion event has occurred.
     */
    internal inner class SimpleOnGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(event: MotionEvent): Boolean {
            // Notified when a tap occurs.
            return true
        }

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            // Notified when tap occurs.
            val linkText = getLinkText(mWidget, mBuffer, event)
            var linkType = LinkType.NONE
            when {
                Patterns.PHONE.matcher(linkText).matches()         -> {
                    linkType = LinkType.PHONE
                }
                Patterns.WEB_URL.matcher(linkText).matches()       -> {
                    linkType = LinkType.WEB_URL
                }
                Patterns.EMAIL_ADDRESS.matcher(linkText).matches() -> {
                    linkType = LinkType.EMAIL_ADDRESS
                }
            }
            onTextViewClickMovementListener.onLinkClicked(linkText, linkType)
            return false
        }

        private fun getLinkText(widget: TextView?, buffer: Spannable?, event: MotionEvent): String {
            var x = event.x
            var y = event.y
            x -= widget!!.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY
            val layout: Layout = widget.layout
            val line: Int = layout.getLineForVertical(y.toInt())
            val off: Int = layout.getOffsetForHorizontal(line, x)
            val link = buffer!!.getSpans(off, off, ClickableSpan::class.java)
            return if (link.isNotEmpty()) {
                buffer.subSequence(
                    buffer.getSpanStart(link[0]),
                    buffer.getSpanEnd(link[0])
                ).toString()
            } else ""
        }
    }
}
