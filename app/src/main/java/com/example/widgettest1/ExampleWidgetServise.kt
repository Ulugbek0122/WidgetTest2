package com.example.widgettest1

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class ExampleWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        TODO("Not yet implemented")
    }

    class ExampleWidgetItemFactory : RemoteViewsFactory{
        private lateinit var context: Context
        private var appWidgetId:Int = 0
        private var list = arrayListOf("one","two","three","four","five","six","seven","eight","nine","ten")


        override fun onCreate() {
            TODO("Not yet implemented")
        }

        override fun onDataSetChanged() {
            TODO("Not yet implemented")
        }

        override fun onDestroy() {
            TODO("Not yet implemented")
        }

        override fun getCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getViewAt(p0: Int): RemoteViews {
            TODO("Not yet implemented")
        }

        override fun getLoadingView(): RemoteViews {
            TODO("Not yet implemented")
        }

        override fun getViewTypeCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getItemId(p0: Int): Long {
            TODO("Not yet implemented")
        }

        override fun hasStableIds(): Boolean {
            TODO("Not yet implemented")
        }

    }
}