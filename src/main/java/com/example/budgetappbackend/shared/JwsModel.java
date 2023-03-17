package com.example.budgetappbackend.shared;

public class JwsModel {
    private String sub;
    private Integer id;
    private String name;
    private Integer iat;
    private Integer exp;

    public JwsModel() {
    }

    public JwsModel(String sub, Integer id, String name, Integer iat, Integer exp) {
        this.sub = sub;
        this.id = id;
        this.name = name;
        this.iat = iat;
        this.exp = exp;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIat() {
        return iat;
    }

    public void setIat(Integer iat) {
        this.iat = iat;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }
}
