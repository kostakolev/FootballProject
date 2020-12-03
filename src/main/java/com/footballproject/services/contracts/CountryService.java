package com.footballproject.services.contracts;

import com.footballproject.models.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();

    Country getCountryById(long id);
}
