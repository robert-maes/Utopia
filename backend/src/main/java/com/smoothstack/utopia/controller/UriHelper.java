package com.smoothstack.utopia.controller;

import javax.servlet.http.HttpServletRequest;

public class UriHelper {

  private static final String ROOT = "/utopia_backend_war/";

  static String[] parseUri(HttpServletRequest request) {
    return request.getRequestURI().replace(ROOT, "").split("/");
  }
}
