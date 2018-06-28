package com.example.token.controllers;

import com.example.token.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TokenController {

    @Autowired
    JWTService jwtService;

    /**
     * Функция генерирует токен по имени потльзователя и возвращает его в виде String значения
     * @return токен в виде String значения
     */
    @RequestMapping(value="/getTokenByUsername/{username}", method = RequestMethod.GET)
    public String getTokenByUsername(@PathVariable("username") String username){
        return jwtService.getTokenBySubject(username);
    }

    /**
     * Функция генерирует токен по id сессии и возвращает его в виде String значения
     * @param request - http запрос
     * @return токен в виде String значения
     */
    @RequestMapping(value="/getTokenBySession", method = RequestMethod.GET)
    public String getTokenBySessionId(final HttpServletRequest request){
        return jwtService.getTokenBySubject(request.getSession().getId());
    }

    /**
     * Функция генерирует токен по id сессии и возвращает его в виде String значения
     * @param request - http запрос
     * @return токен в виде String значения
     */
    @RequestMapping(value="/getTokenBySessionAndUsername/{username}", method = RequestMethod.GET)
    public String getTokenBySessionAndUsername(@PathVariable("username") String username, final HttpServletRequest request){
        return jwtService.getTokenBySessionIdAndUsername(request.getSession().getId(), username);
    }

    /**
     * Функция генерирует токен по id сессии и возвращает его в виде String значения
     * @param request - http запрос
     * @param response - http ответ, в который может быть записан токен
     * @return токен в виде String значения
     */
    @RequestMapping(value="/generateTokenByUsername/{username}", method = RequestMethod.GET)
    public void getToken(@PathVariable("username") String username, final HttpServletRequest request, final HttpServletResponse response){
        try {
            //return jwtService.generateTokenByUsername(username, response);
            jwtService.generateTokenByUsername(username, response);
        } catch (IOException e) {
            //return "";
            //e.printStackTrace();
        }
    }

}
