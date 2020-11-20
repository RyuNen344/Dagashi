package com.ryunen344.dagashi.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Message
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.ryunen344.dagashi.BuildConfig
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentWebBinding
import com.ryunen344.dagashi.util.ext.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebFragment : Fragment(R.layout.fragment_web) {

    private var _binding: FragmentWebBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: WebViewModel by viewModels()

    private val args: WebFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.backKeyTapped()
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentWebBinding.bind(view)

        binding.apply {
            toolbar.setupWithNavController(findNavController())
            viewWeb.apply {
                setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
                savedInstanceState?.let {
                    restoreState(it)
                }
                settings.apply {
                    javaScriptEnabled = true
                    javaScriptCanOpenWindowsAutomatically = true
                    setSupportMultipleWindows(true)
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false
                }
                CookieManager.getInstance().also { manager ->
                    manager.acceptCookie()
                    manager.setAcceptThirdPartyCookies(this, true)
                }

                webChromeClient = DagashiWebChromeClient()
                webViewClient = DagashiWebViewClient()
            }
        }.run {
            if (binding.viewWeb.url == null) {
                binding.viewWeb.loadUrl(args.url)
            }
        }

        viewModel.apply {
            bind(backKeyEvent) {
                if (binding.viewWeb.canGoBack()) {
                    binding.viewWeb.goBack()
                } else {
                    findNavController().navigateUp()
                }
            }

            bind(progress) {
                binding.progressBar.progress = it
            }

            bind(webTitle) {
                binding.toolbar.title = it
            }

            bind(webViewCacheMode) {
                binding.viewWeb.settings.cacheMode = it
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.viewWeb.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class DagashiWebChromeClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            viewModel.progressChanged(newProgress)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            viewModel.titleChanged(title)
        }

        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            view ?: return false
            val href = view.handler.obtainMessage()
            view.requestFocusNodeHref(href)
            val tempWebView = WebView(requireContext())
            tempWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    request?.url?.let {
                        navigate(it.toString())
                    }
                    return false
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    url?.let {
                        navigate(it)
                    }
                    return false
                }

                private fun navigate(url: String) {
                    tempWebView.destroy()
                    view.loadUrl(url)
                }
            }
            resultMsg?.apply {
                (obj as WebView.WebViewTransport).webView = tempWebView
            }?.run {
                sendToTarget()
            }
            return true
        }
    }

    private class DagashiWebViewClient : WebViewClient()
}
