package com.intive.patronage.smarthome.feature.settings.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity

class AboutUsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupToolbar()
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.about_us_fragment, container, false)
        val tpaWebView = view.findViewById<WebView>(R.id.about_us_view)
        tpaWebView.loadUrl("file:///android_asset/about_us.html")

        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
        activity?.menuInflater?.inflate(R.menu.sign_in, menu)
    }

    private fun setupToolbar() {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.about_us_title)
        toolbar.setDisplayHomeAsUpEnabled(true)
        (activity as SmartHomeActivity).hideLogo()
    }
}