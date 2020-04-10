package app.inisiator.myapplication.tab_notifikasi

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import app.inisiator.myapplication.R

private val peluangTabTitles = arrayOf(
    R.string.tab_notifikasi,
    R.string.tab_transaksi
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager):
    FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = NotifikasiFragment()
            1 -> fragment = TransaksiFragment()
        }

        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(peluangTabTitles[position])
    }

    override fun getCount(): Int {
        return 2
    }
}