package it.passwordmanager.dao;

import it.passwordmanager.businessLogic.ConnectionFactory;
import it.passwordmanager.domainModel.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LoginDao implements Dao<Login> {

    @Override
    public List<Login> getAll() {
        Connection connection = ConnectionFactory.getConnection();
        String query = "select * from Login;";
        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            ResultSet rs = pstat.executeQuery();

            ArrayList<Login>  logins = new ArrayList<Login>();
            while(rs.next()) {
                String website = rs.getString("website");
                String username = rs.getString("username");
                String password = rs.getString("password");
                logins.add(new Login(website,username,password));
            }
            return logins;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean create(Login login) {
        return false;
    }

    @Override
    public List<Login> read(Predicate predicate) {
        return null;
    }

    @Override
    public boolean update(Login login) {
        return false;
    }

    @Override
    public boolean delete(Login login) {
        return false;
    }
}
