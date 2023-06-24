package com.example.modul11firebase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul11firebase.R
import com.example.modul11firebase.adapter.HomeAdapter
import com.example.modul11firebase.databinding.FragmentHomeBinding
import com.example.modul11firebase.model.Mahasiswa
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private lateinit var databaseRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= HomeAdapter(ArrayList())
        binding.rvUser.adapter=adapter
        binding.rvUser.layoutManager=LinearLayoutManager(requireContext())

        databaseRef = FirebaseDatabase.getInstance().getReference("mahasiswa")
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mahasiswaList = ArrayList<Mahasiswa>()
                for (snapshot in dataSnapshot.children){
                    val mahasiswa = snapshot.getValue(Mahasiswa::class.java)
                    mahasiswa?.let {
                        mahasiswaList.add(it)
                    }
                    adapter.setData(mahasiswaList)
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tambahFragment)
        }
    }
}