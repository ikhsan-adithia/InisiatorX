package app.inisiator.myapplication.tab_peluang

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import app.inisiator.myapplication.R

private val peluangTabTitles = arrayOf(
    R.string.tab_artikel,
    R.string.tab_kegiatan
//    R.string.tab_kompetensi
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = ArtikelFragment()
            1 -> fragment = KegiatanFragment()
//            2 -> fragment = KompetensiFragment()
        }

        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(peluangTabTitles[position])
    }

    override fun getCount(): Int {
        return 2
//        return 3
    }
}