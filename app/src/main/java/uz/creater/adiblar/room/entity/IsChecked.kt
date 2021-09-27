package uz.creater.adiblar.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class IsChecked(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "checked")
    var checked: Boolean? = null,
    @ColumnInfo(name = "type")
    var type: String? = null
): Serializable