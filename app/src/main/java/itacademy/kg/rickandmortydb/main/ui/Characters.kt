package itacademy.kg.rickandmortydb.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import itacademy.kg.rickandmortydb.R
import itacademy.kg.rickandmortydb.data.Result
import itacademy.kg.rickandmortydb.data.Utils
import itacademy.kg.rickandmortydb.databinding.FragmentCharactersBinding


class Characters : Fragment(), OnMovieClickListener{

    private lateinit var adapter: Adapter
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CharactersViewModel
    private var page = 2
    private var loading = true
    private var previousTotal = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)

        viewModel.liveDataCharactersPg1.observe(viewLifecycleOwner, {
//            start(it, true, binding.recyclerViewChar)
            showList(it)

        })


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun start(movies: List<Result>, boolean: Boolean, recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        adapter = Adapter(requireContext(), movies, this)
        recyclerView.adapter = adapter
    }

    private fun setFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun showList(list: List<Result>) {
        binding.recyclerViewChar.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount =
                        (binding.recyclerViewChar.layoutManager as GridLayoutManager).childCount
                    val totalItemCount =
                        (binding.recyclerViewChar.layoutManager as GridLayoutManager).itemCount
                    val firstVisibleItem =
                        (binding.recyclerViewChar.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

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
                                    viewModel.getNewCharacters(page,2)
                                    page++
                                    loading = true
                                }

                                .show()
                        }

                    }else{
                        view?.let {
                            Snackbar.make(it, "Load previous page?", Snackbar.LENGTH_LONG)
                                .setAction("Yes") {
                                    viewModel.getNewCharacters(page,1)
                                    page--
                                    loading = false
                                }

                                .show()
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            }
        })

        binding.recyclerViewChar.layoutManager = GridLayoutManager(
            view?.context, 2,
            GridLayoutManager.VERTICAL, false
        )
        
        binding.recyclerViewChar.adapter = context?.let { Adapter(it, list, this) }
    }

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
}