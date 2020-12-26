package com.ryunen344.dagashi.util.ext

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.ryunen344.dagashi.util.observe
import kotlinx.coroutines.flow.Flow

inline fun <reified T> Fragment.bind(
    source: Flow<T>,
    crossinline action: (T) -> Unit
) {
    source.observe(viewLifecycleOwner) {
        action(it)
    }
}

inline fun <reified T : ViewModel> Fragment.assistedViewModels(crossinline body: () -> T): Lazy<T> {
    return viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return body() as T
            }
        }
    }
}

inline fun <reified T : ViewModel> Fragment.assistedActivityViewModels(crossinline body: () -> T): Lazy<T> {
    return activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return body() as T
            }
        }
    }
}

inline fun <reified T : ViewModel> Fragment.assistedNavGraphViewModels(@IdRes navGraphId: Int, crossinline body: () -> T): Lazy<T> {
    return navGraphViewModels(navGraphId) {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return body() as T
            }
        }
    }
}

fun Fragment.showKeyboard(targetView: View) {
    (requireContext().getSystemService() as? InputMethodManager)?.let {
        targetView.requestFocus()
        it.showSoftInput(targetView, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Fragment.hideKeyboard() {
    (requireContext().getSystemService() as? InputMethodManager)?.let {
        it.hideSoftInputFromWindow(
            requireView().windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}
