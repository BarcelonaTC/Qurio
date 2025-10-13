package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.barcelona.qurio.R

class CharacterCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val openCharacterContainer: ConstraintLayout
    private val imageCharacter: ImageView
    private val iconCorrect: ImageView
    private val lockedCharacterContainer: ConstraintLayout
    private val imageLockedCharacter: ImageView
    private val lockIcon: ImageView
    private val coinIcon: ImageView
    private val moneyText: TextView
    private val characterName: TextView

    init {
        inflate(context, R.layout.character_card, this)

        openCharacterContainer = findViewById(R.id.open_character)
        imageCharacter = findViewById(R.id.image_character)
        iconCorrect = findViewById(R.id.icon_correct)
        lockedCharacterContainer = findViewById(R.id.image_locked_character_container)
        imageLockedCharacter = findViewById(R.id.image_locked_character)
        lockIcon = findViewById(R.id.lock_icon)
        coinIcon = findViewById(R.id.coin)
        moneyText = findViewById(R.id.money)
        characterName = findViewById(R.id.character_name)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CharacterCardView)
            val name = typedArray.getString(R.styleable.CharacterCardView_characterName) ?: ""
            val image = typedArray.getResourceId(R.styleable.CharacterCardView_characterImage, -1)
            val isLocked = typedArray.getBoolean(R.styleable.CharacterCardView_isLocked, false)
            val isSelected = typedArray.getBoolean(R.styleable.CharacterCardView_isSelected, false)
            typedArray.recycle()

            setCharacterName(name)

            if (image != -1) {
                setCharacterImage(image)
            }
            setLocked(isSelected, isLocked)
        }
    }

    fun setCharacterName(name: String) {
        Log.d("test", "setCharacterName: $name")
        characterName.text = name
        Log.d("test", "setCharacterNameAfter: ${characterName.text}")
    }

    fun setCharacterImage(image: Int) {
        imageCharacter.setImageResource(image)
        imageLockedCharacter.setImageResource(image)
    }

    fun setLocked(isSelected: Boolean, isLocked: Boolean) {
        if (isSelected) {
            openCharacterContainer.visibility = VISIBLE
            iconCorrect.visibility = VISIBLE
            lockedCharacterContainer.visibility = INVISIBLE
            lockIcon.visibility = INVISIBLE
            coinIcon.visibility = INVISIBLE
            moneyText.visibility = INVISIBLE
            characterName.setTextColor(ContextCompat.getColor(context, R.color.primary))
        } else if (isLocked) {
            openCharacterContainer.visibility = INVISIBLE
            lockedCharacterContainer.visibility = VISIBLE
            lockIcon.visibility = VISIBLE
            characterName.setTextColor(ContextCompat.getColor(context, R.color.shade_tertiary))
        } else {
            openCharacterContainer.visibility = VISIBLE
            lockedCharacterContainer.visibility = INVISIBLE
            lockIcon.visibility = INVISIBLE
            iconCorrect.visibility = INVISIBLE
            coinIcon.visibility = INVISIBLE
            moneyText.visibility = INVISIBLE
            characterName.setTextColor(ContextCompat.getColor(context, R.color.shade_secondary))
        }
    }

    fun setSalary(amount: Int) {
        moneyText.text = amount.toThousandsLabel()
    }
}

private fun Int.toThousandsLabel(): String {
    return if (this >= 1000) "${(this / 1000.0).toInt()}K" else this.toString()
}

