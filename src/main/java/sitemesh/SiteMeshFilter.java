package sitemesh;

import javax.servlet.annotation.WebFilter;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

@WebFilter("/*") 
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addDecoratorPath("/users/*", "/layout/MainLayout.jsp")
		.addExcludedPath("/users/loginForm")
		.addExcludedPath("/users/idForm")
		.addExcludedPath("/users/id")
		.addExcludedPath("/users/resetPwForm")
		.addExcludedPath("/users/info")
		.addExcludedPath("/users/updateForm")
		.addExcludedPath("/users/pwForm");
	}
}
