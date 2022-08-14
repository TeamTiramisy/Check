package com.console.check.http.initializer;

import com.console.check.config.ApplicationConfiguration;
import com.console.check.http.servlet.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class AnnotationWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfiguration.class);
        context.refresh();


        CardsServlet cardsServlet = (CardsServlet) context.getBean("cardsServlet");
        CardServlet cardServlet = (CardServlet) context.getBean("cardServlet");
        ProductsServlet productsServlet = (ProductsServlet) context.getBean("productsServlet");
        ProductServlet productServlet = (ProductServlet) context.getBean("productServlet");
        CheckServlet checkServlet = (CheckServlet) context.getBean("checkServlet");


        ServletRegistration.Dynamic cards = servletContext.addServlet("cardsServlet", cardsServlet);
        ServletRegistration.Dynamic card = servletContext.addServlet("cardServlet", cardServlet);
        ServletRegistration.Dynamic products = servletContext.addServlet("productsServlet", productsServlet);
        ServletRegistration.Dynamic product = servletContext.addServlet("productServlet", productServlet);
        ServletRegistration.Dynamic check = servletContext.addServlet("checkServlet", checkServlet);


        cards.addMapping("/cards");
        card.addMapping("/card");
        products.addMapping("/products");
        product.addMapping("/product");
        check.addMapping("/check");
    }
}
