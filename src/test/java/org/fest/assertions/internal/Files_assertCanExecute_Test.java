/*
 * Created on Jul 05, 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2010-2012 the original author or authors.
 */
package org.fest.assertions.internal;

import static org.fest.assertions.error.ShouldBeExecutable.shouldBeExecutable;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for <code>{@link FileAssert#canExecute()}</code>.
 * 
 * @author Olivier Demeijer
 * 
 */

public class Files_assertCanExecute_Test {
  @Rule public ExpectedException thrown = none();

  private File actual;
  private Failures failures;
  private Files files;

  @Before public void setUp() {
    actual = mock(File.class);
    failures = spy(new Failures());
    files = new Files();
    files.failures = failures;
  }

  @Test public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    files.assertCanExecute(someInfo(), null);
  }

  @Test public void should_fail_if_can_not_execute() {
    when(actual.canExecute()).thenReturn(false);
    AssertionInfo info = someInfo();
    try {
      files.assertCanExecute(info, actual);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeExecutable(actual));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test public void should_pass_if_actual_can_execute() {
    when(actual.canExecute()).thenReturn(true);
    files.assertCanExecute(someInfo(), actual);
  }

}
