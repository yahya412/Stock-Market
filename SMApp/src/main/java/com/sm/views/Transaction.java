package com.sm.views;
public class Transaction {

private Integer id;
private String name;
private Integer num;
private Double sprice;
private Double pprice;
private Double totla;


public Transaction(Integer x,String y,Integer z,Double s,Double p){
this.id=x;
this.name=y;
this.num=z;
this.sprice=s;
this.pprice=p;
this.totla=this.getTotla();
}
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the num
     */
    public Integer getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * @return the pprice
     */
    public Double getPprice() {
        return pprice;
    }

    /**
     * @param pprice the pprice to set
     */
    public void setPprice(Double pprice) {
        this.pprice = pprice;
    }

    /**
     * @return the sprice
     */
    public Double getSprice() {
        return sprice;
    }

    /**
     * @param sprice the sprice to set
     */
    public void setSprice(Double sprice) {
        this.sprice = sprice;
    }

    /**
     * @return the totla
     */
    public Double getTotla() {
        
        this.totla=this.sprice-this.pprice;
        return this.totla;
    }

    /**
     * @param totla the totla to set
     */
    public void setTotla(Double x,Double y) {
        this.totla = x-y;
    }


   // public String toString(){return this.id+this.name+this.num+this.getTotla();}
}
