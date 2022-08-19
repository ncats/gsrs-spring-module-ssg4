package gov.hhs.gsrs.ssg4.ssg4m;

import gov.hhs.gsrs.ssg4.ssg4m.Ssg4mStarterEntityRegistrar;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(Ssg4mStarterEntityRegistrar.class)
public class Ssg4mConfiguration {
}
