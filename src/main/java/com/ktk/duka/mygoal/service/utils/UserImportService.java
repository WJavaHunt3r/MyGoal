package com.ktk.duka.mygoal.service.utils;

import com.ktk.duka.mygoal.enums.Role;
import com.ktk.duka.mygoal.security.SecurityUtils;
import com.ktk.duka.mygoal.service.user.User;
import com.ktk.duka.mygoal.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserImportService {
    private static final String USER_DATA_URL = "C:\\Users\\wagne\\OneDrive\\Dokumentumok\\Myshare\\Pmo_lista.csv";
    private static final Map<String, String> ENGLISH_CHARS = new HashMap();

    private UserService userService;

    public UserImportService(UserService userService) {
        this.userService = userService;
    }

    static {
        ENGLISH_CHARS.put("á", "a");
        ENGLISH_CHARS.put("é", "e");
        ENGLISH_CHARS.put("í", "i");
        ENGLISH_CHARS.put("ó", "o");
        ENGLISH_CHARS.put("ö", "o");
        ENGLISH_CHARS.put("ő", "o");
        ENGLISH_CHARS.put("ú", "u");
        ENGLISH_CHARS.put("ü", "u");
        ENGLISH_CHARS.put("ű", "u");
        ENGLISH_CHARS.put(" ", "");
        ENGLISH_CHARS.put("(", "");
        ENGLISH_CHARS.put(")", "");
        ENGLISH_CHARS.put("\\.", "");
    }

    public void importUsersFromCsv() {
        if (userService.count() > 0) {
            return;
        }
        List<User> users = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(USER_DATA_URL);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.ISO_8859_1);
             BufferedReader br = new BufferedReader(isr)) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] attr = line.split(";");
                users.add(createUser(attr));
            }

            for (User u : users) {
                try {
                    if (userService.findByUsername(u.getUsername()).isEmpty()) {
                        userService.save(u);
                    }
                } catch (Exception e) {
                    System.err.println(u.getPassword());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User createUser(String[] data) {
        User user = new User();
        user.setMyShareID(Integer.parseInt(data[0]));
        user.setFirstname(data[2]);
        user.setLastname(data[1]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        user.setBirthDate(LocalDate.parse(data[6], formatter));
        user.setRole(StringUtils.isEmpty(data[7]) ? Role.USER : Role.valueOf(data[7]));
        String username = createUserName(data[8]);
        user.setUsername(username);
        user.setPassword(SecurityUtils.encryptSecret(username));
        user.setU20(Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 18);
        return user;
    }

    private String createUserName(String data) {
        for (String s : ENGLISH_CHARS.keySet()) {
            data = data.replace(s, ENGLISH_CHARS.get(s));
        }
        return data;
    }
}
