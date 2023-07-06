package com.example.widgettest1

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class ExampleWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return ExampleWidgetItemFactory(applicationContext,intent)
    }

    class ExampleWidgetItemFactory constructor(private val context: Context,private val intent: Intent): RemoteViewsFactory{

        private var appWidgetId:Int = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
        private var list = arrayListOf("one","two","three","four","five","six","seven","eight","nine","ten")


        override fun onCreate() {

            SystemClock.sleep(3000)
        }

        override fun onDataSetChanged() {

        }

        override fun onDestroy() {

        }

        override fun getCount(): Int {
            return list.size
        }

        override fun getViewAt(p0: Int): RemoteViews {
            var views = RemoteViews(context.packageName,R.layout.example_widget_item)
            views.setTextViewText(R.id.example_widget_item_text,list[p0])
            return views
        }

        override fun getLoadingView(): RemoteViews {
            return RemoteViews(context.packageName,R.id.example_widget_item_text)
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }

    }
}