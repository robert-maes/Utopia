package com.smoothstack.utopia.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class IndexController extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    response.setContentType("text/html");
    response.setStatus(200);
    try (PrintWriter printWriter = response.getWriter()) {
      printWriter.print(
        "<html><head><title>Hello</title></head><body><h1>Hello, world2!</h1></body></html>"
      );
      printWriter.close();
    } catch (IOException e) {
      response.setStatus(500);
    }
  }
}
