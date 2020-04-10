package app.inisiator.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import app.inisiator.myapplication.tab_peluang.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class Peluang : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root =  inflater.inflate(R.layout.fragment_peluang, container, false)

        val sectionsPagerAdapterPeluang = SectionsPagerAdapter(context!!, childFragmentManager)
        val viewPagerPeluang = root.findViewById<ViewPager>(R.id.view_pager_peluang)
        viewPagerPeluang.adapter = sectionsPagerAdapterPeluang
        val tabsPeluang = root.findViewById<TabLayout>(R.id.tabs_peluang)
        tabsPeluang.setupWithViewPager(viewPagerPeluang)

        return root
    }
}