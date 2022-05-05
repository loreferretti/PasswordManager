package it.passwordmanager.dao;

import it.passwordmanager.businessLogic.EncryptionService;
import it.passwordmanager.domainModel.Login;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoginDao implements Dao<Login> {


    private final String password;
    private final String URL;
    private ArrayList<Login> logins;

    public LoginDao(final String password, final String URL) {
        this.password = password;
        this.URL = URL;
    }

    @Override
    public List<Login> getAll() {
        String query = "select id, website, username, password from Login;";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstat = connection.prepareStatement(query)) {

            ResultSet rs = pstat.executeQuery();
            logins = new ArrayList<>();
            while(rs.next()) {
                Login login = extractLogin(rs);
                logins.add(login);
            }
            return logins;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean create(Login login) {
        String query = "insert into Login (website, username, password) values (?, ?, ?);";
        boolean valid = true;
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstat = connection.prepareStatement(query)) {

            String paddedPassword = new String(EncryptionService.padding(password));
            pstat.setString(1, EncryptionService.encrypt(paddedPassword, login.getWebsite()));
            pstat.setString(2, EncryptionService.encrypt(paddedPassword, login.getUsername()));
            pstat.setString(3, EncryptionService.encrypt(paddedPassword, login.getPassword()));

            pstat.execute();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE"))
                valid = false;
        }
        return valid;
    }
    @Override
    public List<Login> read(Object obj) {
        return logins.stream()
                .filter(l -> l.getWebsite().startsWith(String.valueOf(obj)))
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(Login login) {
        String query = "update Login set website = ?, username = ?, password = ? where id = ?;";
        boolean valid = true;
        try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement pstat = connection.prepareStatement(query)) {

            String paddedPassword = new String(EncryptionService.padding(password));
            pstat.setString(1, EncryptionService.encrypt(paddedPassword,login.getWebsite()));
            pstat.setString(2, EncryptionService.encrypt(paddedPassword,login.getUsername()));
            pstat.setString(3, EncryptionService.encrypt(paddedPassword,login.getPassword()));

            pstat.setString(4, Integer.toString(login.getId()));

            pstat.execute();
        } catch (SQLException e) {
            if(e.getMessage().contains("UNIQUE"))
                valid = false;
        }
        return valid;
    }

    @Override
    public boolean delete(Login login) {
        String query = "delete from Login where id = ?;";
        boolean valid = false;
        try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement pstat = connection.prepareStatement(query)) {

            pstat.setString(1, Integer.toString(login.getId()));
            pstat.execute();
            valid = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valid;

    }

    private Login extractLogin(ResultSet rs) throws SQLException {

        String paddedPassword = new String(EncryptionService.padding(password));
        String website = EncryptionService.decrypt(paddedPassword,rs.getString("website"));
        String username = EncryptionService.decrypt(paddedPassword, rs.getString("username"));
        String password = EncryptionService.decrypt(paddedPassword, rs.getString("password"));
        int id = rs.getInt("id");

        return new Login(id, website, username, password);

    }

}
