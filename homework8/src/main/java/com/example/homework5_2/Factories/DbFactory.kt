package com.example.homework5_2.Factories

import android.os.Handler
import com.example.homework5_2.Async.DBCompletableFuturePoolExecutor
import com.example.homework5_2.Async.DBRxJava
import com.example.homework5_2.Async.DBThreadPoolExecutor
import com.example.homework5_2.Async.EDBService
import com.example.homework5_2.DataBase.DBHelper
import java.util.concurrent.Executor

class DbFactory {

    companion object{
        fun getDBThreadPoolExecutor(dbHelper: DBHelper, mainHandler: Handler) =
            DBThreadPoolExecutor(
                dbHelper,
                mainHandler
            ) as EDBService

        fun getDBCompletableFuturePoolExecutor(dbHelper: DBHelper, mainExecutor : Executor) =
        DBCompletableFuturePoolExecutor(
            mainExecutor,
            dbHelper
        ) as EDBService

        fun getDBRXJava(dbHelper: DBHelper) =
            DBRxJava(
                dbHelper
            ) as EDBService


    }
}