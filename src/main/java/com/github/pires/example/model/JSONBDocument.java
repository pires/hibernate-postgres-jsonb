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

/**
 * Simple object to test JSONB.
 */
public class JSONBDocument {

  private long created;
  private String description;

  public JSONBDocument() {
  }

  public long getCreated() {
    return created;
  }

  public JSONBDocument created(final long created) {
    this.created = created;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public JSONBDocument description(final String description) {
    this.description = description;
    return this;
  }

}
