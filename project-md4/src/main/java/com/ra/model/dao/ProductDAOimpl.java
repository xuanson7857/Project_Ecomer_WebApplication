package com.ra.model.dao;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOimpl implements ProductDAO{
    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public Boolean createProduct(Product product) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL AddProduct(?,?,?,?,?,?)");
            callableStatement.setString(1,product.getProductName());
            callableStatement.setString(2,product.getDescription());
            callableStatement.setDouble(3, product.getPrice());
            callableStatement.setString(4,product.getImage());
            callableStatement.setInt(5,product.getStock());
            callableStatement.setInt(6,product.getCategory().getCategoryId());

            int rs = callableStatement.executeUpdate();
            if(rs > 0){
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
    public Boolean updateProduct(Product product, Integer id) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PRO_UPDATE_PRODUCT(?,?,?,?,?,?,?,?)");
            callableStatement.setInt(1,id);
            callableStatement.setString(2,product.getProductName());
            callableStatement.setString(3,product.getDescription());
            callableStatement.setDouble(4, product.getPrice());
            callableStatement.setString(5,product.getImage());
            callableStatement.setInt(6,product.getStock());
            callableStatement.setBoolean(7,product.isStatus());
            callableStatement.setInt(8,product.getCategory().getCategoryId());

            int rs = callableStatement.executeUpdate();
            if(rs > 0){
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
    public Product findByIdPro(Integer id) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        Product product = new Product();

        try {
            CallableStatement callableStatement = connection.prepareCall("CALL Find_Product(?)");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));

                Category category = categoryDAO.findByIdCate(rs.getInt("categoryId"));
                product.setCategory(category);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return product;
    }



    @Override
    public List<Product> listProduct() {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        List<Product> productList = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL GET_AllProducts()");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Product product = new Product();

                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));

                Category category = categoryDAO.findByIdCate(rs.getInt("categoryId"));
                product.setCategory(category);

                productList.add(product);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return productList;

    }



// phân trang

    @Override
    public List<Product> Pagination(Integer page, Integer limit) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        List<Product> productList = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PRO_Pagination_Product(?,?)");
            callableStatement.setInt(1,page);
            callableStatement.setInt(2,limit);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Product product = new Product();

                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));

                Category category = categoryDAO.findByIdCate(rs.getInt("categoryId"));
                product.setCategory(category);

                productList.add(product);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return productList;

    }






// gọi theo category có số lượng

    @Override
    public List<Product> listProductByCategory(Integer id) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        List<Product> productList = new ArrayList<>();

        try {

            CallableStatement callableStatement = connection.prepareCall("CALL GetProductsByCategoryId(?)");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Product product = new Product();

                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));

                Category category = categoryDAO.findByIdCate(rs.getInt("categoryId"));
                product.setCategory(category);

                productList.add(product);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return productList;

    }




//    lấy product số lượng theo yêu cầu

    @Override
    public List<Product> listProductByRequest(Integer id) {
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        List<Product> productList = new ArrayList<>();

        try {

            CallableStatement callableStatement = connection.prepareCall("CALL GetProductsByRequest(?)");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Product product = new Product();

                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));

                Category category = categoryDAO.findByIdCate(rs.getInt("categoryId"));
                product.setCategory(category);

                productList.add(product);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return productList;

    }

// gọi product toàn bộ theo Category
@Override
public List<Product> listAllProductByCategory(Integer id) {
    Connection connection = null;
    connection = ConnectionDB.openConnection();
    List<Product> productList = new ArrayList<>();

    try {

        CallableStatement callableStatement = connection.prepareCall("CALL GET_AllProductsByCategory(?)");
        callableStatement.setInt(1,id);
        ResultSet rs = callableStatement.executeQuery();
        while (rs.next()){
            Product product = new Product();

            product.setProductId(rs.getInt("productId"));
            product.setProductName(rs.getString("productName"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setImage(rs.getString("image"));
            product.setStock(rs.getInt("stock"));
            product.setStatus(rs.getBoolean("status"));

            Category category = categoryDAO.findByIdCate(rs.getInt("categoryId"));
            product.setCategory(category);

            productList.add(product);

        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }finally {
        ConnectionDB.closeConnection(connection);
    }
    return productList;

}





// xoá
    @Override
    public void deleteProduct(Integer id) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL DeleteProduct(?)");
            callableStatement.setInt(1,id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
    }
}
