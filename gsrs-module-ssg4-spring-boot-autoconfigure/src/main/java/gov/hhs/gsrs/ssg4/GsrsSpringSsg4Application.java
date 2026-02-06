package gov.hhs.gsrs.ssg4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableGsrsApi(indexValueMakerDetector = EnableGsrsApi.IndexValueMakerDetector.CONF)
//@EnableGsrsJpaEntities
//@EnableGsrsLegacyAuthentication
//@EnableGsrsLegacyCache
//@EnableGsrsLegacyPayload
//@EnableGsrsLegacySequenceSearch
//@EnableGsrsLegacyStructureSearch
@EntityScan(basePackages ={"ix","gsrs", "gov.nih.ncats", "gov.hhs.gsrs"} )
@EnableJpaRepositories(basePackages ={"ix","gsrs", "gov.nih.ncats", "gov.hhs.gsrs"} )
//@EnableGsrsScheduler
//@EnableGsrsBackup
@EnableAsync

public class GsrsSpringSsg4Application {

    public static void main(String[] args) {
        SpringApplication.run(GsrsSpringSsg4Application.class, args);
    }

}
