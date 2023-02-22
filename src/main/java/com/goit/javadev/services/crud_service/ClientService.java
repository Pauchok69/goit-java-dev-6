package com.goit.javadev.services.crud_service;

import com.goit.javadev.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientService {
    private final PreparedStatement createSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement getLastIdSt;

    public ClientService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO CLIENT(ID, `NAME`) VALUES (?, ?)");
        getByIdSt = connection.prepareStatement("SELECT * FROM CLIENT WHERE ID = ?");
        updateSt = connection.prepareStatement("UPDATE CLIENT SET NAME = ? WHERE ID = ?");
        deleteByIdSt = connection.prepareStatement("DELETE FROM CLIENT WHERE ID = ?");
        getAllSt = connection.prepareStatement("SELECT * FROM CLIENT");
        getLastIdSt = connection.prepareStatement("SELECT ID FROM CLIENT ORDER BY ID DESC LIMIT 1");
    }

    public long create(String name) throws SQLException {
        validateName(name);
        long lastId = getLastId();

        createSt.setLong(1, ++lastId);
        createSt.setString(2, name);

        createSt.executeUpdate();

        return lastId;
    }

    private long getLastId() throws SQLException {
        ResultSet resultSet = getLastIdSt.executeQuery();
        resultSet.next();

        return resultSet.getLong("ID");
    }

    public String getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);

        ResultSet resultSet = getByIdSt.executeQuery();

        if (!resultSet.next()) {
            return "There is no client with this id";
        }

        long resultId = resultSet.getLong("id");
        String resultName = resultSet.getString("name");

        return String.format("%d: %s", resultId, resultName);
    }

    public void setName(long id, String name) throws SQLException {
        validateName(name);

        updateSt.setString(1, name);
        updateSt.setLong(2, id);

        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }

    public List<Client> listAll() throws SQLException {
        ResultSet resultSet = getAllSt.executeQuery();
        ArrayList<Client> clients = new ArrayList<>();

        while (resultSet.next()) {
            Client client = new Client(
                    resultSet.getLong("id"),
                    resultSet.getString("name")
            );

            clients.add(client);
        }

        return clients;
    }

    private void validateName(String name) {
        if (name == null || name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("Name is not valid");
        }
    }
}
