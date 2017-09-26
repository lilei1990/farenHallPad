package com.shyms.farendating.home.user_info;


public class UserInfoPresenterImp implements UserInfoPresenter{
    private UserInfoView view;

    public UserInfoPresenterImp(UserInfoView view) {
        this.view = view;
    }

    @Override
    public void switchFragment(int index) {
        switch (index){
            case 0:                break;
            case 1:                break;
            case 2:                break;
            case 3:                break;
            case 4:                break;
            case 5:                break;
        }
    }
}
