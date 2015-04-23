package com.example.makeshift.merrygoround;

/**
 * Created by irving on 4/20/15.
 */
public class ListItem {
    private String name;
    private String phoneNo;


    public ListItem(String name, String phoneNo) {
        this.name = name;
        this.setPhoneNo(phoneNo);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo= phoneNo;
    }



}