package itacademy.kg.rickandmortydb.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import itacademy.kg.rickandmortydb.R
import itacademy.kg.rickandmortydb.data.Result
import itacademy.kg.rickandmortydb.data.ResultForEp
import itacademy.kg.rickandmortydb.data.ResultForLocations
import itacademy.kg.rickandmortydb.data.Utils
import itacademy.kg.rickandmortydb.databinding.FragmentSearchBinding


class Search : Fragment(), OnMovieClickListener, AdapterForEp.OnEpListener,
    AdapterForLoc.OnLocClickListener {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterForSpinner: ArrayAdapter<String>
    private lateinit var adapter: Adapter
    private lateinit var adapterForEp: AdapterForEp
    private lateinit var adapterForLoc: AdapterForLoc
    private lateinit var viewModel: SearchViewModel
    lateinit var status: String
    lateinit var gender: String
    lateinit var dimension: String
    lateinit var type: String
    lateinit var selectedItemSt: String
    private lateinit var SearchList: MutableList<Result>
    private lateinit var SearchListEp: MutableList<ResultForEp>
    private lateinit var SearchListLoc: MutableList<ResultForLocations>
    private var page = 2
    private var loading = true
    private var previousTotal = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.liveDataFindChar.observe(viewLifecycleOwner, {
            SearchList = mutableListOf()
            SearchList.addAll(it)
            start(SearchList, true, binding.recyclerForSearch)
        })
        viewModel.liveDataFindEp.observe(viewLifecycleOwner, {
            SearchListEp = mutableListOf()
            SearchListEp.addAll(it)
            startForEp(SearchListEp, true, binding.recyclerForSearch)
        })
        viewModel.liveDataFindLoc.observe(viewLifecycleOwner, {
            SearchListLoc = mutableListOf()
            SearchListLoc.addAll(it)
            startForLoc(SearchListLoc, true, binding.recyclerForSearch)
        })
//        searchView()
        sendSearchResponse()

        val spinnerData = mutableListOf<String>()
        spinnerData.add("Episodes")
        spinnerData.add("Locations")
        spinnerData.add("Characters")
        adapterForSpinner = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            spinnerData
        )
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapterForSpinner
        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItemSt = parent.selectedItem.toString()
                if (selectedItemSt == "Episodes") {
                    Toast.makeText(context, "Add your text for searching", Toast.LENGTH_SHORT)
                        .show()
                } else if (selectedItemSt == "Characters") {
                    //alert dialog for char
                    openAddDialog()
                } else if (selectedItemSt == "Locations") {
                    //alert dialog for locations
                    openDialogLoc()

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun openAddDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Choose params")
            .setMessage("Please choose all params you need")
            .setIcon(R.drawable.ic_baseline_filter_alt_24)


        val view = LayoutInflater.from(context).inflate(R.layout.dialog_find, null)
        dialog.setView(view)
        dialog.setPositiveButton("Choose params") { _, _ ->

            if (view.findViewById<RadioButton>(R.id.alive).isChecked) {
                status = view.findViewById<RadioButton>(R.id.alive).text.toString()
            } else if (view.findViewById<RadioButton>(R.id.dead).isChecked) {
                status = view.findViewById<RadioButton>(R.id.dead).text.toString()
            } else if (view.findViewById<RadioButton>(R.id.unknown).isChecked) {
                status = view.findViewById<RadioButton>(R.id.unknown).text.toString()
            }
            if (view.findViewById<RadioButton>(R.id.male).isChecked) {
                gender = view.findViewById<RadioButton>(R.id.male).text.toString()
            } else if (view.findViewById<RadioButton>(R.id.female).isChecked) {
                gender = view.findViewById<RadioButton>(R.id.female).text.toString()
            } else if (view.findViewById<RadioButton>(R.id.genderless).isChecked) {
                gender = view.findViewById<RadioButton>(R.id.genderless).text.toString()
            } else if (view.findViewById<RadioButton>(R.id.unknownGen).isChecked) {
                gender = view.findViewById<RadioButton>(R.id.unknownGen).text.toString()
            }


        }
        dialog.setNegativeButton("Cancel") { _, _ ->

        }
        dialog.show()
    }

    private fun openDialogLoc() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Choose params")
            .setMessage("Please choose all params you need")
            .setIcon(R.drawable.ic_baseline_filter_alt_24)


        val view = LayoutInflater.from(context).inflate(R.layout.dialog_location, null)
        dialog.setView(view)
        dialog.setPositiveButton("Choose params") { _, _ ->

            if (!view.findViewById<EditText>(R.id.type_dialogue)
                    .equals("") && !view.findViewById<EditText>(R.id.dimension_dialogue).equals("")
            ) {
                type = view.findViewById<EditText>(R.id.type_dialogue).toString()
                dimension = view.findViewById<EditText>(R.id.dimension_dialogue).toString()

            }


        }
        dialog.setNegativeButton("Cancel") { _, _ ->

        }
        dialog.show()
    }

    private fun start(movies: List<Result>, boolean: Boolean, recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(parentFragment?.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        adapter = Adapter(requireContext(), movies, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun startForEp(
        movies: List<ResultForEp>,
        boolean: Boolean,
        recyclerView: RecyclerView
    ) {
        recyclerView.layoutManager =
            LinearLayoutManager(parentFragment?.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        adapterForEp = AdapterForEp(requireContext(), movies, this)
        recyclerView.adapter = adapterForEp
        adapterForEp.notifyDataSetChanged()

    }

    private fun startForLoc(
        movies: List<ResultForLocations>,
        boolean: Boolean,
        recyclerView: RecyclerView
    ) {
        recyclerView.layoutManager =
            LinearLayoutManager(parentFragment?.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        adapterForLoc = AdapterForLoc(requireContext(), movies, this)
        recyclerView.adapter = adapterForLoc
        adapterForLoc.notifyDataSetChanged()

    }

    private fun sendSearchResponse() {
        binding.searchView.setOnClickListener {
            Toast.makeText(context, "Search button clicked", Toast.LENGTH_SHORT).show()
            if (selectedItemSt == "Episodes") {
                if (binding.searchText.toString().isNotEmpty()) {
                    viewModel.findEp(binding.searchText.text.toString())
                }
            } else if (selectedItemSt == "Characters") {
                if (binding.searchText.toString()
                        .isNotEmpty() && gender.isNotEmpty() && status.isNotEmpty()
                ) {
                    Log.i("MyB", binding.searchText.text.toString()+gender.toString()+status.toString())
                    viewModel.findCharacter(binding.searchText.text.toString(), gender, status)
                }
            } else if (selectedItemSt == "Locations") {
                if (binding.searchText.toString()
                        .isNotEmpty() && type.isNotEmpty() && dimension.isNotEmpty()
                ) {

                    viewModel.findLocations(binding.searchText.text.toString(), type, dimension)
                }

            }

        }

    }

//    private fun searchView() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
////                binding.searchView.clearFocus()
//                if (query.toString().isNotEmpty() && gender.isNotEmpty() && status.isNotEmpty()) {
//                    viewModel.findCharacter(page=1, query.toString(), gender, status)
//                } else {
//                    Toast.makeText(
//                        context,
//                        "Please, enter the text and choose all params",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
////                binding.searchView.clearFocus()
//
//                return false
//            }
//
//        })
//    }

    override fun onItemClick(position: Int, movie: List<Result>) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.KEY_PERSON, movie[position])
        val fragment = DescFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun showList(list: List<Result>, name: String) {
        binding.recyclerForSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount =
                        (binding.recyclerForSearch.layoutManager as GridLayoutManager).childCount
                    val totalItemCount =
                        (binding.recyclerForSearch.layoutManager as GridLayoutManager).itemCount
                    val firstVisibleItem =
                        (binding.recyclerForSearch.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        view?.let {
                            Snackbar.make(it, "Load next page?", Snackbar.LENGTH_LONG)
                                .setAction("Yes") {
                                    viewModel.findCharacter(name, gender, status)
                                    page++
                                    loading = true
                                }

                                .show()
                        }

                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            }
        })

        binding.recyclerForSearch.layoutManager = GridLayoutManager(
            view?.context, 2,
            GridLayoutManager.VERTICAL, false
        )

        binding.recyclerForSearch.adapter = context?.let { Adapter(it, list, this) }
    }

    override fun onDescClick(position: Int, movie: List<ResultForEp>) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.KEY_EPISODE, movie[position])
        val fragment = DescFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun OnItemLocClick(position: Int, movie: List<ResultForLocations>) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.KEY_LOCATION, movie[position])
        val fragment = DescFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}