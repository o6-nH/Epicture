package com.example.dev_epicture_2019

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    var webView: WebView? = null
    var token: String? = null
    var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)
        webView?.setBackgroundColor(Color.TRANSPARENT)
        val settings = webView?.settings
        settings?.setSupportMultipleWindows(true)
        webView?.loadUrl("https://api.imgur.com/oauth2/authorize?client_id=e83a0703a2fd371&response_type=token&state=APPLICATION_STATE")
        webView?.settings?.javaScriptEnabled = true

        webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean
            {
                val url=request?.url.toString()

                if (Uri.parse(url).host=="https://imgur.com") {
                    return false
                }
                val test = url.split("\\#")
                val oui = test[0]
                val splitOne = oui.split("\\&")
                var refreshToken: String? = null

                for ((index, s) in splitOne.withIndex()) {
                    val splitTwo = s.split(("\\=").toRegex()).dropLastWhile {it.isEmpty()}.toTypedArray()
                    when (index) {
                        0 -> {
                            token = splitTwo[1]
                        }
                        3 -> {
                            refreshToken = splitTwo[1]
                        }
                        4 -> {
                            username = splitTwo[1]
                        }
                    }
                }
                val intent = Intent(applicationContext, Accueil::class.java)
                intent.putExtra("token", token)
                intent.putExtra("username", username)
                startActivity(intent)
                return true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class Accueil : AppCompatActivity() {
    var webView: WebView? = null
    var token: String? = null
    var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accueil)
    }
}

class Favorites : AppCompatActivity() {
    var webView: WebView? = null
    var token: String? = null
    var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favoris)
    }
}