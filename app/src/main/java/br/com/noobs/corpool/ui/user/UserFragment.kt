package br.com.noobs.corpool.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.noobs.corpool.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserFragment : Fragment() {


    private lateinit var viewModel: UserViewModel
    private var user: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var profileImage: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var logoutBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_user, container, false)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser

        profileImage = inflate.findViewById(R.id.profileImage)
        user?.photoUrl?.let {
            profileImage.setImageURI(it)
        }

        profileEmail = inflate.findViewById(R.id.profileViewEmail)
        profileEmail.text = user?.email
        profileName = inflate.findViewById(R.id.profileViewName)
        profileName.text = user?.displayName

        logoutBtn = inflate.findViewById(R.id.btnLogout)

        logoutBtn.setOnClickListener {
            AuthUI.getInstance()
                .signOut(requireContext())

            Intent(requireContext(), UserActivity::class.java).apply {
                startActivity(this)
                requireActivity().finish()
            }
        }

        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}