package com.decagon.decafit.common.dashboard

import android.app.ActionBar
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.decagon.decafit.MainActivity
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.utils.showLogOutDialog
import com.decagon.decafit.databinding.ActivityDashBoardBinding
import com.decagon.decafit.databinding.LogoutDialogLayoutBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashBoardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityDashBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var logoutDialogLayoutBinding: LogoutDialogLayoutBinding
    private lateinit var  logoutDialog: AlertDialog
    private lateinit var name: String
    private var pressedTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val actionBar: ActionBar? = this.actionBar
//        actionBar?.title = "Dashboard"

        _binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        navController = findNavController(com.decagon.decafit.R.id.host_dashboard)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        //setupActionBarWithNavController(navController, appBarConfiguration)

        binding.drawerNavView.setupWithNavController(navController)

        logoutDialogLayoutBinding = LogoutDialogLayoutBinding.inflate(layoutInflater)
        logoutDialog = showLogOutDialog(this, logoutDialogLayoutBinding,resources, {logOut()})

        binding.drawerLogoutConstraintLayout.setOnClickListener {
            logoutDialog.show()
        }

        name = Preference.getName("name").toString()

        val headerView: View = binding.drawerNavView.inflateHeaderView(com.decagon.decafit.R.layout.drawer_header_layout)
        var profileName = headerView.findViewById<TextView>(com.decagon.decafit.R.id.drawer_header_user_full_name_textView)
        profileName.text = name

        var mDrawerLayout: DrawerLayout = findViewById(com.decagon.decafit.R.id.drawer_layout)
        var closeDrawer= headerView.findViewById<ImageView>(com.decagon.decafit.R.id.drawer_header_close_icon_imageView)
        closeDrawer.setOnClickListener {
            mDrawerLayout.closeDrawers()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(com.decagon.decafit.R.id.host_dashboard)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun logOut(){
        Preference.logOut()
        navigateToDashBoard()
    }

    private fun navigateToDashBoard() {
        Intent(
            this,
            MainActivity::class.java
        )
            .also { loginFragment-> startActivity(loginFragment) }

    }

    fun openCloseNavigationDrawer(view: View) {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}