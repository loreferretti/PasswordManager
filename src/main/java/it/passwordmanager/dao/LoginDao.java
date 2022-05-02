package it.passwordmanager.dao;

import it.passwordmanager.businessLogic.ConnectionFactory;
import it.passwordmanager.domainModel.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDao implements Dao<Login> {

    @Override
    public List<Login> getAll() {
        Connection connection = ConnectionFactory.getConnection();
        String query = "select website, username, password from Login;";
        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            ResultSet rs = pstat.executeQuery();

            ArrayList<Login>  logins = new ArrayList<>();
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
        Connection connection = ConnectionFactory.getConnection();
        String query = "insert into Login (website, username, password) values (?, ?, ?);";
        boolean valid = false;
        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, login.getWebsite());
            pstat.setString(2, login.getUsername());
            pstat.setString(3, login.getPassword());
            valid = pstat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valid;
    }

    @Override
    public List<Login> read(Object obj) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "select website, username, password from Login where website = ?;";

        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, String.valueOf(obj));

            ResultSet rs = pstat.executeQuery();
            ArrayList<Login>  logins = new ArrayList<>();
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
    public boolean update(Login login) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "update Login set website = ?, username = ?, password = ? where id = ?;";
        boolean valid = false;
        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, login.getWebsite());
            pstat.setString(2, login.getUsername());
            pstat.setString(3, login.getPassword());

            pstat.setString(4, Integer.toString(login.getId()));

            valid = pstat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return valid;
    }

    @Override
    public boolean delete(Login login) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "delete from Login where id = ?;";
        boolean valid = false;
        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, Integer.toString(login.getId()));
            valid = pstat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return valid;

    }

    private Login extractLogin(ResultSet rs) throws SQLException {
        String website = rs.getString("website");
        String username = rs.getString("username");
        String password = rs.getString("password");

        Login login = new Login(website, username, password);
        return login;

    }
}
