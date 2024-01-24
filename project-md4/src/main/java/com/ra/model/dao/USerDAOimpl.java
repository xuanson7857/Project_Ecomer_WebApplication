package com.ra.model.dao;

import com.ra.dto.request.UserLoginDTO;
import com.ra.model.entity.User;
import com.ra.util.ConnectionDB;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class USerDAOimpl implements UserDAO{
    @Override
    public Boolean register(User user) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();

        // Tạo salt ngẫu nhiên
        String salt = BCrypt.gensalt(12);

        // Kết hợp salt với mật khẩu và sau đó mã hóa mật khẩu
        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);

        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PRO_REGISTER(?,?,?,?,?)}");
            callableStatement.setString(1,user.getUserName());
            callableStatement.setString(2,user.getEmail());
            callableStatement.setString(3,hashedPassword);
            callableStatement.setString(4,user.getAddress());
            callableStatement.setString(5,user.getPhone());

            int check = callableStatement.executeUpdate();
            if(check > 0 ){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }

        return false;

    }

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        Connection connection = null;
        User user1 = null;

        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PRO_LOGIN(?)}");
            callableStatement.setString(1,userLoginDTO.getEmail());

            try (ResultSet rs = callableStatement.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("password");
                    String enteredPassword = userLoginDTO.getPassword();
                    if (BCrypt.checkpw(enteredPassword, hashedPasswordFromDB) ) {
                        user1 = new User();
                        user1.setUserId(rs.getInt("userId"));
                        user1.setUserName(rs.getString("userName"));
                        user1.setEmail(rs.getString("email"));
                        user1.setAddress(rs.getString("address"));
                        user1.setPhone(rs.getString("phone"));
                        user1.setRole(rs.getByte("role"));
                        user1.setStatus(rs.getByte("status"));
                    }
                    return user1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return user1;
    }


    // khoá người dùng
    @Override
    public Boolean lockuser(Integer id) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PRO_LOCK_USER(?)");
            callableStatement.setInt(1,id);
            int check = callableStatement.executeUpdate();
            if(check > 0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return false;
    }


    // Lấy về một user
    @Override
    public User findById(Integer id) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        User user = new User();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PRO_FIND_USER(?)");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()){
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getByte("role"));
                user.setStatus(rs.getByte("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return user;
    }




// lấy về danh sách người dùng
    @Override
    public List<User> listUser(){
        Connection connection = null;
        List<User> userList = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PRO_GET_ALL_USERS()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getByte("role"));
                user.setStatus(rs.getByte("status"));

                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return userList;
    }


  // lấy dữ liệu để check trùng lặp
    @Override
    public List<String> uniquelist(String string) {
        Connection connection = null;
        List<String> list = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        String sql = "SELECT " + string + " FROM users";

        try {
            PreparedStatement pstm = connection.prepareStatement( sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                list.add(rs.getString(string));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }


}
