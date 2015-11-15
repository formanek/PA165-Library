package cz.muni.fi.pa165.projects.library.service;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lajci on 15.11.2015.
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService{

    @Inject
    private Mapper mapper;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(mapper.map(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return mapper.map(u, mapToClass);
    }

    @Override
    public Mapper getMapper() {
        return mapper;
    }
}
