package com.footballproject.services;

import com.footballproject.models.Country;
import com.footballproject.repositories.contracts.CountryRepository;
import com.footballproject.services.contracts.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.getAllCountries();
    }

    @Override
    public Country getCountryById(long id) {
        Country country = countryRepository.getCountryById(id);
        if (country == null) {
            throw new EntityNotFoundException(
                    String.format("Country with id %d not found!", id));
        }
        return countryRepository.getCountryById(id);
    }
}
