/*
 * Copyright © 2017, 2018 Ivar Grimstad
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.eclipse.krazo.security;

import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;

public class CsrfValidateFilterMediaTypeTest {

  @Test
  public void shouldSupportFormMediaType() {
      assertTrue(CsrfValidateFilter.isSupportedMediaType(
          MediaType.valueOf("application/x-www-form-urlencoded")
      ));
  }

  @Test
  public void shouldSupportFormMediaTypeWithCharset() {
      assertTrue(CsrfValidateFilter.isSupportedMediaType(
          MediaType.valueOf("application/x-www-form-urlencoded;charset=windows-31j")
      ));
  }

  @Test
  public void shouldFailForOtherMediaType() {
      assertFalse(CsrfValidateFilter.isSupportedMediaType(
          MediaType.valueOf("application/pdf")
      ));
  }

  @Test
  public void shouldFailForNoMediaType() {
      assertFalse(CsrfValidateFilter.isSupportedMediaType(null));
  }

}
