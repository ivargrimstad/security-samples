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

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.authentication.mechanism.http.HttpMessageContext;
import javax.security.authentication.mechanism.http.annotation.AutoApplySession;
import javax.security.identitystore.CredentialValidationResult;
import static javax.security.identitystore.CredentialValidationResult.Status.VALID;
import javax.security.identitystore.IdentityStore;
import javax.security.identitystore.annotation.Credentials;
import javax.security.identitystore.annotation.EmbeddedIdentityStoreDefinition;
import javax.security.identitystore.credential.Password;
import javax.security.identitystore.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EmbeddedIdentityStoreDefinition({
    @Credentials(callerName = "reza", password = "secret1", groups = {"foo", "bar"}),
    @Credentials(callerName = "alex", password = "secret2", groups = {"foo", "kaz"}),
    @Credentials(callerName = "arjan", password = "secret3", groups = {"foo"}),
    @Credentials(callerName = "ivar", password = "secret4", groups = {"bar"})}
)
@DeclareRoles({"foo", "bar", "kaz"})
@AutoApplySession
@RequestScoped
public class TestAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStore identityStore;

    @Override
    public AuthStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthException {

        if (request.getParameter("j_idt6:j_idt9") != null && request.getParameter("j_idt6:j_idt11") != null) {

            // Get the (caller) name and password from the request
            // NOTE: This is for the smallest possible example only. In practice
            // putting the password in a request query parameter is highly
            // insecure
            String name = request.getParameter("j_idt6:j_idt9");
            Password password = new Password(request.getParameter("j_idt6:j_idt11"));

            // Delegate the {credentials in -> identity data out} function to
            // the Identity Store
            CredentialValidationResult result = identityStore.validate(
                    new UsernamePasswordCredential(name, password));

            if (result.getStatus() == VALID) {
                // Communicate the details of the authenticated user to the
                // container. In many cases the underlying handler will just store the details 
                // and the container will actually handle the login after we return from 
                // this method.
                return httpMessageContext.notifyContainerAboutLogin(
                        result.getCallerPrincipal(), result.getCallerGroups());
            } else {
                
                return httpMessageContext.responseUnAuthorized();
            }
        }

        return httpMessageContext.doNothing();
    }

}
