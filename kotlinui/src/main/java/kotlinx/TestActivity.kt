package kotlinx.kotlinui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class TestActivity : AppCompatActivity() {
    companion object {
        var view: View? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val defaultView = TextView(baseContext)
        defaultView.text = "NONE"
        setContentView(view ?: defaultView)
    }
}