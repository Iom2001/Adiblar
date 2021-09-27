package uz.creater.adiblar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.creater.adiblar.R
import uz.creater.adiblar.adapters.RvSavedAdapter
import uz.creater.adiblar.databinding.FragmentSavedBinding
import uz.creater.adiblar.models.Adib
import uz.creater.adiblar.room.database.AppDatabase
import uz.creater.adiblar.room.entity.IsChecked

private const val ARG_PARAM1 = "param1"

class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adibList: ArrayList<Adib>
    private lateinit var rvSavedAdapter: RvSavedAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater)
        appDatabase = AppDatabase.getInstance(requireContext())
        firebaseFirestore = FirebaseFirestore.getInstance()
        adibList = ArrayList()
        rvSavedAdapter = RvSavedAdapter(object : RvSavedAdapter.OnClickRv {

            override fun onItemClick(adib: Adib) {
                val bundle = Bundle()
                bundle.putSerializable(
                    ARG_PARAM1, adib
                )
                findNavController().navigate(R.id.infoFragment, bundle)
            }

            override fun onSavedButtonClick(adib: Adib, position: Int) {
            }
        })
        appDatabase.isCheckedDao().getAllIsChecked(true)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<List<IsChecked>> {
                override fun accept(t: List<IsChecked>?) {
                    rvSavedAdapter.submitList(t)
                }
            })
        binding.rvSaved.adapter = rvSavedAdapter
        binding.searchCard.setOnClickListener {
            var bundle = Bundle()
            bundle.putBoolean(ARG_PARAM1, true)
            findNavController().navigate(R.id.searchFragment, bundle)
        }
        return binding.root
    }
}