package it.passwordmanager.domainModel;

public class Login {
    private int id;
    private String website;
    private String username;
    private String password;

    public Login(String website, String username, String password) {
        this.website = website;
        this.username = username;
        this.password = password;
    }

    public Login(int id, String website, String username, String password) {
        this.id = id;
        this.website = website;
        this.username = username;
        this.password = password;
    }


    /*public void setId(int id) {
        this.id = id;
    }*/

    public int getId() {
        return this.id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
