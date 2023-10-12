package com.example.pokemonsapp.ui.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.pokemonsapp.R
import com.example.pokemonsapp.utils.extensions.getInitials
import com.example.pokemonsapp.utils.extensions.loadImage
import com.example.pokemonsapp.utils.extensions.startsWithAlphabetCharacter

class CircularInitialsImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var url = ""
    private var initials = ""
    private var initialsBackgroundColor = Color.GREEN
    private var initialsTextColor = Color.WHITE
    private var placeholder: Drawable? = null

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.CircularInitialsImageView, defStyleAttr, 0
        )

        if (typedArray.hasValue(R.styleable.CircularInitialsImageView_url)) {
            url = typedArray.getString(
                R.styleable.CircularInitialsImageView_url
            ).toString()
        }

        if (typedArray.hasValue(R.styleable.CircularInitialsImageView_initials)) {
            initials = typedArray.getString(
                R.styleable.CircularInitialsImageView_initials
            ).toString()
        }

        if (typedArray.hasValue(R.styleable.CircularInitialsImageView_initialsBackgroundColor)) {
            initialsBackgroundColor = typedArray.getColor(
                R.styleable.CircularInitialsImageView_initialsBackgroundColor,
                ContextCompat.getColor(this.context, R.color.green_light)
            )
        }

        if (typedArray.hasValue(R.styleable.CircularInitialsImageView_initialsTextColor)) {
            initialsTextColor = typedArray.getColor(
                R.styleable.CircularInitialsImageView_initialsTextColor,
                ContextCompat.getColor(this.context, R.color.green_dark)
            )
        }

        if (typedArray.hasValue(R.styleable.CircularInitialsImageView_placeholder)) {
            placeholder =
                typedArray.getDrawable(R.styleable.CircularInitialsImageView_placeholder)
                    ?: ContextCompat.getDrawable(context, R.drawable.img_placeholder)
        }

        placeholder?.let { getCircularImage(url, initials, it) }
        typedArray.recycle()
    }

    fun setImageInformation(
        url: String,
        initials: String,
        placeholder: Drawable?,
        textColor: Int = Color.WHITE,
        backgroundColor: Int = Color.GREEN
    ) {
        initialsTextColor = textColor
        initialsBackgroundColor = backgroundColor
        getCircularImage(url, initials, placeholder!!)
    }

    private fun getCircularImage(url: String, initialsStr: String, placeholder: Drawable) {
        if (url.isNotEmpty()) {
            loadImage(url) {
                loadInitials(initialsStr, placeholder)
            }
        } else {
            loadInitials(initialsStr, placeholder)
        }
    }

    private fun loadInitials(initialsStr: String, placeholder: Drawable) {
        if (initialsStr.isNotEmpty() && initialsStr.startsWithAlphabetCharacter()) {
            val text = initialsStr.getInitials().uppercase()
            drawInitials(text)
        } else {
            this.background = placeholder
        }
    }

    private fun drawInitials(initials: String) {
        val size = 200
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val backgroundPaint = Paint()
        backgroundPaint.color = initialsBackgroundColor
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, backgroundPaint)

        val textPaint = Paint()
        textPaint.color = initialsTextColor
        textPaint.textSize = 60f
        textPaint.isAntiAlias = true

        val textBounds = Rect()
        textPaint.getTextBounds(initials, 0, initials.length, textBounds)
        val x = size / 2f - textBounds.exactCenterX()
        val y = size / 2f - textBounds.exactCenterY()

        canvas.drawText(initials, x, y, textPaint)
        this.background = BitmapDrawable(context.resources, bitmap)
    }

}