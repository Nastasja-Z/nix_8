package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.impl;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.ProductDao;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Category;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.store.ConnectionStoreFactory;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.product.ProductResponseDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductDaoImpl implements ProductDao {

    private final ConnectionStoreFactory storeFactory;

    public ProductDaoImpl(ConnectionStoreFactory storeFactory) {
        this.storeFactory = storeFactory;
    }

    private static final String CREATE_PRODUCT_QUERY = "insert into products values (default, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT_QUERY = "update products set name = ?, category = ?, weight = ?, description = ?, price = ? where id = ?";
    private static final String DELETE_PRODUCT_QUERY = "delete from products where id = ?";
    private static final String EXIST_PRODUCT_BY_ID_QUERY = "select count(*) as exist from products where id = ";
    private static final String FIND_PRODUCT_BY_ID_QUERY = "select * from products where id = ";
    private static final String FIND_ALL_PRODUCTS_QUERY = "select * from products";
   /* private static final String FIND_ALL_VIEW_PRODUCT_QUERY = "select p.id, p.name, p.category, p.weight, p.description, p.price, count(product_id) as count_of_shops \n" +
            "from products as p \n" +
            "         left join shop_product as sp on p.id = sp.product_id \n" +
            "group by p.id";
    private static final String FIND_ALL_VIEW_PRODUCT_BY_SHOP_QUERY = "select p.id, p.name, p.category, p.weight, p.description, p.price, (select count(product_id) from shop_product where product_id=p.id)  as count_of_shops \n" +
            "from products as p \n" +
            "left join shop_product as sp on p.id = sp.product_id \n" +
            "  where sp.shop_id= ? \n" +
            "group by p.id";*/
    private static final String FIND_ALL_PRODUCTS_BY_SHOP_ID_QUERY = "select p.id, p.name, p.category, p.weight, p.description, p.price \n" +
            "from products as p \n" +
            "left join shop_product as sp on p.id = sp.product_id \n" +
            "  where sp.shop_id= ";

    private static final String INSERT_REFERENCED_TABLE = "INSERT INTO shop_product (shop_id, product_id) values(?, ?)";

    private static final String FIND_ALL_PRODUCTS_NOT_IN_QUERY ="SELECT s.id, s.name " +
            "FROM    shops as s " +
            "WHERE   id NOT IN" +
            "        (" +
            "        SELECT  s.id" +
            "        FROM    shops s" +
            "        left join shop_product as sp on s.id = sp.shop_id  where sp.product_id = ";
    @Override
    public void create(Product entity) {
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(CREATE_PRODUCT_QUERY)) {
            ps.setString(1, entity.getName());
            ps.setString(2, String.valueOf(entity.getCategory()));
            ps.setFloat(3, entity.getWeight());
            ps.setString(4, entity.getDescription());
            ps.setFloat(5, entity.getPrice());

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
    public long count() {
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery("select count(*) as count from products")) {
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
             ResultSet rs = statement.executeQuery("select count(*) as count from products as p " +
                     "join shop_product sp on p.id = sp.product_id " +
                     "join shops s on s.id = sp.shop_id where s.id = " + id)) {
            while (rs.next()) {
                return rs.getLong("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void update(Product entity) {
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(UPDATE_PRODUCT_QUERY)) {
            ps.setString(1, entity.getName());
            ps.setString(2, String.valueOf(entity.getCategory()));
            ps.setFloat(3, entity.getWeight());
            ps.setString(4, entity.getDescription());
            ps.setFloat(5, entity.getPrice());
            ps.setInt(6, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(DELETE_PRODUCT_QUERY)) {
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
             ResultSet rs = statement.executeQuery(EXIST_PRODUCT_BY_ID_QUERY + id)) {
            if (rs.next()) {
                count = rs.getLong("exist");
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return count == 1;
    }

    @Override
    public Product findById(Integer id) {
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_PRODUCT_BY_ID_QUERY + id)) {
            if (rs.next()) {
                return convertResultSetToProduct(rs);
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Shop> findAllByNotIn(Integer id) {
        List<Shop> shops = new ArrayList<>();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL_PRODUCTS_NOT_IN_QUERY +id+ " )")) {
            while (rs.next()) {
                shops.add(convertResultSetToProductNotIn(rs));
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return shops;
    }

    @Override
    public DataTableResponse<Product> findAll(DataTableRequest request) {
        List<Product> products = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sql = "select p.id, p.name, p.category, p.weight, p.price, count(product_id) as count_of_shops " +
                "from products as p left join shop_product as sp on p.id = sp.product_id " +
                "group by p.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();

        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                ProductDaoImpl.ProductResultSet productResultSet = convertResultSetToSimpleProduct(rs);
                products.add(productResultSet.getProduct());
                otherParamMap.put(productResultSet.getProduct().getId(), productResultSet.getShopCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DataTableResponse<Product> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(products);
        tableResponse.setOtherParamMap(otherParamMap);
        return tableResponse;
    }

    private ProductResultSet convertResultSetToSimpleProduct(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String category = rs.getString("category");
        float weight = rs.getFloat("weight");
        float price = rs.getFloat("price");
        int countOfShops = rs.getInt("count_of_shops");

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCategory(Category.valueOf(category));
        product.setWeight(weight);
        product.setPrice(price);
        return new ProductDaoImpl.ProductResultSet(product, countOfShops);
    }

    @Override
    public DataTableResponse<Product> findAllPrepareViewByShop(DataTableRequest request, int id) {
        List<Product> products = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sql = "select p.id, p.name, p.category, p.weight, p.description, p.price, " +
                "(select count(product_id) from shop_product where product_id=p.id)  as count_of_shops" +
                " from products as p " +
                "left join shop_product as sp on p.id = sp.product_id " +
                " where sp.shop_id =  " + id + " order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                ProductResultSet productResultSet = convertResultSetToSimpleProductPrepareView(rs);
                products.add(productResultSet.getProduct());
                otherParamMap.put(productResultSet.getProduct().getId(), productResultSet.getShopCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataTableResponse<Product> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(products);
        tableResponse.setOtherParamMap(otherParamMap);
        return tableResponse;
    }

    private ProductResultSet convertResultSetToSimpleProductPrepareView(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String category = rs.getString("category");
        float weight = rs.getFloat("weight");
        String description = rs.getString("description");
        float price = rs.getFloat("price");
        int countOfShops = rs.getInt("count_of_shops");

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCategory(Category.valueOf(category));
        product.setWeight(weight);
        product.setDescription(description);
        product.setPrice(price);
        return new ProductDaoImpl.ProductResultSet(product, countOfShops);
    }

    @Override
    public Map<Integer, String> findAllByShopId(Integer shopId) {
        Map<Integer, String> map = new HashMap<>();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL_PRODUCTS_BY_SHOP_ID_QUERY + shopId)) {
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

    private Shop convertResultSetToProductNotIn(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");

        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(name);

        return shop;
    }

    private Product convertResultSetToProduct(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String category = rs.getString("category");
        Float weight = rs.getFloat("weight");
        String description = rs.getString("description");
        Float price = rs.getFloat("price");
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCategory(Category.valueOf(category));
        product.setWeight(weight);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

    private ProductResponseDto convertResultSetToShopViewDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String category = rs.getString("category");
        Float weight = rs.getFloat("weight");
        String description = rs.getString("description");
        Float price = rs.getFloat("price");
        int countOfShops = rs.getInt("count_of_shops");
        ProductResponseDto product = new ProductResponseDto();
        product.setId(id);
        product.setName(name);
        product.setCategory(category);
        product.setWeight(weight);
        product.setDescription(description);
        product.setPrice(price);
        product.setCountOfShops(countOfShops);
        return product;
    }

    @Getter
    private static class ProductResultSet {

        private final Product product;
        private final int shopCount;

        private ProductResultSet(Product product, int shopCount) {
            this.product = product;
            this.shopCount = shopCount;
        }
    }
}
