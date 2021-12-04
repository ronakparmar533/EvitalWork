package com.example.exam;

public class User {
    String names , numbers , city , money;

    public String getNames() {
        return names;
    }

    public String getNumbers() {
        return numbers;
    }


    public String getCity() {
        return city;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public User(String names , String numbers , String city , String money){

        this.names = names;
        this.numbers = numbers;
        this.city = city;
        this.money = money;

    }



}
