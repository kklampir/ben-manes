/*
 * Copyright 2015 Ben Manes. All Rights Reserved.
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
 */
package com.github.benmanes.caffeine.cache.simulator.admission;

import java.util.function.Function;

import com.typesafe.config.Config;

/**
 * The admission policies.
 *
 * @author ben.manes@gmail.com (Ben Manes)
 */
public enum Admission {
  ALWAYS(config -> Admittor.always(), Function.identity()),
  TINYLFU(TinyLfu::new, name -> name + "_TinyLfu");

  private final Function<Config, Admittor> factory;
  private final Function<String, String> formatter;

  private Admission(Function<Config, Admittor> factory, Function<String, String> formatter) {
    this.formatter = formatter;
    this.factory = factory;
  }

  /**
   * Returns a configured admittor.
   *
   * @param config the configuration
   * @return an admission policy
   */
  public Admittor from(Config config) {
    return factory.apply(config);
  }

  /** Returns the policy's formatted name. */
  public String format(String name) {
    return formatter.apply(name);
  }
}
