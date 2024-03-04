package com.example.lab6_fx_15_11.Validator;


import com.example.lab6_fx_15_11.Validator.*;
import com.example.lab6_fx_15_11.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        //TODO: implement method validate
        if(entity.getLastName().isEmpty())
            throw new ValidationException("NUmele trebuie sa contina caractere");
        if(entity.getFirstName().isEmpty())
            throw new ValidationException("Prenumele trebuie sa contina caractere");

    }
}
