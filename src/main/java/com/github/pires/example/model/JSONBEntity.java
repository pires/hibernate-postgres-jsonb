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
package com.github.pires.example.model;

import com.github.pires.example.hibernate.user.types.JSONBUserType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static java.util.UUID.randomUUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@TypeDef(name = "jsonb", typeClass = JSONBUserType.class, parameters = {
  @Parameter(name = JSONBUserType.CLASS,
      value = "com.github.pires.example.model.JSONBEntity")})
@Entity
@Table(name = "JSONB_ENTITIES")
public class JSONBEntity {

  @Id
  @Type(type = "pg-uuid")
  private UUID id;

  @Version
  private Long version;

  @Type(type = "jsonb")
  private List<JSONBDocument> documents;

  public JSONBEntity() {
    this.id = randomUUID();
    documents = new ArrayList<>();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public List<JSONBDocument> getDocuments() {
    return documents;
  }

  public JSONBEntity documents(final List<JSONBDocument> documents) {
    this.documents = documents;
    return this;
  }

  public JSONBEntity documents(final JSONBDocument... documents) {
    for (JSONBDocument document : documents) {
      this.documents.add(document);
    }
    return this;
  }

  public JSONBEntity document(final JSONBDocument document) {
    this.documents.add(document);
    return this;
  }

}
