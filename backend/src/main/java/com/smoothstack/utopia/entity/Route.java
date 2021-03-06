package com.smoothstack.utopia.entity;

public class Route {

  private int id;
  private Airport origin;
  private Airport destination;

  public Route(int id, Airport origin, Airport destination) {
    this.id = id;
    this.origin = origin;
    this.destination = destination;
  }
}
