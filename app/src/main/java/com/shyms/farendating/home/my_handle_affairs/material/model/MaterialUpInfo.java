package com.shyms.farendating.home.my_handle_affairs.material.model;

/**
 * 材料上传界面
 * Created by hokas on 2015/8/27.
 */
public class MaterialUpInfo {


    /**
     * Id : 111430
     * Name : 《分公司名称预先核准通知书》
     * Count : 1
     * Class : 图片材料
     * Nature : 收原件
     * is_Upload : 0
     * is_Complete : 0
     * only_number : GS1509080040
     */

    private String Id;
    private String Name;
    private String Count;
    private String Class;
    private String Nature;
    private String is_Upload;
    private String is_Complete;
    private String only_number;
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setCount(String Count) {
        this.Count = Count;
    }

    public void setClass(String Class) {
        this.Class = Class;
    }

    public void setNature(String Nature) {
        this.Nature = Nature;
    }

    public void setIs_Upload(String is_Upload) {
        this.is_Upload = is_Upload;
    }

    public void setIs_Complete(String is_Complete) {
        this.is_Complete = is_Complete;
    }

    public void setOnly_number(String only_number) {
        this.only_number = only_number;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getCount() {
        return Count;
    }

    public String getsClass() {
        return Class;
    }

    public String getNature() {
        return Nature;
    }

    public String getIs_Upload() {
        return is_Upload;
    }

    public String getIs_Complete() {
        return is_Complete;
    }

    public String getOnly_number() {
        return only_number;
    }

    @Override
    public String toString() {
        return "MaterialUpInfo{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Count='" + Count + '\'' +
                ", Class='" + Class + '\'' +
                ", Nature='" + Nature + '\'' +
                ", is_Upload='" + is_Upload + '\'' +
                ", is_Complete='" + is_Complete + '\'' +
                ", only_number='" + only_number + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
