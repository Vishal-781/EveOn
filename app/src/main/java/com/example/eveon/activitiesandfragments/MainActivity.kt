package com.example.eveon.activitiesandfragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

import com.example.eveon.R
import com.example.eveon.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import pages.Login


class MainActivity : AppCompatActivity() {
      private lateinit var navigation:MeowBottomNavigation
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         navigation =findViewById(R.id.bottom_navigation_bar)
        val   toolbar_homefragment1=findViewById<Toolbar>(R.id.toolbar_homefragment)
        toolbar_homefragment1.title = resources.getString(R.string.app_name)

        toolbar_homefragment1.setTitleTextColor(resources.getColor(R.color.black_1))
        setSupportActionBar(toolbar_homefragment1)
        navigation.add(MeowBottomNavigation.Model(1,R.drawable.house_real))
        navigation.add(MeowBottomNavigation.Model(2,R.drawable.placard4))
        navigation.add(MeowBottomNavigation.Model(3,R.drawable.lost_and_foundreal))
        navigation.add(MeowBottomNavigation.Model(4,R.drawable.user1))
        navigation.show(1,true)
      addfragment(HomeFragment(), 0)

        navigation.setOnClickMenuListener {
            when(it.id)
            {
                1-> addfragment(HomeFragment(),1)
                2-> addfragment(MyEvents_Fragment(),1)
                3-> addfragment(unknownfragment(),1)
                4-> addfragment(ProfileFragment(),1)
                else->{

                }
            }
            true


        }
        navigation.setOnShowListener {
            when(it.id)
            {
                1-> navigation.setBackgroundColor(resources.getColor(R.color.coloraccent))

                2-> navigation.setBackgroundColor(resources.getColor(com.jpardogo.android.googleprogressbar.library.R.color.green))
                3-> addfragment(unknownfragment(),1)
                4-> addfragment(ProfileFragment(),1)
                else->{

                }
            }
            true
        }

//        binding.bottomNavigationBar.itemIconTintList=null
//
//        addfragment(HomeFragment(), 0)
//        binding.bottomNavigationBar.setOnItemSelectedListener {
//       when(it.itemId)
//       {
//           R.id.home123-> addfragment(HomeFragment(),1)
//           R.id.myevent ->addfragment(MyEvents_Fragment(),1)
//           R.id.fragment2 ->addfragment(unknownfragment(),1)
//           R.id.profile123->addfragment(ProfileFragment(),1)
//
//           else->{
//
//           }
//
//       }
//            true
//        }







    }


    private fun addfragment(fragments: Fragment, Flags: Int) {
        // this is a service call
        val fragmentmanager = supportFragmentManager
        val fragmentTransaction = fragmentmanager.beginTransaction()
        if (Flags == 1) {
            fragmentTransaction.replace(R.id.framelayout, fragments)
//
        } else {
            fragmentTransaction.add(R.id.framelayout, fragments)

        }

        fragmentTransaction.commit()
    }

    override fun onBackPressed() {


    super.onBackPressed()
    this.finishAffinity()


//        if (binding.bottomNavigationBar.selectedItemId == R.id.home123  ) {
//            super.onBackPressed()
//            this.finishAffinity()
//
//
//        } else {
//            binding.bottomNavigationBar.selectedItemId = R.id.home123
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout->{
                mAuth.signOut()
                startActivity(Intent(this, Login::class.java))
            }
            R.id.about->{

            }
        }
        return super.onOptionsItemSelected(item)
    }
}