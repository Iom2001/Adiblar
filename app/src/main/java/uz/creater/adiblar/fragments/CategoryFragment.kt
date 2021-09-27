package uz.creater.adiblar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import uz.creater.adiblar.R
import uz.creater.adiblar.adapters.RvAdapter
import uz.creater.adiblar.databinding.FragmentCategoryBinding
import uz.creater.adiblar.models.Adib
import uz.creater.adiblar.room.database.AppDatabase

private const val ARG_PARAM1 = "param1"

class CategoryFragment : Fragment() {

    private var param1: String? = null
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var adibList: ArrayList<Adib>
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater)
        firebaseFirestore = FirebaseFirestore.getInstance()
        appDatabase = AppDatabase.getInstance(requireContext())
        adibList = ArrayList()
        rvAdapter = RvAdapter(adibList, object : RvAdapter.OnClickRv {
            override fun onItemClick(adib: Adib) {
                val bundle = Bundle()
                bundle.putSerializable(
                    ARG_PARAM1, adib
                )
                findNavController().navigate(R.id.infoFragment, bundle)
            }
        })
        binding.rvCategory.adapter = rvAdapter

        firebaseFirestore.collection(param1!!).addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w("TAG", "listen:error", e)
                return@addSnapshotListener
            }
            for (a in snapshots!!.documentChanges) {
                when (a.type) {
                    DocumentChange.Type.ADDED -> {
                        var adib = a.document.toObject(Adib::class.java)
                        adibList.add(adib)
                    }
                    DocumentChange.Type.MODIFIED -> {
//                        var adib = a.document.toObject(Adib::class.java)
//                        adibList.set(adib)
                    }
                    DocumentChange.Type.REMOVED -> {
                        var adib = a.document.toObject(Adib::class.java)
                        adibList.remove(adib)
                        appDatabase.isCheckedDao().deleteIsChecked(adib.name!!)
                    }
                }
                rvAdapter.notifyDataSetChanged()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}