package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.ShopDao;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.ShopStatus;
import ua.com.alevel.hw_7_data_table_jdbc.store.ConnectionStoreFactory;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ShopViewDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final String FIND_ALL_VIEW_SHOP_QUERY = "select s.id, s.name, s.address, s.status, count(shop_id) as count_of_products \n" +
            "from shops as s \n" +
            "         left join shop_product as sp on s.id = sp.shop_id \n" +
            "group by s.id";
    private static final String FIND_ALL_VIEW_SHOP_BY_PRODUCT_QUERY = "select s.id, s.name, s.address, s.status, (select count(shop_id) from shop_product where shop_id=s.id)  as count_of_products \n" +
            "from shops as s \n" +
            "left join shop_product as sp on s.id = sp.shop_id \n" +
            "  where sp.product_id= ? \n" +
            "group by s.id";

    private static final String INSERT_REFERENCED_TABLE = "insert into shop_product values (?, ?)";

    @Override
    public void create(Shop entity) {
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(CREATE_SHOP_QUERY)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAddress());
            ps.setString(2, String.valueOf(entity.getStatus()));
            ps.execute();
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
    }

    @Override
    public void createReferenceConnection(ReferenceViewDto referenceViewDto) {
        try (PreparedStatement ps = storeFactory.getConnection().prepareStatement(INSERT_REFERENCED_TABLE)) {
            ps.setInt(1, referenceViewDto.getShopId());
            ps.setInt(2, referenceViewDto.getProductId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
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
    public List<Shop> findAll() {
        List<Shop> shops = new ArrayList<>();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL_SHOPS_QUERY)) {
            while (rs.next()) {
                shops.add(convertResultSetToShop(rs));
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return shops;
    }

    @Override
    public  List<ShopViewDto> findAllPrepareView() {
        List<ShopViewDto> shops = new ArrayList<>();
        try (Statement statement = storeFactory.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL_VIEW_SHOP_QUERY)) {
            while (rs.next()) {
                shops.add(convertResultSetToShopViewDto(rs));
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return shops;
    }

    @Override
    public List<ShopViewDto> findAllPrepareViewByProduct(int id) {
        List<ShopViewDto> students = new ArrayList<>();
        try(PreparedStatement ps = storeFactory.getConnection().prepareStatement(FIND_ALL_VIEW_SHOP_BY_PRODUCT_QUERY)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(convertResultSetToShopViewDto(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("sql error = " + e.getMessage());
        }
        return students;
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

    private ShopViewDto convertResultSetToShopViewDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String status = rs.getString("status");
        int countOfProducts = rs.getInt("count_of_products");
        ShopViewDto shop = new ShopViewDto();
        shop.setId(id);
        shop.setName(name);
        shop.setAddress(address);
        shop.setStatus(ShopStatus.valueOf(status));
        shop.setCountOfProducts(countOfProducts);
        return shop;
    }
}
