package com.hustunique.morii.design

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.OnTouchListener
import com.hustunique.morii.util.MyApplication

class StartDrag : OnTouchListener {
    private val soundItemId: Int
    private var position = -1
    private var dragFromSquares = false
    private var start: Long = 0
    private var x = 0f
    private var y = 0f

    constructor(soundItemId: Int) {
        this.soundItemId = soundItemId
    }

    constructor(position: Int, dragFromSquares: Boolean, indexOfSoundItem: Int) {
        this.position = position
        this.dragFromSquares = dragFromSquares
        soundItemId = indexOfSoundItem
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                start = System.currentTimeMillis()
                x = event.x
                y = event.y
            }
            MotionEvent.ACTION_MOVE -> if (isLongPressed(event.x, event.y)) {
                //MyApplication.getSoundItemThroughIconID(imageID).reResId(resId);
                //传递被拖动View数据
                makeVibrate()
                val imageID: Int =
                    MyApplication.Companion.soundItemList.get(soundItemId).iconResId
                val intent = Intent()
                intent.putExtra("position", position)
                intent.putExtra("ImageID", imageID)
                intent.putExtra("indexOfSoundItem", soundItemId)
                Log.d("imageViewID", imageID.toString() + "" + "draged")
                val item = ClipData.Item(intent)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_INTENT)
                val shadow = DragShadowBuilder(v)
                val dragData = ClipData("wdwd", mimeTypes, item)
                //v.startDragAndDrop(dragData, shadow, null, 0);
                if (dragFromSquares) {
                    v.alpha = 0.0f
                }
                v.startDragAndDrop(dragData, shadow, position, 0)
            }
        }
        return true
    }

    private fun isLongPressed(thisX: Float, thisY: Float): Boolean {
        val lastTime = System.currentTimeMillis() - start
        val offsetX = Math.abs(thisX - x)
        val offsetY = Math.abs(thisY - y)
        return lastTime >= 100 && offsetX <= 25 && offsetY <= 25
    }

    companion object {
        private val v =
            MyApplication.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        fun makeVibrate() {
            val vibrateDuration: Long = 20
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(
                    VibrationEffect.createOneShot(
                        vibrateDuration,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                v.vibrate(vibrateDuration)
            }
        }
    }
}