package com.example.productfinder;

public class ShelfClass {
    private int shelfID;
    private String aisleNo;
    private String side;
    private int shelfNo;
    private int xpos;
    private int ypos;
    private int node;

    public ShelfClass() {
        shelfID=0;
        aisleNo="";
        side="";
        shelfNo=0;
        xpos =0;
        ypos = 0;
        node = 0;
    }
    public ShelfClass(int shelfID, String aisleNo, String side, int shelfNo, int xpos, int ypos, int node) {
        this.shelfID = shelfID;
        this.aisleNo = aisleNo;
        this.side = side;
        this.shelfNo = shelfNo;
        this.xpos = xpos;
        this.ypos = ypos;
        this.node = node;
    }

    public int getShelfID() {
        return shelfID;
    }

    public void setShelfID(int shelfID) {
        this.shelfID = shelfID;
    }

    public String getAisleNo() {
        return aisleNo;
    }

    public void setAisleNo(String aisleNo) {
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

    public int getNode() {return node;}

    public void setNode(int node) { this.node = node;}
}
