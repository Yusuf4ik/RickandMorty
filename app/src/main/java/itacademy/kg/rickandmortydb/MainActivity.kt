package itacademy.kg.rickandmortydb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import itacademy.kg.rickandmortydb.databinding.ActivityMainBinding
import itacademy.kg.rickandmortydb.main.ui.Characters
import itacademy.kg.rickandmortydb.main.ui.Episodes
import itacademy.kg.rickandmortydb.main.ui.Locations
import itacademy.kg.rickandmortydb.main.ui.Search

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment(Characters())
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.locations -> setFragment(Locations())
                R.id.characters -> setFragment(Characters())
                R.id.episodes -> setFragment(Episodes())
                R.id.search -> setFragment(Search())

            }
            true
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }



    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}