package umn.ac.mecinan;

public class User {

    private String username, email, tagline;

    public User(){

    }
    public User(String username, String email, String tagline) {
        this.username = username;
        this.email = email;
        this.tagline = tagline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}