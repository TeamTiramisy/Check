package com.console.check.http.servlet;

import com.console.check.config.ApplicationConfiguration;
import com.console.check.dto.CardCreateDto;
import com.console.check.dto.CardReadDto;
import com.console.check.service.CardCrudService;
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
public class CardServlet extends HttpServlet {

    private final CardCrudService cardCrudService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));

        String card = new Gson().toJson(cardCrudService.findById(id));

        try(PrintWriter writer = resp.getWriter()){
            writer.write(card);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = new Gson().fromJson(req.getReader(), JsonObject.class);
        String bonus = jsonObject.get("bonus").toString().replaceAll("\"", "");

        CardCreateDto cardCreateDto = CardCreateDto.builder()
                .bonus(bonus)
                .build();

        CardReadDto cardReadDto = cardCrudService.save(cardCreateDto);

        String json = new Gson().toJson(cardReadDto);

        try(PrintWriter writer = resp.getWriter()){
            writer.write(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));

        boolean delete = cardCrudService.delete(id);

        try(PrintWriter writer = resp.getWriter()){
            writer.write(String.valueOf(delete));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = new Gson().fromJson(req.getReader(), JsonObject.class);
        Integer id = Integer.valueOf(jsonObject.get("id").toString().replaceAll("\"", ""));
        String bonus = jsonObject.get("bonus").toString().replaceAll("\"", "");

        CardCreateDto cardCreateDto = CardCreateDto.builder()
                .bonus(bonus)
                .build();

        cardCrudService.update(id, cardCreateDto);
    }
}
