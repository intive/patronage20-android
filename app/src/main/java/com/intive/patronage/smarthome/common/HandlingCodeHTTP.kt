package com.intive.patronage.smarthome.common

fun handleCodeHTTPAndShowToast(code: Int, successMessage: Int, failMessage: Int, showToast: (textToast: Int)  -> Unit) {

    if (code == 200) {
        showMessageToast(successMessage, showToast)
    } else if (code == 401) {
        showMessageToast(failMessage, showToast)
    }
}

fun showMessageToast(message: Int, showToast: (textToast: Int)  -> Unit) {
    showToast(message)
}