package org.utfpr.mf.mf_api.generics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public abstract class GenericController<TModel, TService extends GenericService<TModel, TPk, ?>, TPk> {

    private Class<TPk> pkClass;


    private static <T> T parseObject(Class<T> dest, Object source)
    {
        return dest.cast(source);
    }

    protected TService service;

    public GenericController(TService service) {
        this.service = service;
    }

    @PostMapping("/insert")
    public TModel insert(@RequestBody TModel model)
    {
        return service.insert(model);
    }

    @GetMapping("/find-all")
    public List<TModel> findAll()
    {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<TModel> findById(@PathVariable String id) {
        return service.findById(parseObject(pkClass, id));
    }

}
