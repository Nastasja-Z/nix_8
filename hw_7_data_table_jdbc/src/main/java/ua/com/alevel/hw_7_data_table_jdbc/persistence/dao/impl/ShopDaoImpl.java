package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.impl;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.ShopDao;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.BaseEntity;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.ShopStatus;
import ua.com.alevel.hw_7_data_table_jdbc.store.ConnectionStoreFactory;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopDaoImpl implements ShopDao {

    private final ConnectionStoreFactory storeFactory;

    public ShopDaoImpl(ConnectionStoreFactory storeFactory) {
        this.storeFactory = storeFactory;
    }

    private static final String CREATE_SHOP_QUERY = "insert into shops values (default, ?, ?, ?)";
    private static final String UPDATE_SHOP_QUERY = "update shops set name = ?, address = ?, status = ? where id = ?";
    private static final String DELETE_SHOP_QUERY = "delete from shops where id = ?";
    private static final String EXIST_SHOP_BY_ID_QUERY = "select count(*) as exist from shops where id = ";
    private static final String FIND_SHOP_BY_ID_QUERY = "select * from shops where id = ";
    private static final String FIND_ALL_SHOPS_QUERY = "select * from shops";
    /*private static final String FIND_ALL_VIEW_SHOP_QUERY = "select s.id, s.name, s.address, s.status, count(shop_id) as count_of_products \n" +
            "from shops as s \n" +
            "         left join shop_product as sp on s.id = sp.shop_id \n" +
            "group by s.id";*/
    /*private static final String FIND_ALL_VIEW_SHOP_BY_PRODUCT_QUERY = "select s.id, s.name, s.address, s.status, (select count(shop_id) from shop_product where shop_id=s.id)  as count_of_products \n" +
            "from shops as s \n" +
            "left join shop_product as sp on s.id = sp.shop_id \n" +
            "  where sp.product_id= "? \n" +
           "group by s.id";*/

    private static final String FIND_ALL_SHOPS_BY_PRODUCT_ID_QUERY = "select s.id, s.name, s.address, s.status \n" +
            "from shops as s \n" +
            "left join shop_product as sp on s.id = sp.shop_id \n" +
            "  where sp.product_id= ";

    private static final String INSERT_REFERENCED_TABLE = "INSERT INTO shop_product (shop_id, product_id) values(?, ?)";

    private static final String FIND_ALL_PRODUCTS_NOT_IN_QUERY = "SELECT p.id, p.name " +
            "FROM    products as p " +
            "WHERE   id NOT IN" +
            "        (" +
            "        SELECT  p.id" +
            "        FROM    products p" +
            "        left join shop_product as sp on p.id = sp.product_id  where sp.shop_id = ";

    @Override
    public void create(Shop entity) {
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(CREATE_SHOP_QUERY)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAddress());
            ps.setString(3, String.valueOf(entity.getStatus()));
            ps.execute();
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
    }

    @Override
    public void createReferencedConnection(ReferenceViewDto referenceViewDto) {
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(INSERT_REFERENCED_TABLE)) {
            ps.setInt(1, referenceViewDto.getShopId());
            ps.setInt(2, referenceViewDto.getProductId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
    }


    @Override
    public long count() { //????????
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery("select count(*) as count from shops")) {
            while (rs.next()) {
                return rs.getLong("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long countByReferencedId(int id) {
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery("select count(*) as count from shops as s " +
                     "join shop_product sp on s.id = sp.shop_id" +
                     " join products p on p.id = sp.product_id where p.id = " + id)) {
            while (rs.next()) {
                return rs.getLong("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void update(Shop entity) {
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(UPDATE_SHOP_QUERY)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAddress());
            ps.setString(3, String.valueOf(entity.getStatus()));
            ps.setInt(4, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(DELETE_SHOP_QUERY)) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
    }

    @Override
    public boolean existById(Integer id) {
        long count = 0;
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(EXIST_SHOP_BY_ID_QUERY + id)) {
            if (rs.next()) {
                count = rs.getLong("exist");
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return count == 1;
    }

    @Override
    public Shop findById(Integer id) {
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_SHOP_BY_ID_QUERY + id)) {
            if (rs.next()) {
                return convertResultSetToShop(rs);
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return null;
    }

    @Override
    public  List<Product> findAllByNotIn(Integer id) {
        List<Product> products = new ArrayList<>();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL_PRODUCTS_NOT_IN_QUERY + id+" )")) {
            while (rs.next()) {
                products.add(convertResultSetToProductNotIn(rs));
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return products;
    }

    @Override
    public DataTableResponse<Shop> findAll(DataTableRequest request) {
        List<Shop> shops = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();

        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        String sql = "select s.id, s.name, s.address, s.status, count(shop_id) as count_of_products " +
                "from shops as s left join shop_product as sp on s.id = sp.shop_id " +
                "group by s.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();

        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                ShopResultSet shopResultSet = convertResultSetToSimpleShop(rs);
                shops.add(shopResultSet.getShop());
                otherParamMap.put(shopResultSet.getShop().getId(), shopResultSet.getProductCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DataTableResponse<Shop> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(shops);
        tableResponse.setOtherParamMap(otherParamMap);
        return tableResponse;
    }

    @Override
    public Map<Integer, String> findAllByProductId(Integer productId) {
        Map<Integer, String> map = new HashMap<>();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL_SHOPS_BY_PRODUCT_ID_QUERY + productId)) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                map.put(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public DataTableResponse<Shop> findAllPrepareViewByProduct(DataTableRequest request, int id) {
        List<Shop> shops = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sql = "select s.id, s.name, s.address, s.status, " +
                "(select count(shop_id) from shop_product where shop_id=s.id)  as count_of_products " +
                "from shops as s" +
                " left join shop_product as sp on s.id = sp.shop_id  where sp.product_id=  " + id +
                " order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                ShopResultSet shopResultSet = convertResultSetToSimpleShop(rs);
                shops.add(shopResultSet.getShop());
                otherParamMap.put(shopResultSet.getShop().getId(), shopResultSet.getProductCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataTableResponse<Shop> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(shops);
        tableResponse.setOtherParamMap(otherParamMap);
        return tableResponse;
    }

    private Shop convertResultSetToShop(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String status = rs.getString("status");
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(name);
        shop.setAddress(address);
        shop.setStatus(ShopStatus.valueOf(status));
        return shop;
    }

    private ShopResultSet convertResultSetToSimpleShop(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String status = rs.getString("status");
        int countOfProducts = rs.getInt("count_of_products");

        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(name);
        shop.setAddress(address);
        shop.setStatus(ShopStatus.valueOf(status));
        return new ShopResultSet(shop, countOfProducts);
    }

    private Product convertResultSetToProductNotIn(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        return product;
    }

    @Getter
    private static class ShopResultSet {

        private final Shop shop;
        private final int productCount;

        private ShopResultSet(Shop shop, int productCount) {
            this.shop = shop;
            this.productCount = productCount;
        }
    }
}
