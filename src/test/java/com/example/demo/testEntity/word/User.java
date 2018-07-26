package com.example.demo.testEntity.word;

import java.util.List;
import java.util.Map;

public class User {

    private String name;
    private String pwd;
    private List<Address> adds;
    private Map<String, Address> keyadds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<Address> getAdds() {
        return adds;
    }

    public void setAdds(List<Address> adds) {
        this.adds = adds;
    }

    public Map<String, Address> getKeyadds() {
        return keyadds;
    }

    public void setKeyadds(Map<String, Address> keyadds) {
        this.keyadds = keyadds;
    }
}
