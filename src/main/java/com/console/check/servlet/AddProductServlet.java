package com.console.check.servlet;

import com.console.check.dto.ProductCreateDto;
import com.console.check.dto.ProductReadDto;
import com.console.check.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/add/product")
public class AddProductServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();

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

        ProductReadDto product = productService.save(productCreateDto);

        String json = new Gson().toJson(product);

        try(PrintWriter writer = resp.getWriter()){
            writer.write(json);
        }
    }
}
