package com.example.productfinder;

public class ShelfClass {
    private int shelfID;
    private int aisleNo;
    private String side;
    private int shelfNo;
    private int xpos;
    private int ypos;

    public ShelfClass() {
        shelfID=0;
        aisleNo=0;
        side="";
        shelfNo=0;
        xpos =0;
        ypos = 0;
    }
    public ShelfClass(int shelfID, int aisleNo, String side, int shelfNo, int xpos, int ypos) {
        this.shelfID = shelfID;
        this.aisleNo = aisleNo;
        this.side = side;
        this.shelfNo = shelfNo;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public int getShelfID() {
        return shelfID;
    }

    public void setShelfID(int shelfID) {
        this.shelfID = shelfID;
    }

    public int getAisleNo() {
        return aisleNo;
    }

    public void setAisleNo(int aisleNo) {
        this.aisleNo = aisleNo;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(int shelfNo) {
        this.shelfNo = shelfNo;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }
}
