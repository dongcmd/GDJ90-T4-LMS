package sitemesh;

import javax.servlet.annotation.WebFilter;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

//원동인
@WebFilter("/*")
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		
		builder.addDecoratorPath("/users/*", "/layouts/layout.jsp")
		.addExcludedPath("/users/loginForm")
		.addExcludedPath("/users/idForm")
		.addExcludedPath("/users/id")
		.addExcludedPath("/users/resetPwForm")
		.addExcludedPath("/users/info")
		.addExcludedPath("/users/updateForm")
		.addExcludedPath("/users/pwForm");
		builder.addDecoratorPath("/mainLMS/*", "/layouts/layout.jsp");
		builder.addDecoratorPath("/deptLMS/*", "/layouts/layout.jsp");
		builder.addDecoratorPath("/classLMS/*", "/layouts/layout.jsp");

	}
}
