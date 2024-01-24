package com.ra.model.dao;

import com.ra.model.entity.Category;
import com.ra.model.entity.User;
import com.ra.util.ConnectionDB;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDAOimpl implements CategoryDAO{
    @Override
    public Boolean createCate(Category category) {
      Connection connection = null;
      connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PRO_ADD_CATEGORY(?)");
            callableStatement.setString(1,category.getCategoryName());
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
    public Boolean updateCate(Category category, Integer id) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PRO_UPDATE_CATEGORY(?,?,?)");
            callableStatement.setInt(1,id);
            callableStatement.setString(2,category.getCategoryName());
            callableStatement.setBoolean(3,category.isStatus());
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
    public Category findByIdCate(Integer id) {
        Connection connection = null;
        Category category = new Category();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PRO_FIND_CATEGORY(?)");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return category;
    }

    @Override
    public List<Category> listCate() {
        Connection connection = null;
        List<Category> categories= new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PRO_ALL_CATEGORY()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Category category = new Category();

                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setStatus(rs.getBoolean("status"));

                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return categories;
    }




    public List<Category> listCateDisplay() {
        Connection connection = null;
        List<Category> categories= new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PRO_DISPLAY_CATEGORY()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Category category = new Category();

                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setStatus(rs.getBoolean("status"));

                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return categories;
    }










    @Override
    public List<String> uniqueCate(String nameCate) {
        Connection connection = null;
        List<String> list = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        String sql = "SELECT " + nameCate + " FROM category";

        try {
            PreparedStatement pstm = connection.prepareStatement( sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                list.add(rs.getString(nameCate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }

    @Override
    public void deleteCate(Integer id) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PRO_Delete_Category(?)");
            callableStatement.setInt(1,id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
    }


}
