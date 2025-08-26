package com.xiaoR.mp.domain.po;

import lombok.Data;

@Data
public class Address {

  private long id;
  private long userId;
  private String province;
  private String city;
  private String town;
  private String mobile;
  private String street;
  private String contact;
  private boolean isDefault;
  private String notes;
  private boolean deleted;
}
