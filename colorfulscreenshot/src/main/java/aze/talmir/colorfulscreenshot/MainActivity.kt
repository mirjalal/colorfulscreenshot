package aze.talmir.colorfulscreenshot

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.MeasureSpec
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.File

class MainActivity : AppCompatActivity() {

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null)
        setContentView(view)

        val v = findViewById<ConstraintLayout>(R.id.frame)

        takeScreenShotOfJustView(view).colorful("#aabbcc")
            .compress(Bitmap.CompressFormat.PNG, 100, File("/storage/emulated/0/Download/root3.png").outputStream())
        takeScreenShotOfJustView(v).colorful("#ffccbbaa")
            .compress(Bitmap.CompressFormat.PNG, 100, File("/storage/emulated/0/Download/child3.png").outputStream())
    }

    private fun takeScreenShotOfJustView(v: View): Bitmap {
        v.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        v.isDrawingCacheEnabled = true
        v.buildDrawingCache(true)

        val b = Bitmap.createBitmap(v.drawingCache)
        v.isDrawingCacheEnabled = false

        return b
    }

    private fun Bitmap.colorful(colorHex: String = "#ff000000"): Bitmap {
        val newBitmap = Bitmap.createBitmap(width, height, config)

        val canvas = Canvas(newBitmap)
        canvas.drawColor(Color.parseColor(colorHex))
        canvas.drawBitmap(this, 0f, 0f, null)

        return newBitmap
    }
}
