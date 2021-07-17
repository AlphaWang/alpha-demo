package com.alphawang.jdk.grammar;

import java.util.Date;

public class RefParam {

  public static void main(String[] args) {
    User user = null;
    change(user);

    System.out.println(user);
  }

  private static void change(User user) {
    if (user == null) {
      user = new User();
      user.setName("Test");

      System.out.println(user);
    }

  }

  private static class User {
    private String name;
    private Date bd;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Date getBd() {
      return bd;
    }

    public void setBd(Date bd) {
      this.bd = bd;
    }
  }
}
