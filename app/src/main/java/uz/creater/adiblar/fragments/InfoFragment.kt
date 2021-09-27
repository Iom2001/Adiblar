package uz.creater.adiblar.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import uz.creater.adiblar.R
import uz.creater.adiblar.databinding.FragmentInfoBinding
import uz.creater.adiblar.models.Adib
import uz.creater.adiblar.room.database.AppDatabase
import uz.creater.adiblar.room.entity.IsChecked
import uz.creater.adiblar.utils.AppBarStateChangeListener
import uz.creater.adiblar.utils.CyrillicLatinConverter
import uz.creater.adiblar.utils.MyBounceInterpolator
import uz.creater.adiblar.utils.Settings

private const val ARG_PARAM1 = "param1"

class InfoFragment : Fragment() {

    private lateinit var isChecked: IsChecked
    private lateinit var adib: Adib
    private lateinit var binding: FragmentInfoBinding
    private var toolBarState = true
    private var searchState = false
    private lateinit var appDatabase: AppDatabase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            adib = it.getSerializable(ARG_PARAM1) as Adib
        }
    }

    @SuppressLint("RestrictedApi", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater)
        appDatabase = AppDatabase.getInstance(requireContext())
        isChecked = appDatabase.isCheckedDao().getIsCheckedById(adib.name!!)
        sharedPreferences =
            activity?.getSharedPreferences(Settings.PREF_NAME, Context.MODE_PRIVATE)!!
        val myAnim =
            AnimationUtils.loadAnimation(context, R.anim.bounce_anim)
        val interpolator = MyBounceInterpolator(0.1, 20.0)
        myAnim.interpolator = interpolator
        setView()
        if (isChecked.checked!!) {
            binding.saveImg.setImageResource(R.drawable.ic_selected_icon)
            binding.saveLayout.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_500
                )
            )
        }
        binding.appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(
                appBarLayout: AppBarLayout?,
                state: State?
            ) {
                when (state) {
                    State.COLLAPSED -> {
                        if (isChecked.checked!!) {
                            binding.saveImg.setImageResource(R.drawable.ic_selected_green_icon)
                            binding.saveLayout.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.item_back_color
                                )
                            )
                        }
                        toolBarState = false
                    }
                    State.EXPANDED -> {
                        if (searchState) {
                            binding.collapsingToolbar.title = adib.name
                        }
                    }
                    else -> {
                        if (!toolBarState) {
                            toolBarState = true
                            if (isChecked.checked!!) {
                                binding.saveImg.setImageResource(R.drawable.ic_selected_icon)
                                binding.saveLayout.setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.purple_500
                                    )
                                )
                            }
                        } else if (searchState) {
                            binding.collapsingToolbar.title = ""
                        }
                    }
                }
            }
        })
        binding.saveLayout.setOnClickListener {
            if (toolBarState) {
                if (isChecked.checked!!) {
                    binding.saveImg.setImageResource(R.drawable.ic_unselected_icon_info)
                    binding.saveLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.item_back_color
                        )
                    )
                    binding.saveImg.startAnimation(myAnim)
                    isChecked.checked = false
                    appDatabase.isCheckedDao().updateIsChecked(isChecked)
                } else {
                    binding.saveImg.setImageResource(R.drawable.ic_selected_icon)
                    binding.saveLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.purple_500
                        )
                    )
                    binding.saveImg.startAnimation(myAnim)
                    isChecked.checked = true
                    appDatabase.isCheckedDao().updateIsChecked(isChecked)
                }
            } else {
                if (isChecked.checked!!) {
                    binding.saveImg.setImageResource(R.drawable.ic_unselected_icon_info)
                    isChecked.checked = false
                    appDatabase.isCheckedDao().updateIsChecked(isChecked)
                } else {
                    binding.saveImg.setImageResource(R.drawable.ic_selected_green_icon)
                    isChecked.checked = true
                    appDatabase.isCheckedDao().updateIsChecked(isChecked)
                }
            }
        }
        binding.searchIcon.setOnClickListener {
            binding.saveToolbar.visibility = View.GONE
            binding.backToolbar.visibility = View.INVISIBLE
            binding.searchToolbar.visibility = View.VISIBLE
            searchState = true
            binding.appBarLayout.setExpanded(true, true)
        }

        binding.searchClose.setOnClickListener {
            if (binding.searchEdt.text.toString().isEmpty()) {
                binding.saveToolbar.visibility = View.VISIBLE
                binding.backToolbar.visibility = View.VISIBLE
                binding.searchToolbar.visibility = View.GONE
                searchState = false
                binding.collapsingToolbar.title = adib.name
                closeKerBoard()
            } else {
                binding.searchEdt.setText("")
            }
        }
        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.searchEdt.addTextChangedListener {
            val boolean = sharedPreferences.getBoolean(Settings.NIGHT_MODE_KEY, false)
            if (it!!.toString().length >= 2) {
                binding.writerInfoTv.setTextToHighlight(it.toString())
                binding.writerInfoTv.setCaseInsensitive(true)
                if (boolean) {
                    binding.writerInfoTv.setTextHighlightColor(
                        "#F44336"
                    )
                } else {
                    binding.writerInfoTv.setTextHighlightColor(
                        "#FFEB3B"
                    )
                }
                binding.writerInfoTv.highlight()
            } else {
                binding.writerInfoTv.setTextToHighlight(it.toString())
                binding.writerInfoTv.setCaseInsensitive(true)
                if (boolean) {
                    binding.writerInfoTv.setTextHighlightColor("#25303F")
                } else {
                    binding.writerInfoTv.setTextHighlightColor("#ffffff")
                }
                binding.writerInfoTv.highlight()
            }
        }
        return binding.root
    }

    private fun setView() {
        if (Settings.isKiril) {
            binding.collapsingToolbar.title = CyrillicLatinConverter.latinToCyrillic(adib.name)
            binding.writerInfoTv.text = CyrillicLatinConverter.latinToCyrillic(adib.desc)
        } else {
            binding.collapsingToolbar.title = adib.name
            binding.writerInfoTv.text = adib.desc
        }
        binding.lifeTime.text = adib.years
        Picasso.get().load(adib.imageUri).placeholder(R.drawable.loadplaceholder)
            .error(R.drawable.errorplaceholder).into(binding.writerImg)
    }

    private fun closeKerBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}