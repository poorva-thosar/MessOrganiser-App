package com.example.poorva.messorganizer2;

public class ContactsForVegetable {

    private String srno,itemName,taste,cost;

    public ContactsForVegetable(String srno,String itemName,String taste)
    {
        this.setSrno(srno);
        this.setItemName(itemName);
        this.setTaste(taste);
    }


    public ContactsForVegetable(String srno, String itemName, String taste, String cost) {
        this.srno = srno;
        this.itemName = itemName;
        this.taste = taste;
        this.cost = cost;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
