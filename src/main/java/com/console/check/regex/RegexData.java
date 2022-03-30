package com.console.check.regex;

import com.console.check.collection.CustomArrayList;
import com.console.check.collection.CustomList;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public final class RegexData {

    @SneakyThrows
    public static CustomList<String> validation(String parameters) {
        CustomList<String> list = new CustomArrayList<>();
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
