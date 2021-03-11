package com.smoothstack.utopia.api.dto;

public class UpdateTicketDto {

  private Boolean isActive;

  public Boolean getIsActive() {
    return isActive;
  }

  @Override
  public String toString() {
    return "UpdateTicketDto{" + "isActive=" + isActive + '}';
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
