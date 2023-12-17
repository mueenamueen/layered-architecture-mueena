package com.example.layeredarchitecture.controller.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl {

    public CustomerDAOImpl() throws SQLException, ClassNotFoundException {
    }

    public ArrayList<CustomerDTO> getAllCustomer()throws SQLException, ClassNotFoundException {
    Connection connection = DBConnection.getDbConnection().getConnection();
    Statement stm = connection.createStatement();
    ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

    ArrayList<CustomerDTO> allCustomer =new ArrayList<>();

    while(rst.next()) {
        CustomerDTO customerDTO = new CustomerDTO(
                rst.getString("id"),
                rst.getString("name"),
                rst.getString("address"));

        allCustomer.add(customerDTO);
    }
    return allCustomer;
    }
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer (id,name, address) VALUES (?,?,?)");
        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        return pstm.executeUpdate() > 0;
    }
    public void updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getId());
        pstm.executeUpdate();

    }
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
        pstm.setString(1,id);
        return pstm.executeQuery().next();
    }

    public void deleteCustomer(String id)throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
        pstm.setString(1, id);
        pstm.executeUpdate();

    }

    public String genarateNewId() {
        try {
            com.example.layeredarchitecture.dao.CustomerDAOImpl customerDAO = new com.example.layeredarchitecture.dao.CustomerDAOImpl();
            return customerDAO.genarateId();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblCustomers.getItems().isEmpty()) {
            return "C00-001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        }

    }

    private String getLastCustomerId() {
    }
}

