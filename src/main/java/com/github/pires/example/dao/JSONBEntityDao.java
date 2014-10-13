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
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.pires.example.dao;

import com.github.pires.example.model.JSONBEntity;
import java.util.List;
import javax.persistence.Query;

/**
 * TODO add description
 */
public class JSONBEntityDao extends AbstractDao<JSONBEntity> {

  public JSONBEntityDao() {
    super(JSONBEntity.class);
  }

  public List<JSONBEntity> find_all_by_document_created_between(
      final long begin, final long end) {
    final String sql = "select * from \"public\".jsonb_entities, lateral jsonb_array_elements(documents) document_entry where CAST(document_entry ->> 'created' AS bigint) BETWEEN ? AND ?;";
    Query query = getEntityManager().
        createNativeQuery(sql, entityClass);
    query.setParameter(1, begin);
    query.setParameter(2, end);
    return query.getResultList();
  }

}
