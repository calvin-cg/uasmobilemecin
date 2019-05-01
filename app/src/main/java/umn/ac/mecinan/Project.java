package umn.ac.mecinan;

import java.util.Date;

public class Project {

    private String title;
    //private Date dateStart, dateDue;
    private String idEmployee, idClient;
    private String idField, idCategory;
    private String desc;
    private int price;
    private int status;
    //private Date dateEnd;
    private float rating;

    /**
     * STATUS
     *  0  = Waiting for Response
     *  1  = Accepted
     * -1  = Refused
     *  2  = Paid
     * -2  = Not Paid
     *  3  = Finished
     * -3  = Ongoing
     *  4  = Confirmed
     * -4  = Unconfirmed
     */
    public Project(){

    }

    /*public Project(String title, Date dateStart, Date dateDue, String idEmployee, String idClient, String idField, String idCategory, String desc, int price, int status) {

        this.title = title;
        this.dateStart = dateStart;
        this.dateDue = dateDue;
        this.idEmployee = idEmployee;
        this.idClient = idClient;
        this.idField = idField;
        this.idCategory = idCategory;
        this.desc = desc;
        this.price = price;
        this.status = status;

        this.dateEnd = null;
        this.rating = 4;

    }
*/
    public Project(String title, String idEmployee, String idClient, String idField, String idCategory, String desc, int price, int status, float rating) {

        this.title = title;
        this.idEmployee = idEmployee;
        this.idClient = idClient;
        this.idField = idField;
        this.idCategory = idCategory;
        this.desc = desc;
        this.price = price;
        this.status = status;
        this.rating = rating;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   /* public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }*/

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        if (rating>=0 && rating<=5){
            this.rating = rating;
        }
    }
}
