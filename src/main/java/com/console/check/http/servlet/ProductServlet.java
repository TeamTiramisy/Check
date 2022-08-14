package com.console.check.http.servlet;


import com.console.check.config.ApplicationConfiguration;
import com.console.check.dto.ProductCreateDto;
import com.console.check.dto.ProductReadDto;
import com.console.check.service.ProductCrudService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
@Component
@RequiredArgsConstructor
public class ProductServlet extends HttpServlet {

    private final ProductCrudService productCrudService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));

        String product = new Gson().toJson(productCrudService.findById(id));

        try(PrintWriter writer = resp.getWriter()){
            writer.write(product);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = new Gson().fromJson(req.getReader(), JsonObject.class);
        String qua = jsonObject.get("qua").toString().replaceAll("\"", "");
        String name = jsonObject.get("name").toString().replaceAll("\"", "");
        String cost = jsonObject.get("cost").toString().replaceAll("\"", "");
        String promo = jsonObject.get("promo").toString().replaceAll("\"", "");

        ProductCreateDto productCreateDto = ProductCreateDto.builder()
                .qua(qua)
                .name(name)
                .cost(cost)
                .promo(promo)
                .build();

        ProductReadDto product = productCrudService.save(productCreateDto);

        String json = new Gson().toJson(product);

        try(PrintWriter writer = resp.getWriter()){
            writer.write(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));

        boolean delete = productCrudService.delete(id);

        try(PrintWriter writer = resp.getWriter()){
            writer.write(String.valueOf(delete));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = new Gson().fromJson(req.getReader(), JsonObject.class);
        Integer id = Integer.valueOf(jsonObject.get("id").toString().replaceAll("\"", ""));
        String qua = jsonObject.get("qua").toString().replaceAll("\"", "");
        String name = jsonObject.get("name").toString().replaceAll("\"", "");
        String cost = jsonObject.get("cost").toString().replaceAll("\"", "");
        String promo = jsonObject.get("promo").toString().replaceAll("\"", "");


        ProductCreateDto productCreateDto = ProductCreateDto.builder()
                .qua(qua)
                .name(name)
                .cost(cost)
                .promo(promo)
                .build();

        productCrudService.update(id, productCreateDto);
    }
}
