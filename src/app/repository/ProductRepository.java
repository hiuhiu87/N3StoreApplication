/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Product;
import app.response.ProductResponse;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductRepository implements CrudRepository<Product> {

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                         	ID,
                         	CODE,
                         	ID_CATEGORY,
                         	NAME,
                         	ID_BRAND,
                         	DELETED
                         FROM
                         	N3STORESNEAKER.dbo.PRODUCT
                         WHERE
                         	DELETED = 0
                         ORDER BY
                         	ID DESC;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setCode(rs.getString(2));
                product.setIdCategory(rs.getInt(3));
                product.setName(rs.getString(4));
                product.setIdBrand(rs.getInt(5));
                product.setDeleted(rs.getBoolean(6));
                list.add(product);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<ProductResponse> getAllProductsView() {
        List<ProductResponse> list = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                         	p.CODE,
                         	p.NAME,
                         	c.NAME,
                         	b.NAME,
                         	COUNT(pd.id) AS QUANTITY,
                         	p.DELETED
                         FROM
                         	N3STORESNEAKER.dbo.PRODUCT p
                         LEFT JOIN CATEGORY c on
                         	p.ID_CATEGORY = c.ID
                         LEFT JOIN BRAND b on
                         	p.ID_BRAND = b.ID
                         LEFT JOIN PRODUCT_DETAIL pd on
                         	p.id = pd.ID_PRODUCT
                         GROUP BY p.CODE, p.NAME, c.NAME, p.DELETED, b.NAME;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductResponse product = new ProductResponse();
                product.setCode(rs.getString(1));
                product.setName(rs.getString(2));
                product.setCategory(rs.getString(3));
                product.setCompany(rs.getString(4));
                product.setQuantity(getQuantityProduct(rs.getString(1)));
                product.setDeleted(rs.getBoolean(6));
                list.add(product);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getQuantityProduct(String productCode) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT SUM(QUANTITY)
                         FROM N3STORESNEAKER.dbo.PRODUCT_DETAIL pd 
                         JOIN N3STORESNEAKER.dbo.PRODUCT p on pd.ID_PRODUCT = p.ID 
                         WHERE p.CODE = ? ;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, productCode);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public List<ProductResponse> getAllProductsViewPagenation(int offset, int limit) {
        List<ProductResponse> list = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                            p.ID,
                             p.CODE,
                             p.NAME AS PRODUCT_NAME,
                             c.NAME AS CATEGORY_NAME,
                             b.NAME AS BRAND_NAME,
                             COUNT(pd.id) AS QUANTITY,
                             p.DELETED
                         FROM
                             N3STORESNEAKER.dbo.PRODUCT p
                         LEFT JOIN CATEGORY c ON
                             p.ID_CATEGORY = c.ID
                         LEFT JOIN BRAND b ON
                             p.ID_BRAND = b.ID
                         LEFT JOIN PRODUCT_DETAIL pd ON
                             p.id = pd.ID_PRODUCT
                         GROUP BY
                             p.ID,
                             p.CODE,
                             p.NAME,
                             c.NAME,
                             p.DELETED,
                             b.NAME
                         ORDER BY
                             p.ID DESC
                         OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, offset);
            stm.setObject(2, limit);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductResponse product = new ProductResponse();
                product.setId(rs.getInt(1));
                product.setCode(rs.getString(2));
                product.setName(rs.getString(3));
                product.setCategory(rs.getString(4));
                product.setCompany(rs.getString(5));
                product.setQuantity(getQuantityProduct(rs.getString(2)));
                product.setDeleted(rs.getBoolean(7));
                list.add(product);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public int countProductRecord() {
        String sql = """
                   SELECT COUNT(*) FROM PRODUCT
                   """;
        int count = 0;
        try (Connection con = DBConnector.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int add(Product product) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.PRODUCT
                         (CODE, ID_CATEGORY, NAME, ID_BRAND, DELETED)
                         VALUES(?, ?, ?, ?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, product.getCode());
            stm.setObject(2, product.getIdCategory());
            stm.setObject(3, product.getName());
            stm.setObject(4, product.getIdBrand());
            stm.setObject(5, product.getDeleted());
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Integer id, Product t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int updateStatus(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.PRODUCT
                         SET DELETED = ?
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            Product product = findByName(name);
            if (product != null) {
                if (product.getDeleted() != true) {
                    product.setDeleted(Boolean.TRUE);
                } else {
                    product.setDeleted(Boolean.FALSE);
                }

                stm.setObject(1, product.getDeleted());
                stm.setObject(2, product.getName());
            }
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Product findByName(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, CODE, ID_CATEGORY, NAME, ID_BRAND, DELETED
                         FROM N3STORESNEAKER.dbo.PRODUCT
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            Product product = new Product();
            while (rs.next()) {
                product.setId(rs.getInt(1));
                product.setCode(rs.getString(2));
                product.setIdCategory(rs.getInt(3));
                product.setName(rs.getString(4));
                product.setIdBrand(rs.getInt(5));
                product.setDeleted(rs.getBoolean(6));
            }
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(CAST(SUBSTRING(CODE, 3, LEN(CODE) - 2) AS INT)) FROM PRODUCT";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                int lastNumber = rs.getInt(1);
                System.out.println(lastNumber);

                if (lastNumber == 0) {
                    return "SP1";
                }

                // Loop until finding the next available code
                while (true) {
                    lastNumber++;
                    String nextCode = "SP" + lastNumber;
                    if (!codeExistsInDatabase(nextCode)) {
                        return nextCode;
                    }
                }
            } else {
                return "SP1";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stm != null) {
                    stm.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean codeExistsInDatabase(String code) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT COUNT(*) FROM PRODUCT WHERE CODE = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, code);
            rs = stm.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

            return false;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

}
