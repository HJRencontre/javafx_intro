package org.example.demo.services

import javafx.fxml.FXML
import javafx.scene.control.Label
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class GeoLocationService {
    @FXML
    lateinit var welcomeText: Label

    @FXML
    fun getUserGeolocation():String {
        val ip = getIpAddress()
        val url = URL("https://ipapi.co/$ip/json/")
        return try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonResponse = JSONObject(response)
            val city = jsonResponse.getString("city")
            val countryName = jsonResponse.getString("country_name")
            val displayText = "City: $city, Country: $countryName"
            welcomeText.text = displayText
            displayText
        } catch (e: Exception) {
            welcomeText.text = e.message.toString()
            println(e.message.toString())
            e.message.toString()
        }
    }

    @FXML
    fun getIpAddress(): String {
        val url = URL("https://api.ipify.org")
        return try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val ipAddress = connection.inputStream.bufferedReader().use { it.readLine() }
            return ipAddress
        } catch (e: Exception) {
            welcomeText.text = e.message.toString()
            println(e.message.toString())
            e.message.toString()
        }
    }
}