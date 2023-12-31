package com.example.widgettest1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        var buttonIntent = Intent(this,MainActivity::class.java)
        var buttonPendingIntent = PendingIntent.getActivity(this, 0, buttonIntent, PendingIntent.FLAG_IMMUTABLE)

        var serviceIntent = Intent(this,ExampleWidgetService::class.java)
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        serviceIntent.data = Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))

        var clickIntent = Intent(this,ExampleAppWidgetProvider::class.java)
        clickIntent.action = "actionToast"
        var clickPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            clickIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        var remoteViews = RemoteViews(this.packageName,R.layout.example_app_widget_provider)
        remoteViews.setOnClickPendingIntent(R.id.example_widget_button,buttonPendingIntent)
        remoteViews.setCharSequence(R.id.example_widget_button,"setText",buttonText)
        remoteViews.setRemoteAdapter(R.id.example_widget_stack_view,serviceIntent)
        remoteViews.setEmptyView(R.id.example_widget_stack_view,R.id.example_widget_empty_view)
        remoteViews.setPendingIntentTemplate(R.id.example_widget_stack_view,clickPendingIntent)
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