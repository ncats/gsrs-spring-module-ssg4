package gov.hhs.gsrs.ssg4.ssg4m;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({Ssg4mSelector.class, Ssg4mConfiguration.class})
public @interface EnableSsg4m {
}
