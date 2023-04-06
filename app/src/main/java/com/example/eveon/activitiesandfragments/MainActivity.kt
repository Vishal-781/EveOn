package com.example.eveon.activitiesandfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

import com.example.eveon.R
import com.example.eveon.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
      private lateinit var navigation:MeowBottomNavigation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         navigation =findViewById(R.id.bottom_navigation_bar)

        navigation.add(MeowBottomNavigation.Model(1,R.drawable.house_real))
        navigation.add(MeowBottomNavigation.Model(2,R.drawable.placard4))
        navigation.add(MeowBottomNavigation.Model(3,R.drawable.lost_and_foundreal))
        navigation.add(MeowBottomNavigation.Model(4,R.drawable.user1))

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
        navigation.show(1,true)
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






}