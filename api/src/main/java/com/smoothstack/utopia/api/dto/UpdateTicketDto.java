package com.smoothstack.utopia.api.dto;

import javax.validation.constraints.NotNull;

public class UpdateTicketDto {

  private Boolean isActive;

  public Boolean getIsActive() {
    return isActive;
  }

  @Override
  public String toString() {
    return "UpdateTicketDto{" + "isActive=" + isActive + '}';
  }
}
