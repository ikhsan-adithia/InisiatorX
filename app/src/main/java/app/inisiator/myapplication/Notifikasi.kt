package app.inisiator.myapplication

import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import app.inisiator.myapplication.tab_notifikasi.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class Notifikasi : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_notifikasi, container, false)

        val sectionsPagerAdapterPeluang = SectionsPagerAdapter(context!!, childFragmentManager)
        val viewPagerPeluang = root.findViewById<ViewPager>(R.id.view_pager_notifikasi)
        viewPagerPeluang.adapter = sectionsPagerAdapterPeluang
        val tabsPeluang = root.findViewById<TabLayout>(R.id.tabs_notifikasi)
        tabsPeluang.setupWithViewPager(viewPagerPeluang)

        return root
    }
}
