package uz.creater.adiblar.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.creater.adiblar.room.dao.IsCheckedDao
import uz.creater.adiblar.room.entity.IsChecked

@Database(entities = [IsChecked::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun isCheckedDao(): IsCheckedDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "isChecked.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}