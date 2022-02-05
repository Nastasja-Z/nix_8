package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.impl;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.ProductDao;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Category;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.store.ConnectionStoreFactory;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ProductViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    private static final String FIND_ALL_VIEW_PRODUCT_QUERY = "select p.id, p.name, p.category, p.weight, p.description, p.price, count(product_id) as count_of_shops \n" +
            "from products as p \n" +
            "         left join shop_product as sp on p.id = sp.product_id \n" +
            "group by p.id";
    private static final String FIND_ALL_VIEW_PRODUCT_BY_SHOP_QUERY = "select p.id, p.name, p.category, p.weight, p.description, p.price, (select count(product_id) from shop_product where product_id=p.id)  as count_of_shops \n" +
            "from products as p \n" +
            "left join shop_product as sp on p.id = sp.product_id \n" +
            "  where sp.shop_id= ? \n" +
            "group by p.id";

//    private static final String INSERT_REFERENCED_TABLE = "insert into shop_product values (?, ?)";
private static final String INSERT_REFERENCED_TABLE = "INSERT INTO shop_product (shop_id, product_id) values(?, ?)";
        //"values((select s.id = ? from shops as s), (select p.id = ? from products as p))";
        //"values(select s.id from shops as s where s.id = ?, select p.id from products as p where p.id = ?)";

        /*"SELECT s.id, p.id\n" +
        "FROM shops s, products p\n" +
        "WHERE s.id = ? AND p.id = ?;";*/

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
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL_PRODUCTS_QUERY)) {
            while (rs.next()) {
                products.add(convertResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return products;
    }

    @Override
    public DataTableResponse<Product> findAll(DataTableRequest request) {
        return null;
    }

    @Override
    public List<ProductViewDto> findAllPrepareView() {
        List<ProductViewDto> products = new ArrayList<>();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL_VIEW_PRODUCT_QUERY)) {
            while (rs.next()) {
                products.add(convertResultSetToShopViewDto(rs));
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<ProductViewDto> findAllPrepareViewByShop(int id) {
        List<ProductViewDto> products = new ArrayList<>();
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(FIND_ALL_VIEW_PRODUCT_BY_SHOP_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(convertResultSetToShopViewDto(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return products;
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

    private ProductViewDto convertResultSetToShopViewDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String category = rs.getString("category");
        Float weight = rs.getFloat("weight");
        String description = rs.getString("description");
        Float price = rs.getFloat("price");
        int countOfShops = rs.getInt("count_of_shops");
        ProductViewDto product = new ProductViewDto();
        product.setId(id);
        product.setName(name);
        product.setCategory(Category.valueOf(category));
        product.setWeight(weight);
        product.setDescription(description);
        product.setPrice(price);
        product.setCountOfShops(countOfShops);
        return product;
    }
}
