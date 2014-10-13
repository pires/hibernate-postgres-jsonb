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
package com.github.pires.example.jsonb;

import com.github.pires.example.dao.JSONBEntityDao;
import com.github.pires.example.model.JSONBDocument;
import com.github.pires.example.model.JSONBEntity;
import com.github.pires.example.TestEnvironment;
import java.util.List;
import javax.persistence.EntityTransaction;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for {@link JSONBEntityDao}.
 */
public class JSONBEntityDaoTest {

  public static final String GROUP_CREATE = "JSONBEntityDaoTest_create";
  public static final String GROUP_QUERY = "JSONBEntityDaoTest_query";
  public static final String GROUP_DELETE = "JSONBEntityDaoTest_delete";

  private static final Long NOW = System.currentTimeMillis();
  private static final String DESCRIPTION = "awesome jsonb doc persisted";

  private JSONBEntityDao dao;
  private EntityTransaction tx;

  private JSONBEntity entity;

  @BeforeClass
  public void setUp() {
    // provision entity transaction
    tx = TestEnvironment.getTransaction();

    // provision daos
    dao = new JSONBEntityDao();
    dao.setEntityManager(TestEnvironment.getEntityManager());
  }

  @Test(groups = GROUP_CREATE)
  public void test_creation() {
    tx.begin();
    JSONBDocument doc = new JSONBDocument().created(NOW).
        description(DESCRIPTION);
    entity = new JSONBEntity();
    entity.document(doc);
    dao.persist(entity);
    tx.commit();
    assertNotNull(entity.getId());
    assertNotNull(entity.getVersion());
  }

  @Test(groups = GROUP_CREATE)
  public void test_creation_with_null_document() {
    int count = dao.count();
    tx.begin();
    dao.persist(new JSONBEntity());
    count++;
    tx.commit();
    assertEquals(dao.count(), count);
  }

  @Test(groups = GROUP_CREATE)
  public void test_creation_with_document_tree() {
    final JSONBDocument doc1 = new JSONBDocument().created(NOW + 1000).
        description("doc1");
    final JSONBDocument doc2 = new JSONBDocument().created(NOW + 7000).
        description("doc2");
    tx.begin();
    entity = new JSONBEntity();
    entity.documents(doc1, doc2);
    dao.persist(entity);
    tx.commit();
    assertNotNull(entity.getId());
    assertNotNull(entity.getVersion());
  }

  @Test(groups = GROUP_QUERY, dependsOnGroups = GROUP_CREATE)
  public void test_retrieve_created() {
    final JSONBEntity some = dao.find(entity.getId());
    assertNotNull(some);
    assertNotNull(some.getDocuments());
    assertEquals(some.getDocuments().
        size(), 2);
  }

  @Test(groups = GROUP_QUERY, dependsOnGroups = GROUP_CREATE)
  public void test_find_all_by_document_created() {
    final List<JSONBEntity> entities = dao.
        find_all_by_document_created_between(
            NOW, NOW + 10000);
    assertNotNull(entities);
    assertFalse(entities.isEmpty());
  }

  @AfterClass
  public void cleanUp() {
    tx = null;
    dao = null;
  }

}
