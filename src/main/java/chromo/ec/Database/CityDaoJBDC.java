package chromo.ec.Database;

import chromo.ec.Entity.City;
import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static chromo.ec.Database.SQLConnect.getConnection;

public class CityDaoJBDC implements CityDao{

    private static final String FIND_CITY_BY_ID = "SELECT * FROM city WHERE ID = ?";

    private PreparedStatement create_cityFindById(Connection connection, int ID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_CITY_BY_ID);
        statement.setInt(1, ID);
        return statement;
    }

    @Override
    public City cityFindById(int ID) {
        try(
                PreparedStatement s = create_cityFindById(getConnection(), ID);
                ResultSet r = s.executeQuery())
        {
            r.next();
            return createCityFromResultSet(r);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String FIND_BY_CODE = "SELECT * FROM city WHERE CountryCode = ?";

    private PreparedStatement create_findByCode(Connection connection, String code) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_CODE);
        statement.setString(1,code);
        return statement;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> ret = new ArrayList<>();
        try(
                PreparedStatement s = create_findByCode(getConnection(), code);
                ResultSet r = s.executeQuery()
        )
        {
            while(r.next()){
                ret.add(createCityFromResultSet(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static final String FIND_BY_NAME = "SELECT * FROM city WHERE NAME = ?";

    private PreparedStatement create_findByName(Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1,name);
        return statement;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> ret = new ArrayList<>();
        try(
                PreparedStatement s = create_findByName(getConnection(), name);
                ResultSet r = s.executeQuery()
        )
        {
            while(r.next()){
                ret.add(createCityFromResultSet(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static final String FIND_ALL = "SELECT * FROM city";

    @Override
    public List<City> findAll() {
        List<City> ret = new ArrayList<>();
        try(
            PreparedStatement s = getConnection().prepareStatement(FIND_ALL);
            ResultSet r = s.executeQuery()
        ){
            while(r.next()){
                ret.add(createCityFromResultSet(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }


    private static final String ADD = "INSERT INTO city VALUES ( ? , ? , ? , ? , ? )";

    private PreparedStatement create_add(Connection connection, int id, String name, String countryCode, String district, int population) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD);
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setString(3, countryCode);
        statement.setString(4, district);
        statement.setInt(5, population);
        return statement;
    }

    @Override
    public City add(City city) {
        try{
            if(!findAll().stream().filter(c -> c.getID() == city.getID()).findFirst().isPresent()
                    && !city.getCountryCode().isEmpty()
                    && !city.getName().isEmpty()
                    && !city.getDistrict().isEmpty()
                    && city.getPopulation() > 0){

                        PreparedStatement s = create_add(getConnection(),
                        city.getID(),
                        city.getName(),
                        city.getCountryCode(),
                        city.getDistrict(),
                        city.getPopulation());

                s.executeUpdate();
                return city;
            }else{
                System.out.println("ID is already present, cannot override\nOr one of the values equals null");
            }
        }catch(NullPointerException e){
            System.out.println("NULL EXCEPTION!!!!" + e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL EXCEPTION!!!!"+  e.getMessage());
        }
        return city;
    }

    private static final String UPDATE_CITY = "UPDATE city SET Name=?,CountryCode=?,District=?,Population=? WHERE city.ID=?";

    private PreparedStatement create_Update(Connection connection, String name, String countryCode, String district, int population, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_CITY);
        statement.setString(1, name);
        statement.setString(2, countryCode);
        statement.setString(3, district);
        statement.setInt(4, population);
        statement.setInt(5, id);
        return statement;
    }

    @Override
    public City update(City city) {
        try{
            if(findAll().stream().anyMatch(c -> c.getID() == city.getID())){
                PreparedStatement s = create_Update(getConnection(),
                city.getName(),
                city.getCountryCode(),
                city.getDistrict(),
                city.getPopulation(),
                city.getID());
                s.executeUpdate();
            }else{
                System.out.println("id not present");
            }
        } catch(NullPointerException e){
        System.out.println("NULL EXCEPTION!!!!" + e.getMessage());
        }catch(SQLException e){
        System.out.println("SQL EXCEPTION!!!!"+  e.getMessage());
        }
        return city;
    }

    private static final String DELETE = "DELETE FROM city WHERE ID = ?";

    private PreparedStatement create_delete(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE);
        statement.setInt(1,id);
        return statement;
    }

    @Override
    public int delete(City city) {
        try{
        if(city.equals(cityFindById(city.getID()))){
            PreparedStatement s = create_delete(getConnection(), city.getID());
            s.executeUpdate();
            return 1;
        }
        }catch(NullPointerException e){
            System.out.println("NULL EXCEPTION!!!!" + e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL EXCEPTION!!!!"+  e.getMessage());
        }
        return 0;
    }

    private City createCityFromResultSet(ResultSet resultSet) throws SQLException {
        return new City(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
    }
}
