/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Color;
import app.model.Material;
import app.model.Product;
import app.model.ProductDetail;
import app.model.Size;
import app.model.Sole;
import app.request.UpdateProductDetailRequest;
import app.response.ProductDetailResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductDetailRepository implements CrudRepository<ProductDetail> {

    private final ProductRepository productRepository = new ProductRepository();
    private final ColorRepository colorRepository = new ColorRepository();
    private final MaterialRepository materialRepository = new MaterialRepository();
    private final SizeRepository sizeRepository = new SizeRepository();
    private final SoleRepository soleRepository = new SoleRepository();

    @Override
    public List<ProductDetail> getAll() {
        throw new UnsupportedOperationException();
    }

    public List<ProductDetailResponse> getAllViews() {
        List<ProductDetailResponse> productDetailResponses = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                         	pd.CODE,
                                p.NAME,
                         	s.NAME,
                         	m.NAME,
                         	c.NAME,
                         	s1.NAME,
                         	SELL_PRICE,
                                ORIGIN_PRICE,
                         	QUANTITY,
                         	pd.DELETED
                         FROM
                         	N3STORESNEAKER.dbo.PRODUCT_DETAIL pd
                         LEFT JOIN PRODUCT p ON
                         	pd.ID_PRODUCT = p.ID
                         LEFT JOIN SIZE s on
                         	pd.ID_SIZE = s.ID
                         LEFT JOIN MATERIAL m on
                         	pd.ID_MATERIAL = m.ID
                         LEFT JOIN COLOR c on
                         	pd.ID_COLOR = c.ID
                         LEFT JOIN SOLE s1 on
                         	pd.ID_SOLE = s1.ID;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                ProductDetailResponse detailResponse = new ProductDetailResponse();
                detailResponse.setCode(rs.getString(1));
                detailResponse.setProduct(rs.getString(2));
                detailResponse.setSize(rs.getString(3));
                detailResponse.setMaterial(rs.getString(4));
                detailResponse.setColor(rs.getString(5));
                detailResponse.setSole(rs.getString(6));
                detailResponse.setSellPrice(rs.getString(7));
                detailResponse.setOriginPrice(rs.getString(8));
                detailResponse.setQuantity(rs.getString(9));
                detailResponse.setDeleted(rs.getBoolean(10));
                productDetailResponses.add(detailResponse);
            }
            return productDetailResponses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDetailResponse> getAllViewsPagnation(int offset, int limit) {
        List<ProductDetailResponse> productDetailResponses = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                         	pd.CODE,
                                p.NAME,
                         	s.NAME,
                         	m.NAME,
                         	c.NAME,
                         	s1.NAME,
                         	SELL_PRICE,
                                ORIGIN_PRICE,
                         	QUANTITY,
                         	pd.DELETED
                         FROM
                         	N3STORESNEAKER.dbo.PRODUCT_DETAIL pd
                         LEFT JOIN PRODUCT p ON
                         	pd.ID_PRODUCT = p.ID
                         LEFT JOIN SIZE s on
                         	pd.ID_SIZE = s.ID
                         LEFT JOIN MATERIAL m on
                         	pd.ID_MATERIAL = m.ID
                         LEFT JOIN COLOR c on
                         	pd.ID_COLOR = c.ID
                         LEFT JOIN SOLE s1 on
                         	pd.ID_SOLE = s1.ID
                         ORDER BY
                                pd.ID DESC
                         OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, offset);
            stm.setObject(2, limit);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                ProductDetailResponse detailResponse = new ProductDetailResponse();
                detailResponse.setCode(rs.getString(1));
                detailResponse.setProduct(rs.getString(2));
                detailResponse.setSize(rs.getString(3));
                detailResponse.setMaterial(rs.getString(4));
                detailResponse.setColor(rs.getString(5));
                detailResponse.setSole(rs.getString(6));
                detailResponse.setSellPrice(rs.getString(7));
                detailResponse.setOriginPrice(rs.getString(8));
                detailResponse.setQuantity(rs.getString(9));
                detailResponse.setDeleted(rs.getBoolean(10));
                productDetailResponses.add(detailResponse);
            }
            return productDetailResponses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDetailResponse> getAllViewsPagnationWithNameProduct(int offset, int limit, String nameProduct) {
        List<ProductDetailResponse> productDetailResponses = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                             pd.CODE,
                             p.NAME AS PRODUCT_NAME,
                             s.NAME AS SIZE_NAME,
                             m.NAME AS MATERIAL_NAME,
                             c.NAME AS COLOR_NAME,
                             s1.NAME AS SOLE_NAME,
                             SELL_PRICE,
                             ORIGIN_PRICE,
                             QUANTITY,
                             pd.DELETED
                         FROM
                             N3STORESNEAKER.dbo.PRODUCT_DETAIL pd
                         LEFT JOIN PRODUCT p ON
                             pd.ID_PRODUCT = p.ID
                         LEFT JOIN SIZE s ON
                             pd.ID_SIZE = s.ID
                         LEFT JOIN MATERIAL m ON
                             pd.ID_MATERIAL = m.ID
                         LEFT JOIN COLOR c ON
                             pd.ID_COLOR = c.ID
                         LEFT JOIN SOLE s1 ON
                             pd.ID_SOLE = s1.ID
                         WHERE
                             p.NAME = ?
                         ORDER BY
                             pd.CODE DESC
                         OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, nameProduct);
            stm.setObject(2, offset);
            stm.setObject(3, limit);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                ProductDetailResponse detailResponse = new ProductDetailResponse();
                detailResponse.setCode(rs.getString(1));
                detailResponse.setProduct(rs.getString(2));
                detailResponse.setSize(rs.getString(3));
                detailResponse.setMaterial(rs.getString(4));
                detailResponse.setColor(rs.getString(5));
                detailResponse.setSole(rs.getString(6));
                detailResponse.setSellPrice(rs.getString(7));
                detailResponse.setOriginPrice(rs.getString(8));
                detailResponse.setQuantity(rs.getString(9));
                detailResponse.setDeleted(rs.getBoolean(10));
                productDetailResponses.add(detailResponse);
            }
            return productDetailResponses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(ProductDetail t) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.PRODUCT_DETAIL
                         (ID_SIZE, ID_PRODUCT, ID_MATERIAL, ID_COLOR, ID_SOLE, IMAGE_PRODUCT, SELL_PRICE, QUANTITY, DELETED, ORIGIN_PRICE, CODE, DESCRIPTION)
                         VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, t.getIdSize());
            stm.setObject(2, t.getIdProduct());
            stm.setObject(3, t.getIdMaterial());
            stm.setObject(4, t.getIdColor());
            stm.setObject(5, t.getIdSole());
            stm.setBytes(6, t.getImageProduct());
            stm.setObject(7, t.getSellPrice());
            stm.setObject(8, t.getQuantity());
            stm.setObject(9, t.getDeleted());
            stm.setObject(10, t.getOriginPrice());
            stm.setObject(11, t.getCode());
            stm.setObject(12, t.getDescription());

            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//    public int addAll(List<ProductDetail> productList) {
//        try (Connection con = DBConnector.getConnection()) {
//            String sql = """
//                     INSERT INTO N3STORESNEAKER.dbo.PRODUCT_DETAIL
//                     (ID_SIZE, ID_PRODUCT, ID_MATERIAL, ID_COLOR, ID_SOLE, IMAGE_PRODUCT, SELL_PRICE, QUANTITY, DELETED, ORIGIN_PRICE, CODE, DESCRIPTION)
//                     VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                     ON DUPLICATE KEY UPDATE
//                     SELL_PRICE = VALUES(SELL_PRICE),
//                     QUANTITY = QUANTITY + VALUES(QUANTITY),
//                     DESCRIPTION = VALUES(DESCRIPTION),
//                     ORIGIN_PRICE = VALUES(ORIGIN_PRICE) ;
//                     """;
//            try (PreparedStatement stm = con.prepareStatement(sql)) {
//                for (ProductDetail t : productList) {
//                    stm.setObject(1, t.getIdSize());
//                    stm.setObject(2, t.getIdProduct());
//                    stm.setObject(3, t.getIdMaterial());
//                    stm.setObject(4, t.getIdColor());
//                    stm.setObject(5, t.getIdSole());
//                    stm.setBytes(6, t.getImageProduct());
//                    stm.setObject(7, t.getSellPrice());
//                    stm.setObject(8, t.getQuantity());
//                    stm.setObject(9, Boolean.FALSE);
//                    stm.setObject(10, t.getOriginPrice());
//                    stm.setObject(11, t.getCode());
//                    stm.setObject(12, t.getDescription());
//                    stm.addBatch();
//                }
//
//                int[] results = stm.executeBatch();
//
//                int totalAffectedRows = Arrays.stream(results).sum();
//
//                return totalAffectedRows;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
    public int addAll(List<ProductDetail> productList) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                     MERGE INTO N3STORESNEAKER.dbo.PRODUCT_DETAIL AS target
                     USING (
                         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                     ) AS source (ID_SIZE, ID_PRODUCT, ID_MATERIAL, ID_COLOR, ID_SOLE, IMAGE_PRODUCT, SELL_PRICE, QUANTITY, DELETED, ORIGIN_PRICE, CODE, DESCRIPTION)
                     ON target.CODE = source.CODE
                     WHEN MATCHED THEN
                         UPDATE SET
                         target.SELL_PRICE = source.SELL_PRICE,
                         target.QUANTITY = target.QUANTITY + source.QUANTITY, -- Cộng số lượng từ bản ghi nhập vào
                         target.DESCRIPTION = source.DESCRIPTION,
                         target.ORIGIN_PRICE = source.ORIGIN_PRICE
                     WHEN NOT MATCHED THEN
                         INSERT (ID_SIZE, ID_PRODUCT, ID_MATERIAL, ID_COLOR, ID_SOLE, IMAGE_PRODUCT, SELL_PRICE, QUANTITY, DELETED, ORIGIN_PRICE, CODE, DESCRIPTION)
                         VALUES (source.ID_SIZE, source.ID_PRODUCT, source.ID_MATERIAL, source.ID_COLOR, source.ID_SOLE, source.IMAGE_PRODUCT, source.SELL_PRICE, source.QUANTITY, source.DELETED, source.ORIGIN_PRICE, source.CODE, source.DESCRIPTION);
                     """;

            try (PreparedStatement stm = con.prepareStatement(sql)) {
                for (ProductDetail t : productList) {
                    stm.setObject(1, t.getIdSize());
                    stm.setObject(2, t.getIdProduct());
                    stm.setObject(3, t.getIdMaterial());
                    stm.setObject(4, t.getIdColor());
                    stm.setObject(5, t.getIdSole());
                    stm.setBytes(6, t.getImageProduct());
                    stm.setObject(7, t.getSellPrice());
                    stm.setObject(8, t.getQuantity());
                    stm.setObject(9, Boolean.FALSE);
                    stm.setObject(10, t.getOriginPrice());
                    stm.setObject(11, t.getCode());
                    stm.setObject(12, t.getDescription());
                    stm.addBatch();
                }

                int[] results = stm.executeBatch();

                int totalAffectedRows = Arrays.stream(results).sum();

                return totalAffectedRows;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Integer id, ProductDetail t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int updateProductDetail(String code, UpdateProductDetailRequest detailRequest) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.PRODUCT_DETAIL
                         SET SELL_PRICE = ?, QUANTITY = ?, ORIGIN_PRICE = ?, DESCRIPTION = ?
                         WHERE CODE = ?;
                         """;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, detailRequest.getSellPrice());
            stm.setObject(2, detailRequest.getQuantity());
            stm.setObject(3, detailRequest.getOriginPrice());
            stm.setObject(4, detailRequest.getDescription());
            stm.setObject(5, code);
            return stm.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public ProductDetail findByName(String code) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                     SELECT ID, ID_SIZE, ID_PRODUCT, ID_MATERIAL, ID_COLOR, ID_SOLE, IMAGE_PRODUCT, SELL_PRICE, QUANTITY, DELETED, ORIGIN_PRICE, CODE, DESCRIPTION
                     FROM N3STORESNEAKER.dbo.PRODUCT_DETAIL
                     WHERE CODE = ?;
                     """;

            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, code);
            ProductDetail productDetail = new ProductDetail();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                productDetail.setId(rs.getInt(1));
                productDetail.setIdSize(rs.getInt(2));
                productDetail.setIdProduct(rs.getInt(3));
                productDetail.setIdMaterial(rs.getInt(4));
                productDetail.setIdColor(rs.getInt(5));
                productDetail.setIdSole(rs.getInt(6));
                productDetail.setImageProduct(rs.getBytes(7));
                productDetail.setSellPrice(rs.getDouble(8));
                productDetail.setQuantity(rs.getInt(9));
                productDetail.setDeleted(rs.getBoolean(10));
                productDetail.setOriginPrice(rs.getDouble(11));
                productDetail.setCode(rs.getString(12));
                productDetail.setDescription(rs.getString(13));
            }
            return productDetail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductDetail findById(int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                     SELECT ID, ID_SIZE, ID_PRODUCT, ID_MATERIAL, ID_COLOR, ID_SOLE, IMAGE_PRODUCT, SELL_PRICE, QUANTITY, DELETED, ORIGIN_PRICE, CODE, DESCRIPTION
                     FROM N3STORESNEAKER.dbo.PRODUCT_DETAIL
                     WHERE ID = ?;
                     """;

            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, id);
            ProductDetail productDetail = new ProductDetail();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                productDetail.setId(rs.getInt(1));
                productDetail.setIdSize(rs.getInt(2));
                productDetail.setIdProduct(rs.getInt(3));
                productDetail.setIdMaterial(rs.getInt(4));
                productDetail.setIdColor(rs.getInt(5));
                productDetail.setIdSole(rs.getInt(6));
                productDetail.setImageProduct(rs.getBytes(7));
                productDetail.setSellPrice(rs.getDouble(8));
                productDetail.setQuantity(rs.getInt(9));
                productDetail.setDeleted(rs.getBoolean(10));
                productDetail.setOriginPrice(rs.getDouble(11));
                productDetail.setCode(rs.getString(12));
                productDetail.setDescription(rs.getString(13));
            }
            return productDetail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductDetailResponse> findListByNameProduct(String nameProduct) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                         	pd.CODE,
                                p.NAME,
                         	s.NAME,
                         	m.NAME,
                         	c.NAME,
                         	s1.NAME,
                         	SELL_PRICE,
                                ORIGIN_PRICE,
                         	QUANTITY,
                         	pd.DELETED
                         FROM
                         	N3STORESNEAKER.dbo.PRODUCT_DETAIL pd
                         LEFT JOIN PRODUCT p ON
                         	pd.ID_PRODUCT = p.ID
                         LEFT JOIN SIZE s on
                         	pd.ID_SIZE = s.ID
                         LEFT JOIN MATERIAL m on
                         	pd.ID_MATERIAL = m.ID
                         LEFT JOIN COLOR c on
                         	pd.ID_COLOR = c.ID
                         LEFT JOIN SOLE s1 on
                         	pd.ID_SOLE = s1.ID
                         Where p.NAME = ?;
                         """;

            PreparedStatement stm = con.prepareStatement(sql);
//            Product product = productRepository.findByName(nameProduct);
            stm.setString(1, nameProduct);
            List<ProductDetailResponse> productDetaisl = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductDetailResponse detailResponse = new ProductDetailResponse();
                detailResponse.setCode(rs.getString(1));
                detailResponse.setProduct(rs.getString(2));
                detailResponse.setSize(rs.getString(3));
                detailResponse.setMaterial(rs.getString(4));
                detailResponse.setColor(rs.getString(5));
                detailResponse.setSole(rs.getString(6));
                detailResponse.setSellPrice(rs.getString(7));
                detailResponse.setOriginPrice(rs.getString(8));
                detailResponse.setQuantity(rs.getString(9));
                detailResponse.setDeleted(rs.getBoolean(10));
                productDetaisl.add(detailResponse);
            }
            return productDetaisl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countProductRecord() {
        String sql = """
                   SELECT COUNT(*) FROM PRODUCT_DETAIL
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

    public int countProductRecordWithNameProduct(Integer productId) {
        String sql = """
                   SELECT COUNT(*) FROM PRODUCT_DETAIL
                   WHERE ID_PRODUCT = ?
                   """;
        int count = 0;
        try (Connection con = DBConnector.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, productId);
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

    public ProductDetail findByAtribute(String nameProduct, String nameSize, String nameMaterial, String nameSole, String nameColor) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                     SELECT ID, ID_SIZE, ID_PRODUCT, ID_MATERIAL, ID_COLOR, ID_SOLE, IMAGE_PRODUCT, SELL_PRICE, QUANTITY, DELETED, ORIGIN_PRICE, CODE, DESCRIPTION
                     FROM N3STORESNEAKER.dbo.PRODUCT_DETAIL
                     WHERE ID_SIZE = ? and ID_PRODUCT = ? and ID_MATERIAL = ? and ID_COLOR = ? and ID_SOLE = ?;
                     """;

            PreparedStatement stm = con.prepareStatement(sql);
            Size size = sizeRepository.findByName(nameSize);
            Product product = productRepository.findByName(nameProduct);
            Material material = materialRepository.findByName(nameMaterial);
            Sole sole = soleRepository.findByName(nameSole);
            Color color = colorRepository.findByName(nameColor);

            // Đặt giá trị cho các tham số
            stm.setInt(1, size.getId());
            stm.setInt(2, product.getId());
            stm.setInt(3, material.getId());
            stm.setInt(4, color.getId());
            stm.setInt(5, sole.getId());
            ProductDetail productDetail = new ProductDetail();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                productDetail.setId(rs.getInt(1));
                productDetail.setIdSize(rs.getInt(2));
                productDetail.setIdProduct(rs.getInt(3));
                productDetail.setIdMaterial(rs.getInt(4));
                productDetail.setIdColor(rs.getInt(5));
                productDetail.setIdSole(rs.getInt(6));
                productDetail.setImageProduct(rs.getBytes(7));
                productDetail.setSellPrice(rs.getDouble(8));
                productDetail.setQuantity(rs.getInt(9));
                productDetail.setDeleted(rs.getBoolean(10));
                productDetail.setOriginPrice(rs.getDouble(11));
                productDetail.setCode(rs.getString(12));
                productDetail.setDescription(rs.getString(13));
            }
            return productDetail;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Trả về null nếu không tìm thấy sản phẩm
        return null;
    }

    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(CAST(SUBSTRING(CODE, 5, LEN(CODE) - 4) AS INT)) FROM PRODUCT_DETAIL WHERE CODE LIKE 'CTSP%'";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                int lastNumber = rs.getInt(1);
                System.out.println(lastNumber);

                if (lastNumber == 0) {
                    return "CTSP1";
                }

                // Loop until finding the next available code
                while (true) {
                    lastNumber++;
                    String nextCode = "CTSP" + lastNumber;
                    if (!codeExistsInDatabase(nextCode)) {
                        return nextCode;
                    }
                }
            } else {
                return "CTSP1";
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

    public int generateNextModelCodeNumber() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(CAST(SUBSTRING(CODE, 5, LEN(CODE) - 4) AS INT)) FROM PRODUCT_DETAIL WHERE CODE LIKE 'CTSP%'";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                int lastNumber = rs.getInt(1);
                System.out.println(lastNumber);

                if (lastNumber == 0) {
                    return 1;
                }

                // Loop until finding the next available code
                while (true) {
                    lastNumber++;
                    String nextCode = "CTSP" + lastNumber;
                    if (!codeExistsInDatabase(nextCode)) {
                        return lastNumber;
                    }
                }
            } else {
                return 1;
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
        return 0; // Hoặc giá trị mặc định tùy thuộc vào yêu cầu của bạn
    }

    private boolean codeExistsInDatabase(String code) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT COUNT(*) FROM PRODUCT_DETAIL WHERE CODE = ?";
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

    public int updateStatus(String code) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.PRODUCT_DETAIL
                         SET DELETED = ?
                         WHERE CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ProductDetail product = findByName(code);
            if (product != null) {
                if (product.getDeleted() != true) {
                    product.setDeleted(Boolean.TRUE);
                } else {
                    product.setDeleted(Boolean.FALSE);
                }

                stm.setObject(1, product.getDeleted());
                stm.setObject(2, product.getCode());
            }
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getQuantityProductDetailByCode(String code) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT QUANTITY 
                         FROM N3STORESNEAKER.dbo.PRODUCT_DETAIL pd 
                         WHERE pd.CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, code);
            int quantity = 0;
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt(1);
            }
            return quantity;
        } catch (Exception e) {
            return 0;
        }
    }

    public int updateQuantity(String code, int quantity) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.PRODUCT_DETAIL
                         SET QUANTITY = ?
                         WHERE CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, quantity);
            stm.setObject(2, code);
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            return 0;
        }
    }

}
