package net.evan1026.ledcontrol.common

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

private fun getBaseUrl() = "http://192.168.1.103:5000"
private fun getBrightnessUrl() = getBaseUrl() + "/set_value/brightness"

private suspend fun sendGetRequest(urlString: String)
    = sendGetRequest(urlString, mapOf())
private suspend fun sendGetRequest(urlString: String, params: Map<String, String>)
    = sendRequest("GET", urlString, params)
private suspend fun sendPutRequest(urlString: String)
    = sendPutRequest(urlString, mapOf())
private suspend fun sendPutRequest(urlString: String, params: Map<String, String>)
    = sendRequest("PUT", urlString, params)
private suspend fun sendPostRequest(urlString: String)
    = sendPostRequest(urlString, mapOf())
private suspend fun sendPostRequest(urlString: String, params: Map<String, String>)
    = sendRequest("POST", urlString, params)

private suspend fun sendRequest(requestType: String, urlString: String, params: Map<String, String>) {
    val url =
        if (params.isEmpty()) URL(urlString)
        else URL("${urlString}?${encodeUrlParams(params)}")

    withContext(Dispatchers.IO) {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = requestType
            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()

                Log.i("sendRequest", response.toString())
            }
        }
    }
}

private fun encodeUrlParams(params: Map<String, String>) =
    params.entries.joinToString(separator = "&", transform = ::encodeEntry)

private fun encodeEntry(entry: Map.Entry<String, String>) =
    URLEncoder.encode(entry.key, "UTF-8") + "=" + URLEncoder.encode(entry.value, "UTF-8")

suspend fun setBrightness(brightness: Int) {
    assert(brightness in 0..255) { "Invalid brightness: $brightness" }
    sendPutRequest(getBrightnessUrl(), mapOf(Pair("value", brightness.toString())))
}