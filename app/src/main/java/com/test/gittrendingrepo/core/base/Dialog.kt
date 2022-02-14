package com.test.gittrendingrepo.core.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.test.gittrendingrepo.R

open class Dialog<T>(context: Context, layout: Int) : Dialog(context, R.style.DialogTheme) where T : ViewDataBinding {
    companion object {
        private const val DEFAULT_ANIMATION_DURATION = 200L
    }

    protected var cancellable = false

    protected var binding: T = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        layout,
        null,
        false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setCancelable(cancellable)

        setupWindow()
    }

    protected open fun setupWindow() {
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setGravity(Gravity.CENTER)
    }

    fun createDialogAnimation(popupView: View, animId: Int, duration: Long = DEFAULT_ANIMATION_DURATION) {
        val animation = AnimationUtils.loadAnimation(context, animId)
        animation.duration = duration
        popupView.startAnimation(animation)
    }
}