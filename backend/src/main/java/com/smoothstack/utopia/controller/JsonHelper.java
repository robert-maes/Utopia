package com.smoothstack.utopia.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class JsonHelper {

  private static final Gson gson = new Gson();

  static void respondWithJson(HttpServletResponse response, Object object)
    throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    String objectJsonString = JsonHelper.gson.toJson(object);
    PrintWriter printWriter = response.getWriter();
    printWriter.print(objectJsonString);
    printWriter.flush();
  }
}
