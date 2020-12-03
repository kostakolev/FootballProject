package com.footballproject.repositories.contracts;

import com.footballproject.models.Country;
import java.util.List;

public interface CountryRepository {

    List<Country> getAllCountries();

    Country getCountryById(long id);
}
