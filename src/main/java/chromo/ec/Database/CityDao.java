package chromo.ec.Database;

import chromo.ec.Entity.City;

import java.util.List;

public interface CityDao {
    City cityFindById(int ID);
    List<City> findByCode(String code);
    List<City> findByName(String name);
    List<City> findAll();
    City add(City city);
    City update(City city);
    int delete(City city);
}
