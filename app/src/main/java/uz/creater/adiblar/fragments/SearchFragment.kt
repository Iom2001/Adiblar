package uz.creater.adiblar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import uz.creater.adiblar.R
import uz.creater.adiblar.adapters.RvAdapter
import uz.creater.adiblar.databinding.FragmentSearchBinding
import uz.creater.adiblar.models.Adib
import uz.creater.adiblar.room.database.AppDatabase

private const val ARG_PARAM1 = "param1"

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private var searchType: Boolean? = null
    private lateinit var rvAdapter: RvAdapter
    private lateinit var adibList: ArrayList<Adib>
    private lateinit var firebaseFirestore: FirebaseFirestore
    private val categoryList =
        arrayListOf("Mumtoz adabiyot", "O'zbek adabiyoti", "Jahon adabiyoti")
    private lateinit var nameList: ArrayList<String>
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchType = it.getBoolean(ARG_PARAM1)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
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
        if (searchType != null) {
            loadIsCheckedData()
            if (nameList.isNotEmpty()) {
                for (param in categoryList) {
                    firebaseFirestore.collection(param).whereIn("name", nameList)
                        .addSnapshotListener { snapshots, e ->
                            if (e != null) {
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
                                    }
                                }
                            }
                            rvAdapter.notifyDataSetChanged()
                        }
                }
            }
        } else {
            for (param in categoryList) {
                firebaseFirestore.collection(param)
                    .addSnapshotListener { snapshots, e ->
                        if (e != null) {
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
                                }
                            }
                        }
                        rvAdapter.notifyDataSetChanged()
                    }
            }
        }
        binding.searchRv.adapter = rvAdapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                rvAdapter.getFilter(newText!!)
                return false
            }
        })
        return binding.root
    }

    private fun loadIsCheckedData() {
        nameList = ArrayList()
        nameList.addAll(appDatabase.isCheckedDao().getAllIsCheckedId(true))
    }
}