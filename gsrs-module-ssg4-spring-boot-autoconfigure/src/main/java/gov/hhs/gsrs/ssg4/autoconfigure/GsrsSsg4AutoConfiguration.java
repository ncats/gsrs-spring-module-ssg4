package gov.hhs.gsrs.ssg4.autoconfigure;

import gsrs.EnableGsrsApi;
import gsrs.EnableGsrsJpaEntities;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableGsrsJpaEntities
@EnableGsrsApi
@Configuration
@Import({
})

public class GsrsSsg4AutoConfiguration {
}
