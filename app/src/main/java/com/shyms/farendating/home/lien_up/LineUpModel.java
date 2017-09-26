package com.shyms.farendating.home.lien_up;

public interface LineUpModel {
    void getWindowSystem(String username,String password);
    void getSystem(String siteId);
    void getCheckQueue(String system);
    void getUserGet(String mobile,String system);
    void getGiveUp(String mobile,String system);
    void getUserStatus(String queue_id,String system);
}
