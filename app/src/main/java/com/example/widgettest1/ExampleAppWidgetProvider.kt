package com.example.widgettest1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast


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


            var appWidgetOptions = appWidgetManager.getAppWidgetOptions(appwidgetId)

            resizeWidget(appWidgetOptions,remoteViews)

            Toast.makeText(context, "onUpdate", Toast.LENGTH_SHORT).show()

            appWidgetManager.updateAppWidget(appwidgetId,remoteViews)
        }
    }


    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)

        var views  = RemoteViews(context!!.packageName,R.layout.example_app_widget_provider)

        resizeWidget(newOptions!!,views)

        appWidgetManager!!.updateAppWidget(appWidgetId,views)
    }

    private fun resizeWidget(appWidgetOptions:Bundle,views: RemoteViews){


        var minWidth = appWidgetOptions!!.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
        var maxWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH)
        var minHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)
        var maxHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT)

        if (maxHeight > 150){
            views.setViewVisibility(R.id.example_text_widget, View.VISIBLE)
        }else{
            views.setViewVisibility(R.id.example_text_widget,View.GONE)
        }
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Toast.makeText(context, "onEnabled", Toast.LENGTH_SHORT).show()
    }

}