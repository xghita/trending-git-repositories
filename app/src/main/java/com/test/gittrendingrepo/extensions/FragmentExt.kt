package com.test.gittrendingrepo.extensions

import androidx.fragment.app.Fragment
import com.test.gittrendingrepo.common.LoadingDialog
import com.test.gittrendingrepo.common.MessageDialog

fun Fragment.createLoadingDialog(
    cancelable: Boolean = false,
    cancelCallback: (() -> Unit)? = null
) = LoadingDialog(requireContext()).also {
    it.setCancelable(cancelable)
    it.cancelCallback = cancelCallback
}

fun Fragment.getMessageDialog(
    title: String,
    message: String,
    action: String
): MessageDialog {
    return MessageDialog(
        requireContext(),
        title,
        message,
        action
    )
}