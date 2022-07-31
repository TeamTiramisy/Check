package com.console.check.servlet;

import com.console.check.dto.CardCreateDto;
import com.console.check.dto.ProductCreateDto;
import com.console.check.service.CardService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/update/card")
public class UpdateCardServlet extends HttpServlet {

    private final CardService cardService = CardService.getInstance();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = new Gson().fromJson(req.getReader(), JsonObject.class);
        Integer id = Integer.valueOf(jsonObject.get("id").toString().replaceAll("\"", ""));
        String bonus = jsonObject.get("bonus").toString().replaceAll("\"", "");

        CardCreateDto cardCreateDto = CardCreateDto.builder()
                .bonus(bonus)
                .build();

        cardService.update(id, cardCreateDto);
    }
}
