package com.rjy.dagger.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.rjy.dagger.R
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog(context: Context?, themeResId: Int) : Dialog(context, themeResId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)
    }

    fun setMessage(message:String){
        if (TextUtils.isEmpty(message)){
            mMessageTv.visibility = View.GONE
        }else{
            mMessageTv.text = message
            mMessageTv.visibility = View.VISIBLE
        }
    }

}