package uz.creater.adiblar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import uz.creater.adiblar.R
import uz.creater.adiblar.databinding.ItemRvBinding
import uz.creater.adiblar.models.Adib
import uz.creater.adiblar.room.database.AppDatabase
import uz.creater.adiblar.room.entity.IsChecked
import uz.creater.adiblar.utils.CyrillicLatinConverter
import uz.creater.adiblar.utils.Settings

class RvSavedAdapter(val onClickRv: OnClickRv) :
    ListAdapter<IsChecked, RvSavedAdapter.Vh>(RvSavedDiffUtil()) {

    private lateinit var appDatabase: AppDatabase
    private var fireStore = FirebaseFirestore.getInstance()

    inner class Vh(var bindingItem: ItemRvBinding) : RecyclerView.ViewHolder(bindingItem.root) {

        fun onBind(isChecked: IsChecked) {
            fireStore.collection(isChecked.type!!).document(isChecked.id).get()
                .addOnSuccessListener {
                    val adib = it.toObject(Adib::class.java)
                    Picasso.get().load(adib?.imageUri).placeholder(R.drawable.loadplaceholder)
                        .error(R.drawable.errorplaceholder).into(bindingItem.personImg)
                    bindingItem.root.animation =
                        AnimationUtils.loadAnimation(itemView.context, R.anim.scale)
                    var name = adib?.name
                    if (Settings.isKiril) {
                        name = CyrillicLatinConverter.latinToCyrillic(adib?.name)
                    }
                    for (i in name!!.indices) {
                        if (name.substring(i, i + 1) == " ") {
                            name = name.substring(0, i) + "\n" + name.substring(i + 1)
                            break
                        }
                    }
                    bindingItem.nameTv.text = name
                    bindingItem.yearTv.text = adib?.years
                    bindingItem.root.setOnClickListener {
                        onClickRv.onItemClick(adib!!)
                    }
                }
            bindingItem.savedLayout.setBackgroundResource(R.drawable.selected_saved_btn_back)
            bindingItem.savedImg.setImageResource(R.drawable.ic_selected_icon)
            appDatabase = AppDatabase.getInstance(bindingItem.root.context)
            bindingItem.savedCard.setOnClickListener {
                bindingItem.savedLayout.setBackgroundResource(R.drawable.unselected_saved_btn_back)
                bindingItem.savedImg.setImageResource(R.drawable.ic_unselected_icon)
                isChecked.checked = false
                appDatabase.isCheckedDao().updateIsChecked(isChecked)
            }
        }
    }

    class RvSavedDiffUtil : DiffUtil.ItemCallback<IsChecked>() {

        override fun areItemsTheSame(oldItem: IsChecked, newItem: IsChecked): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: IsChecked, newItem: IsChecked): Boolean {
            return oldItem.equals(newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }

    interface OnClickRv {

        fun onItemClick(adib: Adib)

        fun onSavedButtonClick(adib: Adib, position: Int)

    }
}