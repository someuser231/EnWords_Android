package com.kew.enwords

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    val home = HomeFr()
    val dict = DictionaryFr()
    val frManager = supportFragmentManager
    var frTransaction: FragmentTransaction = frManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        frTransaction = frManager.beginTransaction()
        frTransaction.add(R.id.frame, home)
        frTransaction.commit()

        val txt_toolbar: TextView = findViewById(R.id.txt_toolbar)
        txt_toolbar.text = "Home"

        val drawerLayout: DrawerLayout = findViewById(R.id.main)

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { it ->
            drawerLayout.closeDrawer(GravityCompat.START)
            when(it.itemId) {
                R.id.menu_home -> {
                    frTransaction = frManager.beginTransaction()
                    frTransaction.replace(R.id.frame, home)
                    frTransaction.commit()
                    txt_toolbar.text = "Home"


                    return@setNavigationItemSelectedListener true
                }
                R.id.menu_dict -> {
                    frTransaction = frManager.beginTransaction()
                    frTransaction.replace(R.id.frame, dict)
                    frTransaction.commit()
                    txt_toolbar.text = "Dictionary"

                    return@setNavigationItemSelectedListener true
                }
                else -> true
            }
        }

        val btnNavMenu: ImageButton = findViewById(R.id.btn_nav_menu)
        btnNavMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}