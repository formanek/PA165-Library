package cz.muni.fi.pa165.projects.library.service.config;

import cz.muni.fi.pa165.projects.library.LibraryApplicationContext;
import cz.muni.fi.pa165.projects.library.dto.LoanCreateDTO;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.service.LoanServiceImpl;
import cz.muni.fi.pa165.projects.library.service.MemberServiceImpl;
import cz.muni.fi.pa165.projects.library.service.facade.LoanFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Jan Mosat
 */
@Configuration
@Import(LibraryApplicationContext.class)
@ComponentScan(basePackageClasses={LoanServiceImpl.class, MemberServiceImpl.class, LoanFacadeImpl.class})
public class ServiceConfiguration {


    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Loan.class, LoanCreateDTO.class).exclude("loanItems");
        }
    }

}
