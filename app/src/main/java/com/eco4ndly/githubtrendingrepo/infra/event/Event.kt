package com.eco4ndly.githubtrendingrepo.infra.event

/*
 * Copyright 2018 Google LLC.
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

/**
 * Events will be exposed via a live data using this class
 */
open class Event<out T>(private val content: T) {

  /**
   * Only external read allowed
   */
  var consumed = false
    private set

  /**
   * Consumes the content if it's not been consumed yet.
   * @return The unconsumed content or `null` if it was consumed already.
   */
  fun consume(): T? {
    return if (consumed) {
      null
    } else {
      consumed = true
      content
    }
  }

  /**
   * @return The content whether it's been handled or not.
   */
  fun peek(): T = content
}