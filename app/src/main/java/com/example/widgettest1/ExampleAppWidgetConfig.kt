package com.example.widgettest1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RemoteViews
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.widgettest1.databinding.ActivityExampleAppWidgetConfigBinding

class ExampleAppWidgetConfig : AppCompatActivity() {

     val shared_pref = "pref"
    val key_button_text = "keyButtontext"

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    private val binding:ActivityExampleAppWidgetConfigBinding by viewBinding(ActivityExampleAppWidgetConfigBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_app_widget_config)

        var configIntent = intent
        var extras = configIntent.extras
        if (extras != null){
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        var resultvalue = Intent()
        resultvalue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        setResult(RESULT_CANCELED,resultvalue)

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish()
        }
    }

    fun confirmConfiguration(v:View){
        val buttonText = binding.editTextButton.text.toString()
        var appWidgetManager = AppWidgetManager.getInstance(this)
        var intent = Intent(this,MainActivity::class.java)
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var remoteViews = RemoteViews(this.packageName,R.layout.example_app_widget_provider)
        remoteViews.setOnClickPendingIntent(R.id.example_widget_button,pendingIntent)
        remoteViews.setCharSequence(R.id.example_widget_button,"setText",buttonText)
//        remoteViews.setInt(R.id.example_widget_button,"setBackgroundColor",Color.RED)

        appWidgetManager.updateAppWidget(appWidgetId,remoteViews)

        var shared = getSharedPreferences(shared_pref, MODE_PRIVATE)
        var edit = shared.edit()
        edit.putString(key_button_text + appWidgetId,buttonText)
        edit.apply()

        var resultvalue = Intent()
        resultvalue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        setResult(RESULT_OK,resultvalue)
        finish()

    }
}