package com.planchella.registration.authentication;


public interface IAuthenticator {

    boolean emailPasswordMatch(String creds);

    boolean emailIsUsed(String username);
     
}