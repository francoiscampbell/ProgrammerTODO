package xyz.fcampbell.programmertodo

import android.app.Application
import com.google.gson.Gson

/**
 * Created by francois on 2017-02-24.
 */
class ProgrammerTodo : Application() {
    companion object {
        lateinit var application: ProgrammerTodo
        lateinit var gson: Gson
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        gson = Gson()
    }
}