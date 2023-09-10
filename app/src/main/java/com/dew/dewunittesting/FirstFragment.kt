package com.dew.dewunittesting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dew.dewunittesting.databinding.FragmentFirstBinding
import com.dew.dewunittesting.retrofit.RetrofitService

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var viewModel: MovieViewModel
    private val adapter = MovieAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MovieRepository(retrofitService)
        binding.recyclerview.adapter = adapter
        adapter?.callback = {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(mainRepository)
        ).get(MovieViewModel::class.java)

        viewModel.movieList.observe(viewLifecycleOwner) {
            adapter.setMovies(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })
        viewModel.getAllMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}