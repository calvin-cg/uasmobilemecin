package umn.ac.mecinan;

public class Project {

    private int id;
    private String title;
    private String nameDev, nameClient;
    private String nameField, nameCategory;
    private String status;
    private String desc;

    public Project(){
        this.id = 999;
        this.title = "UNDEFINED";
        this.nameDev = "UNDEFINED";
        this.nameClient = "UNDEFINED";
        this.nameField = "UNDEFINED";
        this.nameCategory = "UNDEFINED";
        this.status = "UNDEFINED";
        this.desc = "UNDEFINED";
    }

    public Project(int id, String title, String nameDev, String nameClient, String nameField, String nameCategory, String status, String desc) {
        this.id = id;
        this.title = title;
        this.nameDev = nameDev;
        this.nameClient = nameClient;
        this.nameField = nameField;
        this.nameCategory = nameCategory;
        this.status = status;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNameDev() {
        return nameDev;
    }

    public void setNameDev(String nameDev) {
        this.nameDev = nameDev;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
