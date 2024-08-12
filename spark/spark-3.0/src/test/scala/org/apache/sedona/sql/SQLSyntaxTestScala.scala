/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sedona.sql

import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.prop.TableDrivenPropertyChecks
import org.apache.spark.sql.catalyst.parser.ParserInterface
import org.apache.spark.sql.AnalysisException
import org.scalatest.matchers.should.Matchers._

/**
 * Test suite for testing Sedona SQL support.
 */
class SQLSyntaxTestScala extends TestBaseScala with TableDrivenPropertyChecks {

  override def beforeAll(): Unit = {
    super.beforeAll()
    sparkSession.conf.set("spark.sql.legacy.createHiveTableByDefault", "false")
  }

  describe("Table creation DDL tests") {

    it("should be able to create a regular table without geometry column should work") {
      val parser: ParserInterface = sparkSession.sessionState.sqlParser
      val plan = parser.parsePlan("CREATE TABLE IF NOT EXISTS T_TEST_REGULAR (INT_COL INT)")

      plan should not be (null)
    }

    it(
      "should be able to create a regular table with geometry column should work without a workaround") {
      val parser: ParserInterface = sparkSession.sessionState.sqlParser
      val plan = parser.parsePlan("CREATE TABLE T_TEST_EXPLICIT_GEOMETRY (GEO_COL GEOMETRY)")

      plan should not be (null)
    }

    it(
      "should be able to create a regular table with regular and geometry column should work without a workaround") {
      val parser: ParserInterface = sparkSession.sessionState.sqlParser
      val plan = parser.parsePlan(
        "CREATE TABLE T_TEST_EXPLICIT_GEOMETRY_2 (INT_COL INT, GEO_COL GEOMETRY)")

      plan should not be (null)
    }
  }
}
