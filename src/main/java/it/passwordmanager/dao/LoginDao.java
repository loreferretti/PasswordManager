package it.passwordmanager.dao;

import it.passwordmanager.businessLogic.ConnectionFactory;
import it.passwordmanager.businessLogic.EncryptionService;
import it.passwordmanager.domainModel.Login;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDao implements Dao<Login> {

    @Override
    public List<Login> getAll(String password) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "select id, website, username, password from Login;";
        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            ResultSet rs = pstat.executeQuery();

            ArrayList<Login>  logins = new ArrayList<>();
            while(rs.next()) {
                Login login = extractLogin(password, rs);
                logins.add(login);
            }
            return logins;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void create(String password, Login login) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "insert into Login (website, username, password) values (?, ?, ?);";
        try {
            EncryptionService es = new EncryptionService();
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, es.encrypt(new String(es.padding(password)), login.getWebsite()));
            pstat.setString(2, es.encrypt(new String(es.padding(password)), login.getUsername()));
            pstat.setString(3, es.encrypt(new String(es.padding(password)), login.getPassword()));

            pstat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //FIXME read non funziona
    @Override
    public List<Login> read(String password, Object obj) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "select id, website, username, password from Login where website like ?;";

        try {
            EncryptionService es = new EncryptionService();
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, es.encrypt(new String(es.padding(password)), String.valueOf(obj) + '%'));

            ResultSet rs = pstat.executeQuery();
            ArrayList<Login>  logins = new ArrayList<>();
            while(rs.next()) {
                Login login = extractLogin(password, rs);
                logins.add(login);
            }
            return logins;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(String password, Login login) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "update Login set website = ?, username = ?, password = ? where id = ?;";
        try {
            EncryptionService es = new EncryptionService();
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, es.encrypt(new String(es.padding(password)),login.getWebsite()));
            pstat.setString(2, es.encrypt(new String(es.padding(password)),login.getUsername()));
            pstat.setString(3, es.encrypt(new String(es.padding(password)),login.getPassword()));

            pstat.setString(4, Integer.toString(login.getId()));

            pstat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Login login) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "delete from Login where id = ?;";
        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, Integer.toString(login.getId()));
            pstat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Login extractLogin(String pass, ResultSet rs) throws SQLException {

        EncryptionService es = new EncryptionService();
        String website = es.decrypt(new String(es.padding(pass)),rs.getString("website"));
        String username = es.decrypt(new String(es.padding(pass)), rs.getString("username"));
        String password = es.decrypt(new String(es.padding(pass)), rs.getString("password"));
        int id = rs.getInt("id");

        Login login = new Login(id, website, username, password);
        return login;

    }

}
