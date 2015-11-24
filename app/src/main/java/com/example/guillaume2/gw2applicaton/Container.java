package com.example.guillaume2.gw2applicaton;

public class Container  {
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account account = null;


    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank bank = null;

    public Container() {}


}
