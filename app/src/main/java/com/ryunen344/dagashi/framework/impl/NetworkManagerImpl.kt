package com.ryunen344.dagashi.framework.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.ryunen344.dagashi.framework.NetworkManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkManagerImpl @Inject constructor(context: Context) : NetworkManager {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val innerIsConnected: Boolean
        get() {
            val activeNetworks =
                connectivityManager.allNetworks
                    .mapNotNull { connectivityManager.getNetworkCapabilities(it) }
                    .filter {
                        it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                                it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    }

            return activeNetworks.isNotEmpty()
        }

    override val isConnected: Flow<Boolean> = callbackFlow {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                offer(innerIsConnected)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                offer(innerIsConnected)
            }

            override fun onLost(network: Network) {
                offer(innerIsConnected)
            }

            override fun onUnavailable() {
                offer(innerIsConnected)
            }
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
}
