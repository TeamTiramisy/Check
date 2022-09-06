package com.console.check.controller;


import com.console.check.dto.CheckDto;
import com.console.check.service.PrintCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


@RestController
@RequestMapping("/api/v1/check")
@RequiredArgsConstructor
public class CheckRestController {

    private final PrintCheckService printCheckService;

    @GetMapping
    public void findAll(CheckDto checkDto,
                        HttpServletResponse resp) throws IOException {
        printCheckService.printCheck(checkDto, resp.getOutputStream());

    }
}
