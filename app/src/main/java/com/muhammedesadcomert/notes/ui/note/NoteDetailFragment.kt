package com.muhammedesadcomert.notes.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muhammedesadcomert.notes.R
import com.muhammedesadcomert.notes.data.local.model.Note
import com.muhammedesadcomert.notes.databinding.FragmentNoteDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var note: Note

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenuProvider()

        /**
         * It is checked to see if there is an incoming id.
         * Otherwise, title and text will not be filled.
         */
        val id = requireArguments().getInt("id")
        viewModel.retrieveNote(id)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.note.collect { selectedNote ->
                selectedNote?.let {
                    note = selectedNote
                    bind(note)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initMenuProvider() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.item_detail_action_bar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.delete -> {
                        if (noteIsInitialized())
                            viewModel.deleteNote(note)
                    }
                    // on back pressed, go back to the note list fragment
                    else -> {
                        saveEntry()
                    }
                }
                findNavController().navigateUp()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun bind(note: Note) {
        binding.apply {
            noteTitle.setText(note.title)
            noteText.setText(note.text)
        }
    }

    // Add new note or update note
    private fun saveEntry() {
        val noteTitle = binding.noteTitle.text.toString()
        val noteText = binding.noteText.text.toString()

        // If the title and text are blank, the entry will not be saved.
        if (viewModel.isEntryValid(noteTitle, noteText)) {
            if (noteIsInitialized()) {
                viewModel.updateNote(note.id, noteTitle, noteText)
            } else {
                viewModel.addNewNote(noteTitle, noteText)
            }
        }
    }

    private fun noteIsInitialized() = ::note.isInitialized
}