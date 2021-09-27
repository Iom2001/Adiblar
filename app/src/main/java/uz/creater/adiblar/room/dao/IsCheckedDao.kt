package uz.creater.adiblar.room.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import uz.creater.adiblar.room.entity.IsChecked

@Dao
interface IsCheckedDao {

    @Insert
    fun insertIsChecked(isChecked: IsChecked)

    @Update
    fun updateIsChecked(isChecked: IsChecked)

    @Query("DELETE FROM IsChecked WHERE id = :id")
    fun deleteIsChecked(id: String)

    @Query("select * from IsChecked where checked = :checked")
    fun getAllIsChecked(checked: Boolean): Flowable<List<IsChecked>>

    @Query("select id from IsChecked where checked = :checked")
    fun getAllIsCheckedId(checked: Boolean): List<String>

    @Query("select * from IsChecked where id = :id")
    fun getIsCheckedById(id: String): IsChecked

    @Query("select * from IsChecked where id = :id")
    fun getIsCheckedFlowableById(id: String): Flowable<IsChecked>
}