package com.example.widgettest1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews


class ExampleAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appwidgetId in appWidgetIds){
            var intent = Intent(context,MainActivity::class.java)
            var pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            var pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val buttonText = pref.getString("keyButtontext" + appWidgetIds, "Press me")

            var remoteViews = RemoteViews(context.packageName,R.layout.example_app_widget_provider)
            remoteViews.setOnClickPendingIntent(R.id.example_widget_button,pendingIntent)
            remoteViews.setCharSequence(R.id.example_widget_button,"setText",buttonText)

            appWidgetManager.updateAppWidget(appwidgetId,remoteViews)
        }
    }
}