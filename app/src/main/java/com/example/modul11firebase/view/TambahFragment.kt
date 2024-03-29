package com.example.modul11firebase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.modul11firebase.databinding.FragmentTambahBinding
import com.example.modul11firebase.model.Mahasiswa
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class TambahFragment : Fragment() {
    lateinit var binding: FragmentTambahBinding
    lateinit var databaseRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRef = FirebaseDatabase.getInstance().getReference("mahasiswa")
        binding.btnTambah.setOnClickListener {
            addData()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun addData() {
        val nim = binding.inputNim.text.toString()
        val nama = binding.inputNama.text.toString()
        val kelas = binding.inputTelepon.text.toString()
        val mhsId = databaseRef.push().key!!
        val mahasiswaData = Mahasiswa(mhsId, nim, nama, kelas)
        databaseRef.child(mhsId!!).setValue(mahasiswaData).addOnCompleteListener() {
            Toast.makeText(requireContext(), "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigateUp()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Data Gagal Ditambahkan", Toast.LENGTH_SHORT)
                .show()
        }
    }
}