package com.intive.patronage.smarthome.common

fun handleHttpResponseCode(code: Int, successMessage: Int, failMessage: Int, showToast: (textToast: Int)  -> Unit) {

    when (code) {
        200 -> {
            showToast(successMessage)
        }
        401 -> {
            showToast(failMessage)
        }
        500 -> {
            showToast(failMessage)
        }
    }
}