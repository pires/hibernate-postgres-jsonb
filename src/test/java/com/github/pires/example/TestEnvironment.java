/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.pires.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Initializes our test environment.
 */
public final class TestEnvironment {

  private static EntityManagerFactory entityManagerFactory;
  private static EntityManager entityManager;
  private static EntityTransaction tx;

  @BeforeSuite
  public void bootstrap() {
    // provision persistence manager
    entityManagerFactory = Persistence.createEntityManagerFactory("test");
    entityManager = entityManagerFactory.createEntityManager();
    entityManager.setFlushMode(FlushModeType.COMMIT);
    tx = entityManager.getTransaction();
  }

  @AfterSuite
  public void cleanup() {
    tx = null;
    entityManager.close();
    entityManagerFactory.close();
  }

  public static EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }

  public static EntityManager getEntityManager() {
    return entityManager;
  }

  public static EntityTransaction getTransaction() {
    return tx;
  }

}
