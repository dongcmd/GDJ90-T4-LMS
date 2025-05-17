package sitemesh;

import javax.servlet.annotation.WebFilter;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

//원동인
@WebFilter("/*")
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		// 이동원
		builder.addDecoratorPath("/users/*", "/layouts/MainLayout.jsp")
		.addExcludedPath("/users/loginForm")
		.addExcludedPath("/users/idForm")
		.addExcludedPath("/users/id")
		.addExcludedPath("/users/resetPwForm")
		.addExcludedPath("/users/info")
		.addExcludedPath("/users/updateForm")
		.addExcludedPath("/users/pwForm");
		builder.addDecoratorPath("/mainLMS/*", "/layouts/MainLayout.jsp");
		builder.addDecoratorPath("/deptLMS/*", "/layouts/MainLayout.jsp");
		builder.addDecoratorPath("/classLMS/*", "/layouts/MainLayout.jsp");
		builder.addDecoratorPath("/board/*", "/layouts/MainLayout.jsp");

	}
}
