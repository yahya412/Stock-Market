package com.sm.views;
public class Company {

private Integer id ;
private String name;


public Company(Integer id,String name){
    this.id=id;
    this.name=name;
    
}
    

public Integer getId(){
    return id;
}

@Override
public String toString(){
return this.name;
}
}
