package umn.ac.mecinan.model;

public class Propose {
    String name, field, cat, duration, price, desc, title, idClient, idEmployee;
    int status;

    public Propose(){

    }

    public Propose(String name, String idClient, String idEmployee, String field, String cat, String title, String duration, String price, String desc, int status){
        this.name = name;
        this.idClient = idClient;
        this.idEmployee = idEmployee;
        this.field = field;
        this.cat = cat;
        this.duration = title;
        this.duration = duration;
        this.price = price;
        this.desc = desc;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
