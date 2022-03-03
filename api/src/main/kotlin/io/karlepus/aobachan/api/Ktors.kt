@file:Suppress("unused")

package io.karlepus.aobachan.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import okhttp3.Cache
import okhttp3.ConnectionPool
import java.io.File
import java.io.IOException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

private val EasySSL: SSLContext = try {
    val context: SSLContext = SSLContext.getInstance("TLS")
    context.init(null, null, null)
    context
} catch (e: Exception) {
    throw IOException(e.message)
}

public val http: HttpClient = HttpClient(OkHttp) {
    engine {
        config {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            sslSocketFactory(EasySSL.socketFactory, EasyX509TrustManager(null))
            hostnameVerifier { _, _ -> true }
            val sep: String = File.separator
            cache(Cache(File("aobachan${sep}caches${sep}http${sep}ktor.tmp"), (10 * 1024 * 1024).toLong()))
            connectionPool(ConnectionPool(5, 6, TimeUnit.MINUTES))
            followRedirects(true)
            followSslRedirects(false)
            pingInterval(30, TimeUnit.SECONDS)
        }
    }
}

private class EasyX509TrustManager(keyStore: KeyStore?) : X509TrustManager {
    private var standardTrustManager: X509TrustManager? = null

    init {
        val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        factory.init(keyStore)
        val trustManagers = factory.trustManagers
        if (trustManagers.isEmpty()) throw NoSuchAlgorithmException("no trust manager found.")
        standardTrustManager = trustManagers[0] as X509TrustManager
    }

    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
        standardTrustManager?.checkClientTrusted(p0, p1)
    }

    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
        if (p0 != null && p0.size == 1) p0[0].checkValidity()
        else standardTrustManager?.checkServerTrusted(p0, p1)
    }

    override fun getAcceptedIssuers(): Array<X509Certificate>? = standardTrustManager?.acceptedIssuers
}
