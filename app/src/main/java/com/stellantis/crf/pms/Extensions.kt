package com.stellantis.crf.pms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Any.serialize(): String = GsonBuilder().disableHtmlEscaping().create().toJson(this)
fun Any.serializePretty(): String =
    GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(this)

inline fun <reified T> String.deserialize(): T =
    GsonBuilder().create().fromJson<T>(this, object : TypeToken<T>() {}.type)

inline fun <reified T> String.deserializeOrNull(): T? = tryOrNull { this.deserialize<T>() }

inline fun <T> tryOrNull(block: () -> T): T? {
    return try {
        block()
    } catch (t: Throwable) {
        null
    }
}

fun inflate(layoutResId: Int, parent: ViewGroup): View {
    return LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
}


/** Long-String Date Formatting **/

fun Long.toStringTimestamp(format: String): String {
    return retrieveFormatter(format).format(Date(this))
}

fun String.toLongTimestamp(format: String): Long {
    return retrieveFormatter(format).parse(this)?.time ?: throw ParseException(this, 0)
}

private val mDateFormatters = mutableMapOf<String, DateFormat>()
private fun retrieveFormatter(format: String): DateFormat {
    return mDateFormatters[format] ?: SimpleDateFormat(format, Locale.ITALIAN).also {
        // Save for later use
        mDateFormatters[format] = it
    }
}

/** Keyboard utils **/

fun hideKeyboard(context: Context?, focusedView: View?) {
    if (focusedView != null) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}

fun showKeyboard(context: Context?, focusedView: View?) {
    if (focusedView != null) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(focusedView, 0)
    }
}

fun Fragment.showKeyboard(focusView: View? = view) {
    showKeyboard(context, focusedView = focusView)
}

fun Fragment.hideKeyboard() {
    hideKeyboard(context, view)
}