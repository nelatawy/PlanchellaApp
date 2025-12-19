import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  /**
   * This automatically adds the authentication data with every request we send to the backend
   * If this isn't present then a 403 (not authorized) error would be thrown when we communicate with the backend
   */

  // Don't add token to public auth endpoints (login, register, etc.) (they don't need the token)
  const publicEndpoints = ['/account/login', '/account/register', '/account/auth/google'];
  
  const isPublicEndpoint = publicEndpoints.some(endpoint => req.url.includes(endpoint));
  
  if (isPublicEndpoint) {
    return next(req); // Send request without token
  }

  const token = localStorage.getItem('authToken');
  
  if (token) {
    // Clone the request and add the Authorization header
    const clonedRequest = req.clone({

      // Adds this to every http request
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(clonedRequest); // This passes the new request to the next interceptor (if there was any)
  }
  
  return next(req); // This passes the original request to the next interceptor (if there was any)
};
