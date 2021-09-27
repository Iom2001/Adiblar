package uz.creater.adiblar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.creater.adiblar.R
import uz.creater.adiblar.databinding.ItemRvBinding
import uz.creater.adiblar.models.Adib
import uz.creater.adiblar.room.database.AppDatabase
import uz.creater.adiblar.room.entity.IsChecked
import uz.creater.adiblar.utils.CyrillicLatinConverter
import uz.creater.adiblar.utils.MyBounceInterpolator
import uz.creater.adiblar.utils.Settings

class RvAdapter(private var list: ArrayList<Adib>, val onClickRv: OnClickRv) :
    RecyclerView.Adapter<RvAdapter.Vh>() {

    private lateinit var appDatabase: AppDatabase
    private var lastList = ArrayList<Adib>()

    inner class Vh(var bindingItem: ItemRvBinding) : RecyclerView.ViewHolder(bindingItem.root) {
        fun onBind(adib: Adib) {
            if (adib.imageUri!!.isNotBlank())
                Picasso.get().load(adib.imageUri).placeholder(R.drawable.loadplaceholder)
                    .error(R.drawable.errorplaceholder).into(bindingItem.personImg)
            bindingItem.root.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.scale)
            var name = adib.name
            if (Settings.isKiril) {
                name = CyrillicLatinConverter.latinToCyrillic(adib.name)
            }
            for (n in name!!.indices) {
                if (name.substring(n, n + 1) == " ") {
                    name = name.substring(0, n) + "\n" + name.substring(n + 1)
                    break
                }
            }
            bindingItem.nameTv.text = name
            bindingItem.yearTv.text = adib.years
            bindingItem.root.setOnClickListener {
                onClickRv.onItemClick(adib)
            }
            val myAnim =
                AnimationUtils.loadAnimation(bindingItem.root.context, R.anim.bounce_anim)
            val interpolator = MyBounceInterpolator(0.1, 20.0)
            myAnim.interpolator = interpolator
            appDatabase = AppDatabase.getInstance(bindingItem.root.context)
            var isChecked = appDatabase.isCheckedDao().getIsCheckedById(adib.name!!)
            if (isChecked == null) {
                isChecked = IsChecked(adib.name!!, false, adib.type)
                appDatabase.isCheckedDao().insertIsChecked(isChecked)
            }
            appDatabase.isCheckedDao().getIsCheckedFlowableById(adib.name!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<IsChecked> {
                    override fun accept(t: IsChecked?) {
                        isChecked = t!!
                        if (isChecked.checked!!) {
                            bindingItem.savedLayout.setBackgroundResource(R.drawable.selected_saved_btn_back)
                            bindingItem.savedImg.setImageResource(R.drawable.ic_selected_icon)
                        } else {
                            bindingItem.savedLayout.setBackgroundResource(R.drawable.unselected_saved_btn_back)
                            bindingItem.savedImg.setImageResource(R.drawable.ic_unselected_icon)
                        }
                    }
                })
            bindingItem.savedCard.setOnClickListener {
                if (isChecked.checked == false) {
                    isChecked.checked = true
                    appDatabase.isCheckedDao().updateIsChecked(isChecked)
                    bindingItem.savedImg.startAnimation(myAnim)
                } else {
                    isChecked.checked = false
                    appDatabase.isCheckedDao().updateIsChecked(isChecked)
                    bindingItem.savedImg.startAnimation(myAnim)
                }
            }

            bindingItem.root.setOnClickListener {
                onClickRv.onItemClick(adib)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnClickRv {
        fun onItemClick(adib: Adib)
    }

    fun getFilter(value: String) {
        val filterList = ArrayList<Adib>()
        val filterValue = value.lowercase().trim()
        if (lastList.size == 0) {
            if (list.size > 0) {
                lastList.addAll(list)
            }
        }
        if (lastList.size > 0) {
            if (filterValue.isNotBlank()) {
                for (i in 0 until lastList.size) {
                    if (lastList[i].name?.lowercase()!!.contains(filterValue)) {
                        filterList.add(lastList[i])
                    }
                }
                list.clear()
                list.addAll(filterList)
                notifyDataSetChanged()
            } else {
                if (list.size != lastList.size) {
                    list.clear()
                    list.addAll(lastList)
                    notifyDataSetChanged()
                }
            }
        }
    }
}