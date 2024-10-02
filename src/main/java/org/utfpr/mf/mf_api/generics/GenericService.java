package org.utfpr.mf.mf_api.generics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class GenericService<TModel, TPk, TRepository extends JpaRepository<TModel, TPk>> {

    protected TRepository repository;

    public GenericService(TRepository repository)
    {
        this.repository = repository;
    }

    public TModel insert(TModel model)
    {
        return repository.save(model);
    }

    public List<TModel> findAll()
    {
        return repository.findAll();
    }

    public Optional<TModel> findById(TPk id) {return repository.findById(id);}

}
