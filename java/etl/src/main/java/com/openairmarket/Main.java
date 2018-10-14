package com.openairmarket;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Main {
  private static EntityManager em;

  public static void main(String[] args) {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SPVGanaMasSQL");
    em = emf.createEntityManager();

    Query q = em.createNativeQuery("SELECT count(*) FROM dbo.PRODUCTOS");
    List authors = q.getResultList();
    for (Object a : authors) {
      System.out.println(a);
    }
  }
}
