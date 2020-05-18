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

class LegalNoticeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupToolbar()
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.legal_notice_fragment, container, false)
        val legalNoticeWebView = view.findViewById<WebView>(R.id.legal_notice_WebView)
        legalNoticeWebView.loadUrl("file:///android_asset/legal_notice.html")

        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
        activity?.menuInflater?.inflate(R.menu.sign_in, menu)
    }

    private fun setupToolbar() {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.legal_notice)
        toolbar.setDisplayHomeAsUpEnabled(true)
        (activity as SmartHomeActivity).hideLogo()
    }
}
