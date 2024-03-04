package com.example.lab6_fx_15_11.repo;



import com.example.lab6_fx_15_11.domain.Utilizator;
import com.example.lab6_fx_15_11.Validator.*;
import java.util.List;

public class UtilizatorFileRepo extends AbstractFileRepo<Long, Utilizator> {

    public UtilizatorFileRepo(String fileName, Validator<Utilizator> validator) {
        super(fileName, validator);
    }

    @Override
    public Utilizator extractEntity(List<String> attributes) {
        //TODO: implement method
        Utilizator user = new Utilizator(attributes.get(1),attributes.get(2),"aa");
        user.setId(Long.parseLong(attributes.get(0)));

        return user;
    }

    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getId()+";"+entity.getFirstName()+";"+entity.getLastName();
    }
}
