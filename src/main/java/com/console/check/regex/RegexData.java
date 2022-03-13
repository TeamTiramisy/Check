package com.console.check.regex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexData {

    private RegexData(){}

    public static List<String> validation(String parameters) throws IOException {
        List<String> list = new ArrayList<>();
        String regex = "(?<!.)([1-9]\\d?|100);(([А-Я][а-я]{2,29})|([A-Z][a-z]{2,29}));([1-9]\\d?\\.\\d\\d|100\\.00);([1-9]|[1]\\d|20)(?!.)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameters);

        String s = parameters.replaceAll(regex, "").strip();
        Path path = Path.of("fileCheck/invalidData.txt");

        Files.write(path, s.getBytes());

        while (matcher.find()){
            list.add(matcher.group());
        }
        return list;
    }
}
