package cz.muni.fi.pa165.projects.library.mvc.config;

import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @author jkaspar
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {DataLoadingFacade.class})
public class DataConfiguration {

    private final static Logger LOG = LoggerFactory.getLogger(DataConfiguration.class);

    @Autowired
    private DataLoadingFacade dataLoadingFacade;

    @PostConstruct
    public void LoadData() {
        LOG.info("Populate database");
        dataLoadingFacade.loadData();
    }
}
