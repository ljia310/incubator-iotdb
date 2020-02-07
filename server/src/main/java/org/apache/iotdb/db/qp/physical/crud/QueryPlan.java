/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iotdb.db.qp.physical.crud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.iotdb.db.qp.logical.Operator;
import org.apache.iotdb.db.qp.physical.PhysicalPlan;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Path;
import org.apache.iotdb.tsfile.read.expression.IExpression;

public class QueryPlan extends PhysicalPlan {

  private List<Path> paths = null;
  private List<TSDataType> dataTypes = null;

  private List<Path> deduplicatedPaths = new ArrayList<>();
  private List<TSDataType> deduplicatedDataTypes = new ArrayList<>();

  private IExpression expression = null;

  private int rowLimit = 0;
  private int rowOffset = 0;

  private boolean alignByTime = true; // for disable align sql

  private boolean isGroupByDevice = false; // for group by device sql
  private List<String> measurements; // for group by device sql, e.g. temperature
  private Map<String, Set<String>> measurementsGroupByDevice; // for group by device sql, e.g. root.ln.d1 -> temperature
  private Map<String, TSDataType> dataTypeConsistencyChecker; // for group by device sql, e.g. root.ln.d1.temperature -> Float
  private Map<String, IExpression> deviceToFilterMap; // for group by device sql
  private Map<Path, TSDataType> dataTypeMapping = new HashMap<>(); // for group by device sql

  public QueryPlan() {
    super(true);
    setOperatorType(Operator.OperatorType.QUERY);
  }

  public QueryPlan(boolean isQuery, Operator.OperatorType operatorType) {
    super(isQuery, operatorType);
  }

  public IExpression getExpression() {
    return expression;
  }

  public void setExpression(IExpression expression) {
    this.expression = expression;
  }

  @Override
  public List<Path> getPaths() {
    return paths;
  }

  public void setPaths(List<Path> paths) {
    this.paths = paths;
  }

  public List<TSDataType> getDataTypes() {
    return dataTypes;
  }

  public void setDataTypes(List<TSDataType> dataTypes) {
    this.dataTypes = dataTypes;
  }

  public List<Path> getDeduplicatedPaths() {
    return deduplicatedPaths;
  }

  public void addDeduplicatedPaths(Path path) {
    this.deduplicatedPaths.add(path);
  }

  public List<TSDataType> getDeduplicatedDataTypes() {
    return deduplicatedDataTypes;
  }

  public void addDeduplicatedDataTypes(TSDataType dataType) {
    this.deduplicatedDataTypes.add(dataType);
  }

  public int getRowLimit() {
    return rowLimit;
  }

  public void setRowLimit(int rowLimit) {
    this.rowLimit = rowLimit;
  }

  public int getRowOffset() {
    return rowOffset;
  }

  public void setRowOffset(int rowOffset) {
    this.rowOffset = rowOffset;
  }

  public boolean hasLimit() {
    return rowLimit > 0;
  }

  public boolean isGroupByDevice() {
    return isGroupByDevice;
  }

  public void setGroupByDevice(boolean groupByDevice) {
    isGroupByDevice = groupByDevice;
  }
  
  public boolean isAlignByTime() {
    return alignByTime;
  }
  
  public void setAlignByTime(boolean align) {
    alignByTime = align;
  }

  public void setMeasurements(List<String> measurements) {
    this.measurements = measurements;
  }

  public List<String> getMeasurements() {
    return measurements;
  }

  public void setMeasurementsGroupByDevice(
      Map<String, Set<String>> measurementsGroupByDevice) {
    this.measurementsGroupByDevice = measurementsGroupByDevice;
  }

  public Map<String, Set<String>> getMeasurementsGroupByDevice() {
    return measurementsGroupByDevice;
  }

  public void setDataTypeConsistencyChecker(
      Map<String, TSDataType> dataTypeConsistencyChecker) {
    this.dataTypeConsistencyChecker = dataTypeConsistencyChecker;
  }

  public Map<String, TSDataType> getDataTypeConsistencyChecker() {
    return dataTypeConsistencyChecker;
  }

  public Map<Path, TSDataType> getDataTypeMapping() {
    return dataTypeMapping;
  }

  public void addTypeMapping(Path path, TSDataType dataType) {
    dataTypeMapping.put(path, dataType);
  }

  public void setDeduplicatedPaths(
      List<Path> deduplicatedPaths) {
    this.deduplicatedPaths = deduplicatedPaths;
  }

  public void setDeduplicatedDataTypes(List<TSDataType> deduplicatedDataTypes) {
    this.deduplicatedDataTypes = deduplicatedDataTypes;
  }

  public Map<String, IExpression> getDeviceToFilterMap() {
    return deviceToFilterMap;
  }

  public void setDeviceToFilterMap(Map<String, IExpression> deviceToFilterMap) {
    this.deviceToFilterMap = deviceToFilterMap;
  }

}
