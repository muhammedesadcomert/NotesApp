package com.muhammedesadcomert.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.muhammedesadcomert.notes.adapter.NoteAdapter
import com.muhammedesadcomert.notes.databinding.FragmentNoteListBinding

class NoteListFragment : Fragment() {

    private lateinit var binding: FragmentNoteListBinding

    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).database.noteDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteAdapter {
            val action =
                NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment()
            val bundle = bundleOf("id" to it.id)
            this.findNavController().navigate(action.actionId, bundle)
        }
        binding.recyclerView.adapter = adapter
        viewModel.allNotes.observe(this.viewLifecycleOwner) { notes ->
            notes.let {
                adapter.submitList(it)
            }
        }

        binding.apply {
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.setHasFixedSize(true)

            fab.setOnClickListener {
                val action =
                    NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment()
                findNavController().navigate(action)
            }
        }
    }
}