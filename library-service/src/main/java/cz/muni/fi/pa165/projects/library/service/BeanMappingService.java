package cz.muni.fi.pa165.projects.library.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Created by lajci on 15.11.2015.
 */
public interface BeanMappingService {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);
    Mapper getMapper();
}
