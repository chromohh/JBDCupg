package chromo.ec.Entity;

import java.util.Objects;

public class City {

    private int ID, Population;
    private String Name, CountryCode, District;

    public City(int ID, String name, String countryCode, String district, int population) {
        this.ID = ID;
        Name = name;
        CountryCode = countryCode;
        District = district;
        Population = population;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public String getDistrict() {
        return District;
    }

    public int getPopulation() {
        return Population;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public void setPopulation(int population) {
        Population = population;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return ID == city.ID &&
                Population == city.Population &&
                Objects.equals(Name, city.Name) &&
                Objects.equals(CountryCode, city.CountryCode) &&
                Objects.equals(District, city.District);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Name, CountryCode, District, Population);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("ID=").append(ID);
        sb.append(", Name='").append(Name).append('\'');
        sb.append(", CountryCode='").append(CountryCode).append('\'');
        sb.append(", District='").append(District).append('\'');
        sb.append(", Population=").append(Population);
        sb.append('}');
        return sb.toString();
    }

}
