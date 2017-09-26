package com.shyms.farendating.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;


import java.util.List;

/**
 * 基础模块
 */
public class HomeAndLineUpFragment {
    private List<Fragment> fragments; // 一个tab页面对应一个Fragment

    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int fragmentContentId; // Activity中所要被替换的区域的id
    private int currentTab; // 当前Tab页面索引

    public HomeAndLineUpFragment(FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;
        // 默认显示第一页
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(fragmentContentId, fragments.get(0));
        ft.addToBackStack(fragments.get(0).getClass().getName());
        ft.commit();
    }

    public void onCheckedChanged(int index) {
            Fragment fragment = fragments.get(index);
            FragmentTransaction ft = obtainFragmentTransaction(index);
            getCurrentFragment().onPause(); // 暂停当前tab
            getCurrentFragment().onStop(); // 停止当前tab
            if (fragment.isAdded()) {
                fragment.onStart(); // 启动目标tab的onStart()
                fragment.onResume(); // 启动目标tab的onResume()
            } else {
                ft.addToBackStack(fragment.getClass().getName());
                ft.add(fragmentContentId, fragment);
            }
            showTab(index); // 显示目标tab
            ft.commit();
    }

    /**
     * 切换tab
     *
     * @param idx
     */
    private void showTab(int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);
            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = idx; // 更新目标tab为当前tab
    }

    /**
     * 获取一个带动画的FragmentTransaction
     *
     * @param index
     * @return
     */
    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // 设置切换动画
//        if (index > currentTab) {
//            ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
//        } else {
//            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
//        }
        return ft;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

}