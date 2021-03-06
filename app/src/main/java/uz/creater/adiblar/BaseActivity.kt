package uz.creater.adiblar

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import uz.creater.adiblar.utils.ContextUtils
import uz.creater.adiblar.utils.Settings
import java.util.*

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        var localeToSwitchTo: Locale = if (Settings.isKiril) {
            Locale("uz")
        } else {
            Locale("en")
        }
        val localeUpdatedContext: ContextWrapper =
            ContextUtils.updateLocale(newBase, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }
}