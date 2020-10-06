package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/7/19.
 * 货物种类和车牌颜色信息
 */
public class CargoTypeAndColorBeans {
  private String id;//	true	string	代码对应id
  private String name;//	true	string	代码名称
  private String value;//	true	string	代码值
  private String type;//	true	string	代码类型
  private String description;//	true	string	代码描述

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return name;
  }
}
