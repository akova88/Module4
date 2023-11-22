package com.example.users_manager_mvc.dao;

import com.example.users_manager_mvc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    void insertUser(User user);
    User selectUser(int id);
    List<User> selectAllUsers();

    List<User> getAllUsers();
    boolean deleteUser(int id) throws SQLException;
    boolean deleteUserStore(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;
    boolean updateUseStore(User user) throws SQLException;

    User getUserById(int id);

    void insertUserStore(User user) throws SQLException;

    void addUserTransaction(User user, List<Integer> permissions);

    public void insertUpdateUseTransaction();
}
