/*
 * The MIT License
 *
 * Copyright 2016 Ivar Grimstad (ivar.grimstad@gmail.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package eu.agilejava.security;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.security.DeclareRoles;
import javax.security.authentication.mechanism.http.annotation.FormAuthenticationMechanismDefinition;
import javax.security.authentication.mechanism.http.annotation.LoginToContinue;
import javax.security.identitystore.annotation.Credentials;
import javax.security.identitystore.annotation.EmbeddedIdentityStoreDefinition;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Ivar Grimstad (ivar.grimstad@gmail.com)
 */
@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/ui/login",
                errorPage = "/ui/login?auth=-1"))
@EmbeddedIdentityStoreDefinition({
    @Credentials(callerName = "reza", password = "secret1", groups = {"foo", "bar"}),
    @Credentials(callerName = "alex", password = "secret2", groups = {"foo", "kaz"}),
    @Credentials(callerName = "arjan", password = "secret3", groups = {"foo"}),
    @Credentials(callerName = "ivar", password = "secret4", groups = {"bar"})}
)
@DeclareRoles({"foo", "bar", "kaz"})
@ApplicationPath("ui")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> classes = new HashSet<>();

        classes.add(HelloController.class);
        classes.add(LoginController.class);

        return classes;
    }
}
