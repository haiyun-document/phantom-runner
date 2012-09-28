/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2012 Solita Oy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fi.solita.phantomrunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import fi.solita.phantomrunner.jetty.PhantomJettyServer;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface PhantomConfiguration {

    /**
     * Defines the absolute or relative path where PhantomJS executable is in the system. Preferred
     * way is to set the PhantomJS executable in the system path so it can be referenced directly with
     * simple "phantomjs" from anywhere. Default value is "phantomjs"
     */
    String phantomPath() default "phantomjs";
    
    /**
     * Ant-style path pattern list which is used to scan the Javascript tests to be ran.
     */
    String[] tests();
    
    /**
     * Base directory where the tests should be scanned relative to the working directory, default is src/test/js
     */
    String testsBaseDir() default "src/test/js";
    
    /**
     * <p>A list of external libraries which should be injected to the test pages before any tests are actually ran.
     * This is the preferred way to provide your application an external module loading mechanism (such as the
     * excellent <a href="http://requirejs.org/">RequireJs</a>) which you can then easily use to load your actual
     * application code which you are going to test.</p>
     * 
     * <p>Library paths follow the conventional Java resource loading mechanism with the additional support for
     * <i>classpath</i> protocol. Thus all of the following are valid paths:</p>
     * 
     * <ul>
     *   <li>file://./some/path/foo.js</li>
     *   <li>classpath:some/path/foo.js</li>
     *   <li>http://foobar.com/some/path/foo.js</li>
     * </ul>
     * 
     * <p>All loaded resources are cached for the execution of {@link PhantomRunner}. This prevents both excessive
     * disc I/O and also network I/O in case of external network resources.</p>
     */
    String[] injectLibs() default "";
    
    /**
     * Interpreter type to be used when running the tests.
     */
    JavascriptTestInterpreterConfiguration interpreter();

    
    PhantomServerConfiguration server() default 
        @PhantomServerConfiguration(
            serverClass=PhantomJettyServer.class
        );
}
