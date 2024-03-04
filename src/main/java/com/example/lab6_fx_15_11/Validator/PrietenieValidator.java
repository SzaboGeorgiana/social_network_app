package com.example.lab6_fx_15_11.Validator;

import com.example.lab6_fx_15_11.domain.Prietenie;
import com.example.lab6_fx_15_11.domain.Utilizator;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.Long.parseLong;

public class PrietenieValidator implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        //TODO: implement method validate
        if(entity.getDate().isAfter(LocalDateTime.now()))
            throw new ValidationException("Data trebuie sa fie dinainte de momentul curent");

        if(entity.getId().getLeft()==null || (entity.getId().getRight())==null)
            throw new ValidationException("id ul nu poate fi null");
        if(entity.getId().getLeft().intValue()==entity.getId().getRight().intValue())
            throw new ValidationException("idurile nu pot apartine de aceasi persona");
    }
}