package com.sm;

import java.time.LocalDate;

public class Stocks {

    private String stockName = new String();
    private LocalDate pdate;
    private Integer stockNum = 0;
    private Double cprice = 0.0;
    private Double total = 0.0;

    /**
     * @return the stockName
     */

    public Stocks(String x, Integer y, Double z) {
        this.stockName = x;
        this.pdate = null;
        this.stockNum = y;
        this.cprice = z;
        this.total = getTotal();

    }

    public Stocks(String x, Integer z) {
        this.stockName = x;
        this.pdate = LocalDate.now();
        this.cprice = 0.0;
        this.stockNum = z;
        this.total = 0.0;
    }

    public String getStockName() {
        return stockName;
    }

    /**
     * @param stockName the stockName to set
     */
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setCprice(Double price) {
        this.cprice = price;
    }

    public Double getCprice() {
        return this.cprice;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;

    }

    public Integer getStockNum() {
        return this.stockNum;
    }

    /**
     * @return the pdate
     */
    public LocalDate getPdate() {
        return pdate;
    }

    /**
     * @param pdate the pdate to set
     */
    public void setPdate(LocalDate pdate) {
        this.pdate = pdate;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return this.cprice * this.stockNum;
    }

    /**
     * @param total the total to set
     */
}
