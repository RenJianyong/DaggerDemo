package com.rjy.dagger.android.main

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.widget.RadioGroup
import com.rjy.dagger.R
import com.rjy.dagger.android.main.fragment.cart.CartFragment
import com.rjy.dagger.android.main.fragment.commodity.CommodityFragment
import com.rjy.dagger.android.main.fragment.home.HomeFragment
import com.rjy.dagger.android.main.fragment.user.UserFragment
import com.rjy.dagger.base.fragment.BaseFragment
import com.rjy.dagger.base.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpActivity<MainMvpPresenter>(), MainMvpView, RadioGroup.OnCheckedChangeListener {


    var currentFragment:BaseFragment? = null

    var fragmentList:Array<BaseFragment> = arrayOf(
        HomeFragment(),
        CommodityFragment(),
        CartFragment(),
        UserFragment()
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNavigationBarRg.setOnCheckedChangeListener(this)
        mHomeRb.isChecked = true

    }

    override fun onCheckedChanged(group: RadioGroup?, checkId: Int) {
        if (checkId == R.id.mHomeRb){
            //首页
            add(fragmentList[0],R.id.fragment,"home")
        }else if(checkId == R.id.mConnodityRb){
            //商品分类
            add(fragmentList[1],R.id.fragment,"commodity")
        }else if(checkId == R.id.mCartRb){
            //购物车
            add(fragmentList[2],R.id.fragment,"cart")
        }else{
            //用户信息
            add(fragmentList[3],R.id.fragment,"user")
        }
    }


    override fun initPresenter(): MainMvpPresenter {
        val mvpPresenter = MainMvpPresenter(this)
        return mvpPresenter
    }


    fun add(fragment: BaseFragment, id: Int, tag: String) {
        var fragment = fragment
        val fragmentManager =  getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        //优先检查，fragment是否存在，避免重叠
        val tempFragment = fragmentManager.findFragmentByTag(tag)
        if (tempFragment != null) {
            fragment = tempFragment as BaseFragment
        }
        if (fragment.isAdded()) {
            addOrShowFragment(fragmentTransaction, fragment, id, tag)
        } else {
            if (currentFragment != null && currentFragment!!.isAdded()) {
                fragmentTransaction.hide(currentFragment).add(id, fragment, tag).commit()
            } else {
                fragmentTransaction.add(id, fragment, tag).commit()
            }
            currentFragment = fragment
        }
    }

    /**
     * 添加或者显示 fragment
     *
     * @param fragment
     */
    private fun addOrShowFragment(transaction: FragmentTransaction, fragment: BaseFragment, id: Int, tag: String) {
        if (currentFragment === fragment)
            return
        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(id, fragment, tag).commit()
        } else {
            transaction.hide(currentFragment).show(fragment).commit()
        }
        currentFragment!!.setUserVisibleHint(false)
        currentFragment = fragment
        currentFragment!!.setUserVisibleHint(true)
    }

}
