package com.smoothstack.utopia.api.dto;

import javax.validation.constraints.NotNull;

public class UpdateTicketDto {

  @NotNull(message = "Ticket active status is required")
  private Boolean isActive;

  public Boolean getActive() {
    return isActive;
  }
}
