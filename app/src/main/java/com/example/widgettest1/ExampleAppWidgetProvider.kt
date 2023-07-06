package com.example.widgettest1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
            var buttonIntent = Intent(context,MainActivity::class.java)
            var buttonPendingIntent = PendingIntent.getActivity(context,0, buttonIntent, PendingIntent.FLAG_IMMUTABLE)

            var pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val buttonText = pref.getString("keyButtontext" + appWidgetIds, "Press me")


            var serviceIntent = Intent(context,ExampleWidgetService::class.java)
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appwidgetId)
            serviceIntent.data = Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))

            var clickIntent = Intent(context,ExampleAppWidgetProvider::class.java)
            clickIntent.action = "actionToast"
            var clickPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                clickIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            var remoteViews = RemoteViews(context.packageName,R.layout.example_app_widget_provider)
            remoteViews.setOnClickPendingIntent(R.id.example_widget_button,buttonPendingIntent)
            remoteViews.setCharSequence(R.id.example_widget_button,"setText",buttonText)
            remoteViews.setRemoteAdapter(R.id.example_widget_stack_view,serviceIntent)
            remoteViews.setEmptyView(R.id.example_widget_stack_view,R.id.example_widget_empty_view)
            remoteViews.setPendingIntentTemplate(R.id.example_widget_stack_view,clickPendingIntent)


            var appWidgetOptions = appWidgetManager.getAppWidgetOptions(appwidgetId)

            resizeWidget(appWidgetOptions,remoteViews)

            Toast.makeText(context, "onUpdate", Toast.LENGTH_SHORT).show()

            appWidgetManager.updateAppWidget(appwidgetId,remoteViews)
            appWidgetManager.notifyAppWidgetViewDataChanged(appwidgetId,R.id.example_widget_stack_view)
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


//        var minWidth = appWidgetOptions!!.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
//        var maxWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH)
//        var minHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)
        var maxHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT)

        if (maxHeight > 150){
            views.setViewVisibility(R.id.example_text_widget, View.VISIBLE)
            views.setViewVisibility(R.id.example_widget_button, View.VISIBLE)
        }else{
            views.setViewVisibility(R.id.example_text_widget,View.GONE)
            views.setViewVisibility(R.id.example_widget_button,View.GONE)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if ("actionToast" == intent!!.action){
            var appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
            var appWidgetManager = AppWidgetManager.getInstance(context)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.example_widget_stack_view)
        }

        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Toast.makeText(context, "onEnabled", Toast.LENGTH_SHORT).show()
    }

}